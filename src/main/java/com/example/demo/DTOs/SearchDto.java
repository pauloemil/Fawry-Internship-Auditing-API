package com.example.demo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private String user_name;
    private String application_name;
    private String business_entity_name;
    private long action_type_id;
    private long parameter_type_id;
    private String parameter_value;
    private long limit;
    private long page_number;
}
