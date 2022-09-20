package com.example.demo.Services;

import com.example.demo.DTOs.ActionRequestDto;
import com.example.demo.Entities.Action;

import java.util.List;
import java.util.Optional;

public interface ActionService {
    Action findActionById(long id);
    Action saveAction(ActionRequestDto actionRequestDto);
}
