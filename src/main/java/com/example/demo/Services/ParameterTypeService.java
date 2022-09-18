package com.example.demo.Services;

import com.example.demo.Entities.ParameterType;

import java.util.List;
import java.util.Optional;

public interface ParameterTypeService {
    ParameterType findParameterTypeByName(String name);

    List<ParameterType> findAllParameterTypes();
}
