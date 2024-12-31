package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "JWTAuthResourceDto Model Information")
public class JWTAuthResponseDto {
	@Schema(description = "Blog JWTAuthResponse Access Token")
	private String accessToken;
	@Schema(description = "Blog JWTAuthResponse Token Type")
	private String TokenType = "Bearer";
}
