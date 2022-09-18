package com.example.demo.Services;

import com.example.demo.Entities.ActionType;

import java.util.List;
import java.util.Optional;

public interface ActionTypeService {
    ActionType findActionTypeByName(String name);
    List<ActionType> findAllActionTypes();
}
