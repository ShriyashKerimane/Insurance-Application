package com.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorObject {

	private Integer statusCode;
	private String message;
	
}
