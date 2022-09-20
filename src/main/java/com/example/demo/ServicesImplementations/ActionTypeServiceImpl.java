package com.example.demo.ServicesImplementations;

import com.example.demo.Entities.ActionType;
import com.example.demo.Exceptions.MissingParameterException;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.ActionTypeRepository;
import com.example.demo.Services.ActionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionTypeServiceImpl  implements ActionTypeService {

    @Autowired
    ActionTypeRepository actionTypeRepository;
    @Override
    public ActionType findActionTypeByName(String name) {
        if(name == null)
            throw new MissingParameterException("action_type_name is missing from the json body!");
        return Optional.ofNullable(actionTypeRepository.findByName(name)).orElseThrow(() -> new NotFoundException(String.format("Action Type with name: %1s is not found!", name)));
    }

    @Override
    public List<ActionType> findAllActionTypes() {
        return actionTypeRepository.findAll();
    }

}
