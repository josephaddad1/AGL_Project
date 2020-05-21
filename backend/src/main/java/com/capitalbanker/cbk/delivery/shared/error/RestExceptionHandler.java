package com.capitalbanker.cbk.delivery.shared.error;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.capitalbanker.cbk.delivery.shared.response.ErrorDetailsResponse;
import com.fasterxml.jackson.databind.JsonMappingException;


@EnableWebMvc
@ControllerAdvice
public class RestExceptionHandler   {

	@ExceptionHandler(Throwable.class)
	public final ResponseEntity<ErrorDetailsResponse> handleExceptions(Throwable ex, WebRequest request) {
		ErrorDetailsResponse errorDetails = new ErrorDetailsResponse(HttpStatus.BAD_REQUEST.toString(), new Date(), ex.getMessage());
		System.out.print(errorDetails.getMessage());
		return new ResponseEntity<ErrorDetailsResponse>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
//	 other exception handlers below

}