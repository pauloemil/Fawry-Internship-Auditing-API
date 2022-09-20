package com.example.demo.ServicesImplementations;

import com.example.demo.DTOs.ActionRequestDto;
import com.example.demo.DTOs.ParameterDto;
import com.example.demo.Entities.Action;
import com.example.demo.Entities.ActionType;
import com.example.demo.Entities.Parameter;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Mappers.ActionMapper;
import com.example.demo.Mappers.ParameterMapper;
import com.example.demo.Repositories.ActionRepository;
import com.example.demo.Services.*;
import com.example.demo.Validators.ActionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.demo.Helpers.TemplateDescriptionConverter.templateToDescriptionAdvanced;

@Service
public class ActionServiceImpl implements ActionService  {

    @Autowired
    ActionRepository actionRepository;

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

    @Autowired
    ActionMapper actionMapper;
    @Autowired
    ParameterMapper parameterMapper;

    @Override
    public Action findActionById(long id) {
        return actionRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Action with id: %1d is not found!", id)));
    }

    @Override
    public Action saveAction(ActionRequestDto actionRequestDto) {
        ActionValidation actionValidation = new ActionValidation(actionTypeService, applicationService, businessEntityService, userService, parameterTypeService);
        actionValidation.isActionRequestValid(actionRequestDto);

        List<ParameterDto> parameters = actionRequestDto.getParameters();

        Action action = actionMapper.convertToAction(actionRequestDto, businessEntityService, applicationService, userService, actionTypeService);

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

        Action saved = actionRepository.save(action);
        //TODO: why?
        actionRequestDto.getParameters().forEach(parameterDto -> {
            String name = parameterDto.getParameter_type_name();
            if(name.equals("user") || name.equals("application") || name.equals("be")) {
                return;
            }

            Parameter parameter = parameterMapper.convertToParameter(parameterDto, parameterTypeService);
            parameter.setAction(saved);
            parameterService.saveParameter(parameter);
        });
        return saved;
    }
}
