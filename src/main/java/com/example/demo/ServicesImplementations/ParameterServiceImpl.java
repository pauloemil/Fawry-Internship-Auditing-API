package com.example.demo.ServicesImplementations;

import com.example.demo.Entities.Parameter;
import com.example.demo.Exceptions.CouldNotSaveException;
import com.example.demo.Repositories.ParameterRepository;
import com.example.demo.Services.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterServiceImpl implements ParameterService {
    @Autowired
    ParameterRepository parameterRepo;

    @Override
    public Parameter saveParameter(Parameter parameter) {
        Parameter savedParameter = parameterRepo.save(parameter);
        if(savedParameter != null) {
            return savedParameter;
        }
        else {
            throw new CouldNotSaveException(String.format("Can't save Parameter of type: %1s with value: %2s", parameter.getParameter_type().getParameter_type_id(), parameter.getParameter_value()));
        }
    }

    @Override
    public List<Parameter> saveAllParameters(List<Parameter> parameters) {
        return parameterRepo.saveAll(parameters);
    }
}
