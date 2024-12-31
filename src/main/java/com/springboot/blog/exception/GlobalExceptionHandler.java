package com.springboot.blog.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.blog.dto.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// handle specific exceptions
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// handle global exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAuthorizationDeniedException(AuthorizationDeniedException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String fieldValue = error.getDefaultMessage();
			errors.put(fieldName, fieldValue);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
