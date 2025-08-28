package com.adr.followapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adr.followapp.dto.CommentDto;
import com.adr.followapp.service.CommentService;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment APIs", description = "This APIs are related to commenting in post.")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
		
	@GetMapping("/show-all-comment/{postId}")
	public ResponseEntity<?> showAllComment(@PathVariable Long postId) {
		List<CommentDto> commentDto = commentService.getAllCommentsForPost(postId);
		return ResponseEntity.ok(commentDto);		
	}
	
	@PostMapping("/add-comment/{postId}")
	public ResponseEntity<?> createCommentInPost(@PathVariable Long postId, @RequestBody String commentText) {
		CommentDto commentDto = commentService.createComment(postId, commentText);
		return ResponseEntity.ok(commentDto);		
	}

	@PutMapping("update-comment/{postId}")
	public ResponseEntity<?> updateCommentInPost(@PathVariable Long postId, @RequestBody String commentText) {
		CommentDto commentDto = commentService.updateComment(postId, commentText);
		return ResponseEntity.ok(commentDto);		
	}

}
