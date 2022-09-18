package com.example.demo.Services;

import com.example.demo.Entities.BusinessEntity;

import java.util.Optional;

public interface BusinessEntityService {
    BusinessEntity findBusinessEntityById(long id);
}
