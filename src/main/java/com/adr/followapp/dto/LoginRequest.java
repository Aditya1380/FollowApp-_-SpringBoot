package com.adr.followapp.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Username cannot be blank.")
	private String userName;
	
	@NotBlank(message = "Password cannot be blank.")
	private String password;

	public LoginRequest(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public LoginRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
