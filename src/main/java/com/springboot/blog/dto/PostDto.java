package com.springboot.blog.dto;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "PostDto Model Information")
public class PostDto {
	@Schema(description = "Blog Post Id")
	private Long id;
	@Schema(description = "Blog Post Title")
	@NotEmpty
	@Size(min = 2, message = "Title field should have atleast 2 characters.")
	private String title;

	@Schema(description = "Blog Post Description")
	@NotEmpty
	@Size(min = 10, message = "Description field should have atleast 10 characters.")
	private String description;

	@Schema(description = "Blog Post Content")
	@NotEmpty
	private String content;
	@Schema(description = "Blog Post Comments")
	private Set<CommentDto> comments;
	@Schema(description = "Blog Post Category")
	private Long categoryId;
}
