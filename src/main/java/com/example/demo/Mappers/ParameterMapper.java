package com.example.demo.Mappers;

import com.example.demo.DTOs.ParameterDto;
import com.example.demo.Entities.Parameter;
import com.example.demo.Services.ParameterTypeService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParameterMapper {
	@Mapping(target = "parameter_type", expression = "java(parameterTypeService.findParameterTypeByName(parameterDto.getParameter_type_name()))")
	Parameter convertToParameter(ParameterDto parameterDto,
								 @Context ParameterTypeService parameterTypeService);
}
