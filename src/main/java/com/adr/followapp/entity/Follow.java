package com.adr.followapp.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "followmst_fm")
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "follower_id", nullable = false)
	@JsonIgnore 
	private UserProfile follower;

	@ManyToOne
	@JoinColumn(name = "followee_id", nullable = false)
	@JsonIgnore 
	private UserProfile followee;

	private LocalDateTime followedAt;

	public Follow(long id, UserProfile follower, UserProfile followee, LocalDateTime followedAt) {
		super();
		this.id = id;
		this.follower = follower;
		this.followee = followee;
		this.followedAt = followedAt;
	}

	public Follow() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserProfile getFollower() {
		return follower;
	}

	public void setFollower(UserProfile follower) {
		this.follower = follower;
	}

	public UserProfile getFollowee() {
		return followee;
	}

	public void setFollowee(UserProfile followee) {
		this.followee = followee;
	}

	public LocalDateTime getFollowedAt() {
		return followedAt;
	}

	public void setFollowedAt(LocalDateTime followedAt) {
		this.followedAt = followedAt;
	}

}
