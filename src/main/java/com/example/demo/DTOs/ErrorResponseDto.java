package com.example.demo.DTOs;

import lombok.Data;
import org.springframework.messaging.support.ErrorMessage;

@Data
public class ErrorResponseDto {
	private String errorMessage;
}
