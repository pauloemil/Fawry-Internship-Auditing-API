package com.example.demo.Mappers;

import com.example.demo.DTOs.ActionRequestDto;
import com.example.demo.Exceptions.WrongJsonBodyException;
import com.example.demo.Exceptions.WrongJsonPropertyException;
import com.example.demo.Exceptions.WrongParameterValueException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class StringToActionRequestDto {
	public static ActionRequestDto convertFromString(String stringJson) throws JsonProcessingException, WrongParameterValueException, WrongJsonBodyException, WrongJsonPropertyException {
		try{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode actualObj = mapper.readTree(stringJson);
			return mapper.readValue(actualObj.toString(), ActionRequestDto.class);
		}
		catch(InvalidFormatException exception)
		{
			throw new WrongParameterValueException(exception.getMessage());
		}
		catch(JsonParseException exception){
			System.out.println(exception.getMessage());
			throw new WrongJsonBodyException("The JSON body is invalid!");
		}
		catch(UnrecognizedPropertyException exception){
			throw new WrongJsonPropertyException(exception.getMessage());
		}

	}

}
