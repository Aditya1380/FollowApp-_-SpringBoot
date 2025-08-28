package com.adr.followapp.dto;

public class JwtAuthResponse {
	private String accessToken;
	private String tokenType = "Bearer";

	public JwtAuthResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public JwtAuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}



	// Getters and Setters
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

}
