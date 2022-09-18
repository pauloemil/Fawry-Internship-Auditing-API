package com.example.demo.Configurations;

import com.example.demo.DTOs.ParameterDto;
import com.example.demo.DTOs.ActionRequestDto;
import com.example.demo.Entities.*;
import com.example.demo.Exceptions.WrongJsonBodyException;
import com.example.demo.Exceptions.WrongJsonPropertyException;
import com.example.demo.Exceptions.WrongParameterValueException;
import com.example.demo.Mappers.ActionMapper;
import com.example.demo.Mappers.ParameterMapper;
import com.example.demo.Mappers.StringToActionRequestDto;
import com.example.demo.Services.*;
import com.example.demo.Validators.ActionValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import static com.example.demo.Helpers.TemplateDescriptionConverter.templateToDescriptionAdvanced;


@Component
public class QueueConsumer {
    @Autowired
    private ActionService actionService;
    @Autowired
    private ActionTypeService actionTypeService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private BusinessEntityService businessEntityService;
    @Autowired
    private UserService userService;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    private ParameterTypeService parameterTypeService;

    // ugly method with many lines and not sorted line actions!
    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload String message) throws JsonProcessingException, WrongParameterValueException, WrongJsonBodyException, WrongJsonPropertyException {
        ActionRequestDto actionRequestDto = StringToActionRequestDto.convertFromString(message);

        ActionValidation actionValidation = new ActionValidation(actionTypeService, applicationService, businessEntityService, userService, parameterTypeService);
        actionValidation.isActionRequestValid(actionRequestDto);

        List<ParameterDto> parameters = actionRequestDto.getParameters();

        Action action = ActionMapper.INSTANCE.convertToAction(actionRequestDto, businessEntityService, applicationService, userService, actionTypeService);

        // setting the ar/en descriptions for actions
        action.setDescription_en(templateToDescriptionAdvanced(actionRequestDto.getParameters(), action.getAction_type().getMessage_template_en()));
        action.setDescription_ar(templateToDescriptionAdvanced(actionRequestDto.getParameters(), action.getAction_type().getMessage_template_ar()));

        // to check if the user is sent or not!
        if(actionRequestDto.getUser_id() != 0) {
            action.setUser(userService.findUserById(actionRequestDto.getUser_id()));
            parameters.add(new ParameterDto(action.getUser().getUser_id(), "user", action.getUser().getUser_name()));
        }

        parameters.add(new ParameterDto(action.getApplication().getApplication_id(), "application", action.getApplication().getApplication_name()));
        parameters.add(new ParameterDto(action.getBusiness_entity().getBusiness_entity_id(), "be", action.getBusiness_entity().getBusiness_entity_name()));

        actionService.saveAction(action);
        actionRequestDto.getParameters().forEach(parameterDto -> {
            String name = parameterDto.getParameter_type_name();
            if(name.equals("user") || name.equals("application") || name.equals("be")) {
                return;
            }

            Parameter parameter = ParameterMapper.INSTANCE.convertToParameter(parameterDto, parameterTypeService);
            parameter.setAction(action);
            parameterService.saveParameter(parameter);
        });
    }
}