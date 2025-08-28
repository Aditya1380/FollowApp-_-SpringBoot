package com.adr.followapp.dto;

import java.time.LocalDateTime;

import com.adr.followapp.entity.User;

public class PostDto {

	private String content;

	private LocalDateTime postCreatedAt;

	private String username;

	public PostDto(String content, LocalDateTime postCreatedAt, String username) {
		super();
		this.content = content;
		this.postCreatedAt = postCreatedAt;
		this.username = username;
	}

	public PostDto() {
		super();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getPostCreatedAt() {
		return postCreatedAt;
	}

	public void setPostCreatedAt(LocalDateTime postCreatedAt) {
		this.postCreatedAt = postCreatedAt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
