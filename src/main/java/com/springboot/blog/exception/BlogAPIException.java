package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlogAPIException extends RuntimeException {

	public BlogAPIException(String message) {
		super(message);
	}
}
