package com.springboot.blog.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDetails {
	private LocalDateTime date;
	private String message;
	private String details;
}
