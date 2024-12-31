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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/categories")
@Tag(name = "CRUD REST APIs for Category Resource")
public class CategoryController {
	private CategoryService categoryService;

	// Build Add Category REST API
	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "Add Category REST API", description = "Add Category REST API is used to add the category into database.")
	@ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
	}

	// Get Category By Id REST API
	@Operation(summary = "Get Category By Id REST API", description = "Get Category By Id REST API is used to get single category from the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@GetMapping("{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId) {
		return ResponseEntity.ok(categoryService.getCategory(categoryId));
	}

	// Get All Categories REST API
	@Operation(summary = "Get All Categories REST API", description = "Get All Categories REST API is used to get all the categories from the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	// Update Category REST API
	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "Update Category REST API", description = "Update Category REST API is used to update a particular category in the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@PutMapping("{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
			@PathVariable Long categoryId) {
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
	}

	// Delete Category REST API
	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "Delete Category REST API", description = "Delete Category REST API is used to delete a particular category from the database.")
	@ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
	@DeleteMapping("{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted successfully!");
	}

}
