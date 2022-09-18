package com.example.demo.Services;

import com.example.demo.Entities.Parameter;

import java.util.List;

public interface ParameterService {
    Parameter saveParameter(Parameter parameter);
    List<Parameter> saveAllParameters(List<Parameter> parameters);
}
