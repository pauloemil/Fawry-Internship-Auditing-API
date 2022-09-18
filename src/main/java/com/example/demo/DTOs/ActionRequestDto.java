package com.example.demo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionRequestDto {

    private long application_id;
    private long user_id;
    private long business_entity_id;
    private String action_type_name;
    private List<ParameterDto> parameters;
}
