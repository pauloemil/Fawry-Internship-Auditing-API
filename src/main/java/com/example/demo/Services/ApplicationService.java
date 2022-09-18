package com.example.demo.Services;

import com.example.demo.Entities.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    Application findApplicationById(long id);
    List<Application> findAllApplications();

//    List<Application> findApplicationByName(String name);
}
