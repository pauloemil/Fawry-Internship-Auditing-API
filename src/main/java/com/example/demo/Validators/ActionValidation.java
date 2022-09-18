package com.example.demo.Validators;

import com.example.demo.DTOs.ActionRequestDto;
import com.example.demo.Exceptions.*;
import com.example.demo.Services.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
public class ActionValidation {
	@Autowired
	private ActionTypeService actionTypeService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private BusinessEntityService businessEntityService;
	@Autowired
	private UserService userService;
	@Autowired
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
