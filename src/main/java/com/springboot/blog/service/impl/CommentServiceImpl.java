package com.springboot.blog.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(Long postId, CommentDto commentDto) {
		Comment comment = modelMapper.map(commentDto, Comment.class);

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		comment.setPost(post);

		Comment savedComment = commentRepository.save(comment);

		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(Long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map((comment) -> modelMapper.map(comment, CommentDto.class)).toList();
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		Comment comment = commentBelongsPost(postId, commentId);
		return modelMapper.map(comment, CommentDto.class);
	}

	@Override
	public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {
		Comment comment = commentBelongsPost(postId, commentId);

		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());

		return modelMapper.map(commentRepository.save(comment), CommentDto.class);
	}

	@Override
	public void deleteCommentById(Long postId, Long commentId) {
		commentBelongsPost(postId, commentId);
		commentRepository.deleteById(commentId);
	}

	public Comment commentBelongsPost(Long postId, Long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException("Comment does not belongs to this post.");
		}
		return comment;
	}

}
