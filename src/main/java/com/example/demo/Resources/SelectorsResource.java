package com.example.demo.Resources;

import com.example.demo.DTOs.SelectorsDto;
import com.example.demo.Services.ActionTypeService;
import com.example.demo.Services.ApplicationService;
import com.example.demo.Services.ParameterTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/selectors")
public class SelectorsResource {
	@Autowired
	ParameterTypeService parameterTypeService;
	@Autowired
	ActionTypeService actionTypeService;
	@Autowired
	ApplicationService applicationService;

	@GetMapping("/")
	public ResponseEntity<SelectorsDto> findSelectors(){
		SelectorsDto selectorsDto = new SelectorsDto();
		selectorsDto.setActionTypes(actionTypeService.findAllActionTypes());
		selectorsDto.setParameterTypes(parameterTypeService.findAllParameterTypes());
		selectorsDto.setApplications(applicationService.findAllApplications());

		return new ResponseEntity<>(selectorsDto, HttpStatus.OK);
	}
}
