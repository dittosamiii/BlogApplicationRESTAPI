package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo, int PageSize, String sortBy, String sortDir);

	PostDto getPostById(Long id);

	PostDto updatePostById(PostDto postDto, Long id);

	void deletePost(Long id);
	
	List<PostDto> findByCategory(Long categoryId);
}
