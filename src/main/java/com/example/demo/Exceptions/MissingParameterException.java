package com.example.demo.Exceptions;

public class MissingParameterException extends RuntimeException {
	public MissingParameterException() {
	}

	public MissingParameterException(String message) {
		super(message);
	}

}
