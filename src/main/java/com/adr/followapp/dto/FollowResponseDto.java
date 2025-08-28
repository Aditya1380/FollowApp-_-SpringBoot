package com.adr.followapp.dto;

import java.time.LocalDateTime;

public class FollowResponseDto {
	private String followerUsername;
	private String followeeUsername;
	private LocalDateTime followedAt;

	public FollowResponseDto(String followerUsername, String followeeUsername, LocalDateTime followedAt) {
		super();
		this.followerUsername = followerUsername;
		this.followeeUsername = followeeUsername;
		this.followedAt = followedAt;
	}

	public FollowResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFollowerUsername() {
		return followerUsername;
	}

	public void setFollowerUsername(String followerUsername) {
		this.followerUsername = followerUsername;
	}

	public String getFolloweeUsername() {
		return followeeUsername;
	}

	public void setFolloweeUsername(String followeeUsername) {
		this.followeeUsername = followeeUsername;
	}

	public LocalDateTime getFollowedAt() {
		return followedAt;
	}

	public void setFollowedAt(LocalDateTime followedAt) {
		this.followedAt = followedAt;
	}

}
