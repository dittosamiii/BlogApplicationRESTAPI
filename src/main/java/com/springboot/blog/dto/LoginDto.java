package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "LoginDto Model Information")
public class LoginDto {
	@Schema(description = "Blog Login Username Or Email")
	@NotEmpty
	private String usernameOrEmail;
	@Schema(description = "Blog Login Password")
	@NotEmpty
	private String password;
}
