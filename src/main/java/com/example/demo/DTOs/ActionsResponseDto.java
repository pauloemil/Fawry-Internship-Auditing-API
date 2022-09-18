package com.example.demo.DTOs;

import com.example.demo.Entities.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionsResponseDto {
	private long total_count;
	private long page_size;
	private long page_number;
	private List<Action> actions;


	@Override
	public String toString() {
		return "ActionsResponseDto{" +
				"actions=" + actions +
				", total_count=" + total_count +
				", page_size=" + page_size +
				", page_number=" + page_number +
				'}';
	}
}
