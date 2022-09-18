package com.example.demo.DTOs;

import com.example.demo.Entities.ActionType;
import com.example.demo.Entities.Application;
import com.example.demo.Entities.ParameterType;
import com.example.demo.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectorsDto {
	private List<ParameterType> parameterTypes;
	private List<ActionType> actionTypes;

	private List<Application> applications;

}
