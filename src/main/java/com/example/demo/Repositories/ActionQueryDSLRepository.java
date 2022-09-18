package com.example.demo.Repositories;

import com.example.demo.DTOs.ActionsResponseDto;
import com.example.demo.DTOs.SearchDto;

public interface ActionQueryDSLRepository {
    ActionsResponseDto findAppsGeneric(SearchDto searchDto);
}
