package com.example.demo.Helpers;

import com.example.demo.DTOs.ParameterDto;

import java.util.List;

public class TemplateDescriptionConverter {
    public static String templateToDescriptionAdvanced(List<ParameterDto> parameters, String template) {
        for (ParameterDto parameterDto: parameters) {
            template = template.replace(String.format("{{%1s.value}}", parameterDto.getParameter_type_name()), parameterDto.getParameter_value());
            template = template.replace(String.format("{{%1s.name}}", parameterDto.getParameter_type_name()), parameterDto.getParameter_value());
            template = template.replace(String.format("{{%1s.id}}", parameterDto.getParameter_type_name()), ""+parameterDto.getParameter_id());
        }
        return template;
    }


}
