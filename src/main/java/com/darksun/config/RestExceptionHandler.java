package com.darksun.config;

import com.darksun.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {

	public ResponseEntity< ErrorMessage > handle( Exception ex, HttpStatus statusCode ) {
		ErrorMessage message = new ErrorMessage( statusCode, LocalDateTime.now( ),
												 ex.getMessage( ) );
		ex.printStackTrace( );
		return new ResponseEntity<>( message, statusCode );
	}

	@ExceptionHandler( { Exception.class } )
	public ResponseEntity< ErrorMessage > internalServerErrorHandler( Exception ex ) {
		return handle( ex, HttpStatus.INTERNAL_SERVER_ERROR );
	}

	@ExceptionHandler( { EntityNotFoundException.class } )
	public ResponseEntity< ErrorMessage > notFoundHandler( Exception ex ) {
		return handle( ex, HttpStatus.NOT_FOUND );
	}

	@ExceptionHandler( { IllegalArgumentException.class } )
	public ResponseEntity< ErrorMessage > badRequestHandler( Exception ex ) {
		return handle( ex, HttpStatus.BAD_REQUEST );
	}
}
