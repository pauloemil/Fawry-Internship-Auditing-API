package com.example.demo.ServicesImplementations;

import com.example.demo.Entities.BusinessEntity;
import com.example.demo.Exceptions.MissingParameterException;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.BusinessEntityRepository;
import com.example.demo.Services.BusinessEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessEntityServiceImpl implements BusinessEntityService {
    @Autowired
    BusinessEntityRepository businessEntityRepository;
    @Override
    public BusinessEntity findBusinessEntityById(long id) {
        if(id == 0)
                throw new MissingParameterException("business_entity_id is missing from the json body!");
        return businessEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Business Entity with id: %1d is not found!", id)));
    }
}
