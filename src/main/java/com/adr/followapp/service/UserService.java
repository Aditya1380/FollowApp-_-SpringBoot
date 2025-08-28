package com.adr.followapp.service;

import com.adr.followapp.dto.JwtAuthResponse;
import com.adr.followapp.dto.LoginRequest;
import com.adr.followapp.dto.RegisterRequest;

public interface UserService {
	public String registerUser(RegisterRequest request) ;
	
	public JwtAuthResponse loginUser(LoginRequest loginRequest);
}
