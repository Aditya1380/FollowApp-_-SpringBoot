package com.adr.followapp.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adr.followapp.dto.FollowResponseDto;
import com.adr.followapp.service.FollowService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/follow")
@Tag(name = "Follow APIs", description = "In this controller I have several api which focuses on follow and User profile of application")
public class FollowController {

	private final FollowService followService;

	public FollowController(FollowService followService) {
		super();
		this.followService = followService;
	}

	@PostMapping("/follow-user/{targetUser}")
	public ResponseEntity<String> followUser(@PathVariable String targetUser) {
		String message = null;
		try {
			message = followService.followUser(targetUser);
		} catch (BadRequestException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(message, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/unfollow-user/{targetUser}")
	public ResponseEntity<String> unfollowUser(@PathVariable String targetUser) throws BadRequestException{
		String message = followService.unfollowUser(targetUser);
		return ResponseEntity.ok(message);
	}

	@GetMapping("/list-follow-user/{targetUser}")
	public ResponseEntity<List<FollowResponseDto>> listFollowUser(@PathVariable String targetUser) {
		return ResponseEntity.ok(followService.followingList(targetUser)); 
	}
	
	@GetMapping("/count-followers/{targetUser}")
	public ResponseEntity<Long> followersCount(@PathVariable String targetUser){
		return ResponseEntity.ok(followService.followersCount(targetUser));
	}
	
	@GetMapping("/count-following/{targetUser}")
	public ResponseEntity<Long> followingCount(@PathVariable String targetUser){
		return ResponseEntity.ok(followService.followingCount(targetUser));
	}
	
}
