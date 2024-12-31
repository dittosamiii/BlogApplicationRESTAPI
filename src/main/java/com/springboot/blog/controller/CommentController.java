package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "CRUD REST APIs for Comment Resource")
public class CommentController {
	private CommentService commentService;

	// handle method to create comment
	@Operation(summary = "Create Comment REST API", description = "Create Comment REST API is used to save comment into database.")
	@ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable Long postId,
			@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);

	}

	// handle method to get all comments
	@Operation(summary = "Get All Comments REST API", description = "Get All Comments REST API is used to get all the comments from the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable Long postId) {
		return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
	}

	// handle method to get comment by id
	@Operation(summary = "Get Comment By Id REST API", description = "Get Comment By Id REST API is used to get single comment from the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getComment(@PathVariable Long postId, @PathVariable Long commentId) {
		return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
	}

	// handle method to update and existing comment
	@Operation(summary = "Update Comment REST API", description = "Update Comment REST API is used to update a particular comment in the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
			@Valid @RequestBody CommentDto commentDto) {
		return ResponseEntity.ok(commentService.updateCommentById(postId, commentId, commentDto));
	}

	// handle method to delete comment
	@Operation(summary = "Delete Comment REST API", description = "Delete Comment REST API is used to delete a particular comment from the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
		commentService.deleteCommentById(postId, commentId);
		return ResponseEntity.ok("Comment deleted successfully!");
	}
}
