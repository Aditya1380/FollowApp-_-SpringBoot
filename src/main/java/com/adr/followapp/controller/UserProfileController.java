package com.adr.followapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adr.followapp.dto.UserProfileDto;
import com.adr.followapp.service.UserProfileService;


import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User Profile APIs",description = "In this controller there are 2 api add and update user profile API.")
@RequestMapping("/api/user-profile")
public class UserProfileController {

	private final UserProfileService userProfileService;

	public UserProfileController(UserProfileService userProfileService) {
		super();
		this.userProfileService = userProfileService;
	}
	
	@PostMapping("/create-userprofile")
	public ResponseEntity<String> createUserProfileController(@RequestBody UserProfileDto userProfileDto){
		String message = userProfileService.addUserProfile(userProfileDto);
		return ResponseEntity.ok(message);
	}

	@PutMapping("/update-userprofile")
	public ResponseEntity<String> updateUserProfileController(@RequestBody UserProfileDto userProfileDto){
		String message = userProfileService.updateUserProfile(userProfileDto);
		return ResponseEntity.ok(message);
	}
}

