package com.example.demo.ServicesImplementations;

import com.example.demo.Entities.ParameterType;
import com.example.demo.Exceptions.MissingParameterException;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.ParameterTypeRepository;
import com.example.demo.Services.ParameterTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParameterTypeServiceImpl implements ParameterTypeService {
    @Autowired
    ParameterTypeRepository parameterTypeRepository;

    @Override
    public ParameterType findParameterTypeByName(String name) {
        if(name == null)
            throw new MissingParameterException("parameter_type_name is missing from the json body!");
        return parameterTypeRepository.findByName(name).orElseThrow(() -> new NotFoundException(String.format("Parameter Type with name: %1s is not found!", name)));
    }

    @Override
    public List<ParameterType> findAllParameterTypes() {
        return parameterTypeRepository.findAll();
    }


}
