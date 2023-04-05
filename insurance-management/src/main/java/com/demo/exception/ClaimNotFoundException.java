package com.demo.exception;

@SuppressWarnings("serial")
public class ClaimNotFoundException extends RuntimeException{
	
	public ClaimNotFoundException(String message) {
		super(message);
	}

}
