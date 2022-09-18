package com.example.demo.Exceptions;

import java.io.IOException;

public class WrongParameterValueException extends IOException {
	public WrongParameterValueException() {
	}

	public WrongParameterValueException(String message) {
		super(message);
	}

	public WrongParameterValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongParameterValueException(Throwable cause) {
		super(cause);
	}
}
