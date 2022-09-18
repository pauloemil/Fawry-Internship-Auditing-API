package com.example.demo.Repositories;

import com.example.demo.Entities.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActionTypeRepository extends JpaRepository<ActionType, Long>{
    ActionType findByName(String name);
}
