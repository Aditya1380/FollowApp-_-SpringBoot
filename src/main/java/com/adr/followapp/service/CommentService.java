package com.adr.followapp.service;

import java.util.List;

import com.adr.followapp.dto.CommentDto;

public interface CommentService{

	List<CommentDto> getAllCommentsForPost(Long postId);
	CommentDto createComment(Long postId, String commentText);
	CommentDto updateComment(Long commentId, String updatedComment);
}
