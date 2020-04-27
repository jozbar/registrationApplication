package com.baraniok.appregister.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
//import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({UserNotFoundException.class, UserAlreadyExistException.class, ConstraintViolationException.class})
	public ResponseEntity<Object> handleException(Exception e) {
		if (e instanceof UserNotFoundException) {
			return handleCommonException(e, e.getMessage(), HttpStatus.NOT_FOUND);
		} else if (e instanceof UserAlreadyExistException) {
			return handleCommonException(e, e.getMessage(), HttpStatus.CONFLICT);
		} else if (e instanceof ConstraintViolationException) {
			return handleConstraintViolation((ConstraintViolationException)e);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	private ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e) {
		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " +
			violation.getPropertyPath() + ": " + violation.getMessage());
		}
	
		return  handleCommonException(e, errors, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		return handleCommonException(ex, errors, HttpStatus.BAD_REQUEST);
	}
	
	private ResponseEntity<Object> handleCommonException(Exception ex, List<String> errors, HttpStatus status) {
		ApiError apiError = new ApiError(ex.getLocalizedMessage(), errors, status);
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	private ResponseEntity<Object> handleCommonException(Exception ex, String error, HttpStatus status) {
		ApiError apiError = new ApiError(ex.getLocalizedMessage(), error, status);
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
