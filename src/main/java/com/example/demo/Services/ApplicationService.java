package com.example.demo.Services;

import com.example.demo.Entities.Application;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    Application findApplicationById(long id);
    List<Application> findAllApplications();
}
