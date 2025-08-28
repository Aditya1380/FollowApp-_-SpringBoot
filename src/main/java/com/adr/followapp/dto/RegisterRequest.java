package com.adr.followapp.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

	@NotBlank(message = "Username is mandatory")
	private String username;
	
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@NotBlank(message = "Email is mandatory")
	private String userEmail;
	
	@NotBlank(message = "Role is mandatory")
	private String roleName;	

	public RegisterRequest(String username, String password, String userEmail, String roleName) {
		super();
		this.username = username;
		this.password = password;
		this.userEmail = userEmail;
		this.roleName = roleName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}	
}
