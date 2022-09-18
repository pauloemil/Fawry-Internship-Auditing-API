package com.example.demo.Exceptions;

import java.io.IOException;

public class WrongJsonBodyException extends IOException {
	public WrongJsonBodyException() {
	}

	public WrongJsonBodyException(String message) {
		super(message);
	}
}

