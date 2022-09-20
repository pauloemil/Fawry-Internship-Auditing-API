package com.example.demo.Validators;

import com.example.demo.DTOs.ActionRequestDto;
import com.example.demo.Exceptions.*;
import com.example.demo.Services.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ActionValidation {

	// autowire doesn't work properly!
	private ActionTypeService actionTypeService;
	private ApplicationService applicationService;
	private BusinessEntityService businessEntityService;
	private UserService userService;
	private ParameterTypeService parameterTypeService;

	public void isActionRequestValid(ActionRequestDto actionRequestDto) {
		try{
			applicationService.findApplicationById(actionRequestDto.getApplication_id());
			businessEntityService.findBusinessEntityById(actionRequestDto.getBusiness_entity_id());

			if (actionRequestDto.getUser_id() != 0) {
				userService.findUserById(actionRequestDto.getUser_id());
			}

			actionTypeService.findActionTypeByName(actionRequestDto.getAction_type_name());
			actionRequestDto.getParameters().forEach(parameterDto -> {
				parameterTypeService.findParameterTypeByName(parameterDto.getParameter_type_name());
			});
		}
		catch (MissingParameterException exception){
			throw new MissingParameterException(exception.getMessage());
		}
		catch (NotFoundException exception){
			throw new NotFoundException(exception.getMessage());
		}
	}
}
