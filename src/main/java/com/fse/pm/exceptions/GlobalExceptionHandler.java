package com.fse.pm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fse.pm.entity.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(BadRequestException ex){
		ErrorResponse errResponse=new ErrorResponse(
												HttpStatus.BAD_REQUEST.value(),
												ex.getMessage(),
												System.currentTimeMillis());
		return new ResponseEntity<>(errResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(NotFoundException ex){
		ErrorResponse errResponse=new ErrorResponse(
												HttpStatus.NOT_FOUND.value(),
												ex.getMessage(),
												System.currentTimeMillis());
		return new ResponseEntity<>(errResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception ex){
		ErrorResponse errResponse=new ErrorResponse(
												HttpStatus.BAD_REQUEST.value(),
												ex.getMessage(),
												System.currentTimeMillis());
		return new ResponseEntity<>(errResponse,HttpStatus.BAD_REQUEST);
	}
}
