package com.cloudcomp.ccoms.emp.advice;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cloudcomp.ccoms.emp.controller.ResourceNotFoundException;

@ControllerAdvice
public class EmpControllerAdvice extends ResponseEntityExceptionHandler {

	//handles jvm response exception in understandable format
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return new ResponseEntity<>("Please provide proper Id,ID should be in Integer form. ",HttpStatus.BAD_REQUEST);
	}

	//Handles Custom Exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException rnf){
		return new ResponseEntity<>("ID not found in DB",HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>("Http method type is mismatched,Please provide valid method type.",HttpStatus.NOT_FOUND);
		}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> handleNullPointerException(NullPointerException np){
		return new ResponseEntity<>("Value is null, Please provide some value",HttpStatus.BAD_REQUEST);
		
	}


}
