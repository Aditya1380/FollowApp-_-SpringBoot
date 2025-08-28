package com.adr.followapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "followcommentmst_fcm")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String commenttext;
	
	private LocalDateTime commentedAt;
	
	@ManyToOne	
	@JoinColumn(name = "post_id",nullable = false)
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;

	public Comment(long id, String commenttext, LocalDateTime commentedAt, Post post, User user) {
		super();
		this.id = id;
		this.commenttext = commenttext;
		this.commentedAt = commentedAt;
		this.post = post;
		this.user = user;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCommenttext() {
		return commenttext;
	}

	public void setCommenttext(String commenttext) {
		this.commenttext = commenttext;
	}

	public LocalDateTime getCommentedAt() {
		return commentedAt;
	}

	public void setCommentedAt(LocalDateTime commentedAt) {
		this.commentedAt = commentedAt;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
