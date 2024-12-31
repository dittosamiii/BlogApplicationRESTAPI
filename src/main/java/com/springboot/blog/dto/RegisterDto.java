package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "RegisterDto Model Information")
public class RegisterDto {
	@Schema(description = "Blog Register Name")
	@NotEmpty
	private String name;
	@Schema(description = "Blog Register Username")
	@NotEmpty
	private String username;
	@Schema(description = "Blog Register Email")
	@NotEmpty
	@Email
	private String email;
	@Schema(description = "Blog Register Password")
	@NotEmpty
	private String password;
}
