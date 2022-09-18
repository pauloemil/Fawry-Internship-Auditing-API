package com.example.demo.Repositories;

import com.example.demo.Entities.ParameterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParameterTypeRepository extends JpaRepository<ParameterType, Long>{
    Optional<ParameterType> findByName(String name);
}
