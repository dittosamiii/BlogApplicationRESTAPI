package com.springboot.blog.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(@RequestBody PostDto postDto) {

		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

		Post post = modelMapper.map(postDto, Post.class);
		post.setCategory(category);
		Post savedPost = postRepository.save(post);
		return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		PageRequest pageAble = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> page = postRepository.findAll(pageAble);
		List<Post> posts = page.getContent();
		List<PostDto> postDtoContent = posts.stream().map((entity) -> modelMapper.map(entity, PostDto.class)).toList();

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtoContent);
		postResponse.setPageNo(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setLast(page.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Long id) {
		return modelMapper.map(
				postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)),
				PostDto.class);
	}

	@Override
	public PostDto updatePostById(PostDto postDto, Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		Post savedPost = postRepository.save(post);
		return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public void deletePost(Long id) {
		postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.deleteById(id);
	}

	@Override
	public List<PostDto> findByCategory(Long categoryId) {
		categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		return posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).toList();
	}

}
