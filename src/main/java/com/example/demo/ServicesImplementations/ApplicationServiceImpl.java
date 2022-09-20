package com.example.demo.ServicesImplementations;

import com.example.demo.Entities.Application;
import com.example.demo.Exceptions.MissingParameterException;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.ApplicationRepository;
import com.example.demo.Services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Application findApplicationById(long id) {
        if(id == 0)
            throw new MissingParameterException("application_id is missing from the json body!");
        return applicationRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Application with id: %1d is not found!", id)));
    }

    @Override
    public List<Application> findAllApplications() {
        return applicationRepository.findAll();
    }
}
