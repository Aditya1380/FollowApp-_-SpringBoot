package com.adr.followapp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.adr.followapp.dto.PostDto;
import com.adr.followapp.entity.Follow;
import com.adr.followapp.entity.User;
import com.adr.followapp.entity.UserProfile;
import com.adr.followapp.repository.FollowRepository;
import com.adr.followapp.repository.UserProfileRepository;
import com.adr.followapp.repository.UserRepository;
import com.adr.followapp.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	private final UserRepository userRepository;

	private final UserProfileRepository userProfileRepository;

	private final FollowRepository followRepository;

	private final ObjectMapper objectMapper;

	public NotificationServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository,
			FollowRepository followRepository, ObjectMapper objectMapper) {
		super();
		this.userRepository = userRepository;
		this.userProfileRepository = userProfileRepository;
		this.followRepository = followRepository;
		this.objectMapper = objectMapper;
	}

	
	@Override
	@KafkaListener(topics = "post-notification", groupId = "notification-service-group")
	public void handlePostNotification(String postJson) {
		try {
			logger.info("Received new post notification event from kafka.");
			PostDto postDto = objectMapper.readValue(postJson, PostDto.class);

			// 1. Find the User who posted
			User user = userRepository.findByUsername(postDto.getUsername())
					.orElseThrow(() -> new RuntimeException("User Not Found :" + postDto.getUsername()));

			// 2. Find the UserProfile associated with that User
			UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(
					() -> new RuntimeException("UserProfile not found for user: " + postDto.getUsername()));

			// 3. Find all followers of that UserProfile
			List<Follow> followers = followRepository.findByFollower(userProfile);

			// 4. Send email notification to each follower
			for (Follow follow : followers) {
				String followerEmail = follow.getFollowee().getUserEmail();
				logger.info("---------------------------------------------");
				logger.info("Simulating email notification to: " + followerEmail);
				logger.info("Subject: New post from " + postDto.getUsername());
				logger.info("Body: " + postDto.getContent());
				logger.info("---------------------------------------------");				
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
