package com.example.demo.Mappers;

import com.example.demo.DTOs.ActionRequestDto;
import com.example.demo.DTOs.ParameterDto;
import com.example.demo.Entities.Action;
import com.example.demo.Entities.User;
import com.example.demo.Services.ActionTypeService;
import com.example.demo.Services.ApplicationService;
import com.example.demo.Services.BusinessEntityService;
import com.example.demo.Services.UserService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActionMapper {

	ActionMapper INSTANCE = Mappers.getMapper(ActionMapper.class);


// available on the beta version f the mapstruct! condition and conditionExpression
//		@Mapping(target = "user",
//				 condition = "actionRequestDto.getUser_id() != 0"
//				, expression = "java(userService.findUserById(actionDto.getUser_id()))")
	@Mapping(target = "business_entity",
			expression = "java(businessEntityService.findBusinessEntityById(actionDto.getBusiness_entity_id()))")
	@Mapping(target = "application",
			expression = "java(applicationService.findApplicationById(actionDto.getApplication_id()))")

	@Mapping(target = "action_type",
			expression = "java(actionTypeService.findActionTypeByName(actionDto.getAction_type_name()))")
	@Mapping(target="parameters", ignore = true)
	@Mapping(target = "trace_id", expression = "java(\"dummy\")")
	Action convertToAction(ActionRequestDto actionDto,
						   @Context BusinessEntityService businessEntityService,
						   @Context ApplicationService applicationService,
						   @Context UserService userService,
						   @Context ActionTypeService actionTypeService);
}
