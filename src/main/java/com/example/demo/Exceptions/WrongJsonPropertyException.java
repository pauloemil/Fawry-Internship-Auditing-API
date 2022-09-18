package com.example.demo.Exceptions;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.io.IOException;

public class WrongJsonPropertyException extends IOException {
	public WrongJsonPropertyException() {
	}

	public WrongJsonPropertyException(String message) {
		super(message);
	}

	public WrongJsonPropertyException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongJsonPropertyException(Throwable cause) {
		super(cause);
	}
}
