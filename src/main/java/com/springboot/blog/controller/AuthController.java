package com.springboot.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.JWTAuthResponseDto;
import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;
import com.springboot.blog.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "REST APIs for Auth Resource")
public class AuthController {

	private AuthService authService;

	// Build API for login REST API
	@Operation(summary = "Login REST API", description = "Login REST API is used to login the user and get JWT Token from the Application.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
		String token = authService.login(loginDto);
		JWTAuthResponseDto authResponse = new JWTAuthResponseDto();
		authResponse.setAccessToken(token);
		return ResponseEntity.ok(authResponse);
	}

	// Build API for register REST API
	@Operation(summary = "Register REST API", description = "Register REST API is used to register the user.")
	@ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
		return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
	}

}
