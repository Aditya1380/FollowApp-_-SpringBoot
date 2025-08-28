package com.adr.followapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adr.followapp.dto.JwtAuthResponse;
import com.adr.followapp.dto.LoginRequest;
import com.adr.followapp.dto.RegisterRequest;
import com.adr.followapp.security.JwtUtils;
import com.adr.followapp.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Tag(description = "Register and Login APIs", name = "Security APIs")
@RequestMapping("api/auth")
public class UserController {

	private final UserService userService;
	private final JwtUtils jwtUtils;

	public UserController(UserService userService,JwtUtils jwtUtils) {
		super();
		this.userService = userService;
		this.jwtUtils = jwtUtils;	
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
		String message = userService.registerUser(request);
		return new ResponseEntity<String>(message,HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {
		JwtAuthResponse authResponse = userService.loginUser(loginRequest);
		return ResponseEntity.ok(authResponse);
	}

}
