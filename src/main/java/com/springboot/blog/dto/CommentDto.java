package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "CommentDto Model Information")
public class CommentDto {
	@Schema(description = "Blog Comment Id")
	private Long id;

	@Schema(description = "Blog Comment Name")
	@NotEmpty(message = "Name should not be empty.")
	private String name;
	
	@Schema(description = "Blog Comment Email")
	@NotEmpty(message = "Email should not be empty.")
	@Email
	private String email;
	
	@Schema(description = "Blog Comment Body")
	@NotEmpty(message = "Body should not be empty.")
	@Size(min = 3, message = "Body should have atleast 3 characters.")
	private String body;
}
