package com.example.demo.Exceptions;

public class CouldNotSaveException extends RuntimeException{
	public CouldNotSaveException() {
	}

	public CouldNotSaveException(String message) {
		super(message);
	}

	public CouldNotSaveException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouldNotSaveException(Throwable cause) {
		super(cause);
	}
}
