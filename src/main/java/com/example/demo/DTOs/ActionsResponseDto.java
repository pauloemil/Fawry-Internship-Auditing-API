package com.example.demo.DTOs;

import com.example.demo.Entities.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ActionsResponseDto {
	private long total_count;
	private long page_size;
	private long page_number;
	private List<Action> actions;
}
