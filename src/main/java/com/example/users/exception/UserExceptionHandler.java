package com.example.users.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler 
{

	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleExceptions(Exception ex,WebRequest req)
	{
		
		String message = ex.getLocalizedMessage();
		if(message==null) message=ex.toString();
		ErrorMessage errorMessage = new ErrorMessage(message,new Date());
		
		return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		
		
	}
	
	
	
}
