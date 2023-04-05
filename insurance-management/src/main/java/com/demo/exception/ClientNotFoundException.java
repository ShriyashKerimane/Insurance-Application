package com.demo.exception;

@SuppressWarnings("serial")
public class ClientNotFoundException extends RuntimeException{

	public ClientNotFoundException(String message) {
		super(message);
	}
	
}
