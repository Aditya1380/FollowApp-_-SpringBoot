package com.adr.followapp.dto;

import java.time.LocalDateTime;

public class CommentDto {

	private Long id;
	private String text;
	private String username;
	private LocalDateTime createdAt;

	public CommentDto(Long id, String text, String username, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.text = text;
		this.username = username;
		this.createdAt = createdAt;
	}

	public CommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
