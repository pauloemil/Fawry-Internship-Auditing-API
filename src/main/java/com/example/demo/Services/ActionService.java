package com.example.demo.Services;

import com.example.demo.Entities.Action;

import java.util.List;
import java.util.Optional;

public interface ActionService {
//    List<Action> actionsList();
    Action findActionById(long id);

    Action saveAction(Action action);
}
