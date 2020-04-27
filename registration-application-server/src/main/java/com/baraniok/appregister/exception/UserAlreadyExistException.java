package com.baraniok.appregister.exception;

public class UserAlreadyExistException extends RuntimeException {
	
	public UserAlreadyExistException() {super();}
	
	public UserAlreadyExistException(String message) { super(message);}
}
