package com.adr.followapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adr.followapp.dto.CommentDto;
import com.adr.followapp.entity.Comment;
import com.adr.followapp.entity.Post;
import com.adr.followapp.entity.User;
import com.adr.followapp.exception.ResourceNotFoundException;
import com.adr.followapp.repository.CommentRepository;
import com.adr.followapp.repository.PostRepository;
import com.adr.followapp.repository.UserRepository;
import com.adr.followapp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private final PostRepository postRepository;

	private final UserRepository userRepository;

	private final CommentRepository commentRepository;

	public CommentServiceImpl(PostRepository postRepository, UserRepository userRepository,
			CommentRepository commentRepository) {
		super();
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	public List<CommentDto> getAllCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post with Id" + postId + " now found"));

		List<Comment> comments = commentRepository.findByPost(post);

		return comments.stream().map(comment -> new CommentDto(comment.getId(), comment.getCommenttext(),
				comment.getUser().getUsername(), comment.getCommentedAt())).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public CommentDto createComment(Long postId, String commentText) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post not found with id :" + postId));

		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByUsername(loggedInUsername)
				.orElseThrow(() -> new IllegalArgumentException("User not found: " + loggedInUsername));

		Comment comment = new Comment();
		comment.setCommenttext(commentText);
		comment.setPost(post);
		comment.setUser(user);
		comment.setCommentedAt(LocalDateTime.now());

		Comment savedComment = commentRepository.save(comment);

		return new CommentDto(savedComment.getId(), savedComment.getCommenttext(), savedComment.getUser().getUsername(),
				savedComment.getCommentedAt());

	}

	@Override
	public CommentDto updateComment(Long commentId, String updatedComment) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("commentId not found with id :" + commentId));

		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByUsername(loggedInUsername)
				.orElseThrow(() -> new IllegalArgumentException("User not found: " + loggedInUsername));
		
		if (!comment.getUser().getUsername().equals(loggedInUsername)) {
            throw new IllegalArgumentException("Unauthorized: You do not have permission to update this comment.");
        }
		
		comment.setCommenttext(updatedComment);
        Comment updatedCommentText = commentRepository.save(comment);

        // Map the updated entity to a DTO for the response
        return new CommentDto(
        		updatedCommentText.getId(),
        		updatedCommentText.getCommenttext(),
        		updatedCommentText.getUser().getUsername(),
        		updatedCommentText.getCommentedAt()
        );
	}

}
