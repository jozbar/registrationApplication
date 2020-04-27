package com.baraniok.appregister.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {
	
	private HttpStatus status;
	private String message;
	private List<String> errors;
	
	public ApiError(String message, List<String> errors, HttpStatus status) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}
	
	public ApiError(String message, String error, HttpStatus status) {
		super();
		this.status = status;
		this.message = message;
		errors = Collections.singletonList(error);
	}
}
