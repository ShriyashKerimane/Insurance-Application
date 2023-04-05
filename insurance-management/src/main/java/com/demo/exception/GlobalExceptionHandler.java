package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleClientNotFoundException(ClientNotFoundException e){
		ErrorObject errorObj = new ErrorObject();
		errorObj.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorObj.setMessage(e.getMessage());
		return new ResponseEntity<>(errorObj, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorObject> handlePolicyNotFoundException(PolicyNotFoundException e){
		ErrorObject errorObj = new ErrorObject();
		errorObj.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorObj.setMessage(e.getMessage());
		return new ResponseEntity<>(errorObj, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleClaimNotFoundException(ClaimNotFoundException e){
		ErrorObject errorObj = new ErrorObject();
		errorObj.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorObj.setMessage(e.getMessage());
		return new ResponseEntity<>(errorObj, HttpStatus.NOT_FOUND);
	}
	
}
