package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {
	private PostService postService;

	// handle method to create post
	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "Create Post REST API", description = "Create Post REST API is used to save post into database.")
	@ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	// handle method to get all posts
	@Operation(summary = "Get All Posts REST API", description = "Get All Posts REST API is used to get all the posts from the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@GetMapping
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

	) {
		return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
	}

	// handle method to get post by id
	@Operation(summary = "Get Post By Id REST API", description = "Get Post By Id REST API is used to get single post from the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@GetMapping("{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	// handle method to update post by id
	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "Update Post REST API", description = "Update Post REST API is used to update a particular post in the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable Long id) {
		return ResponseEntity.ok(postService.updatePostById(postDto, id));
	}

	// handle method to delete post by id
	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "Delete Post REST API", description = "Delete Post REST API is used to delete a particular post from the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletePostById(@PathVariable Long id) {
		postService.deletePost(id);
		return ResponseEntity.ok("Post deleted successfully!");
	}

	// handle method to get all posts by category id
	@Operation(summary = "Get By Category REST API", description = "Get By Category REST API is used to get all the posts based on the category from the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@GetMapping("category/{categoryId}")
	public ResponseEntity<List<PostDto>> getByCategory(@PathVariable Long categoryId) {
		return ResponseEntity.ok(postService.findByCategory(categoryId));
	}
}
