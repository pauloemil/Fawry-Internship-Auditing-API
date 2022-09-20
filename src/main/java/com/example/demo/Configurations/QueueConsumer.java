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

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload String message) throws JsonProcessingException, WrongParameterValueException, WrongJsonBodyException, WrongJsonPropertyException {
        ActionRequestDto actionRequestDto = StringToActionRequestDto.convertFromString(message);

        actionService.saveAction(actionRequestDto);
    }
}