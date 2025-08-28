package com.adr.followapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adr.followapp.dto.PostContent;
import com.adr.followapp.dto.PostDto;
import com.adr.followapp.service.PostService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/post")
@Tag(name = "POST API", description = "These API will create or delete post only by user that has logged in")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@PostMapping("/create-post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostContent postContent) {
		PostDto postDto = postService.createPost(postContent);
		return new ResponseEntity<>(postDto, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete-post/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
		postService.deletePost(postId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/listall-post/{userId}")
	public ResponseEntity<List<PostDto>> getAllPosts(@PathVariable Long userId) {
		List<PostDto> posts = postService.listAllPost(userId);
		return ResponseEntity.ok(posts);
	}

}
