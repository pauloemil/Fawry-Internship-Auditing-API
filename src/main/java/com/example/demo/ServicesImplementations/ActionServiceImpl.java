package com.example.demo.ServicesImplementations;

import com.example.demo.Entities.Action;
import com.example.demo.Entities.ActionType;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.ActionRepository;
import com.example.demo.Services.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionServiceImpl implements ActionService  {

    @Autowired
    ActionRepository actionRepository;

//    @Override
//    public List<Action> actionsList() {
//        return actionRepository.findAll();
//    }


    public ActionServiceImpl(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public ActionServiceImpl() {
    }

    @Override
    public Action findActionById(long id) {
        return actionRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Action with id: %1d is not found!", id)));

    }
    @Override
    public Action saveAction(Action action) {
        return actionRepository.save(action);
    }
}
