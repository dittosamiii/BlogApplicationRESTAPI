package com.springboot.blog.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "PostResponse Model Information")
public class PostResponse {
	@Schema(description = "Blog PostResponse Content")
	private List<PostDto> content;
	@Schema(description = "Blog PostResponse Page No")
	private int pageNo;
	@Schema(description = "Blog PostResponse Page Size")
	private int pageSize;
	@Schema(description = "Blog PostResponse Total Elements")
	private long totalElements;
	@Schema(description = "Blog PostResponse Total Pages")
	private int totalPages;
	@Schema(description = "Blog PostResponse Last")
	private boolean last;
}
