package com.adr.followapp.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.adr.followapp.dto.FollowResponseDto;
import com.adr.followapp.entity.Follow;
import com.adr.followapp.entity.User;
import com.adr.followapp.entity.UserProfile;
import com.adr.followapp.exception.ResourceNotFoundException;
import com.adr.followapp.repository.FollowRepository;
import com.adr.followapp.repository.UserProfileRepository;
import com.adr.followapp.repository.UserRepository;
import com.adr.followapp.service.FollowService;

import jakarta.transaction.Transactional;

@Service
public class FollowServiceImpl implements FollowService {

	private final UserRepository userRepository;

	private final UserProfileRepository userProfileRepository;

	private final FollowRepository followRepository;

	public FollowServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository,
			FollowRepository followRepository) {
		super();
		this.userRepository = userRepository;
		this.userProfileRepository = userProfileRepository;
		this.followRepository = followRepository;
	}

	@Override
	@Transactional
	public String followUser(String targetUsername) throws BadRequestException {
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		User followUser = userRepository.findByUsername(loggedInUsername)// Getting logged in User Info
				.orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found"));

		User followeeUser = userRepository.findByUsername(targetUsername)// Getting target in User Info
				.orElseThrow(() -> new ResourceNotFoundException("Target user not found"));

		UserProfile followerProfile = followUser.getUserProfile();

		UserProfile followeeProfile = followeeUser.getUserProfile();

		boolean alreadyfollowing = followRepository.findByFollowerAndFollowee(followerProfile, followeeProfile)
				.isPresent();
		if (alreadyfollowing) {
			throw new BadRequestException("You were already following the user(" + targetUsername + ")");
		}

		Follow follow = new Follow();
		follow.setFollowedAt(LocalDateTime.now());
		follow.setFollowee(followeeProfile);
		follow.setFollower(followerProfile);

		followRepository.save(follow);

		return "Now following " + followeeProfile.getFirstName() + " " + followeeProfile.getLastName();
	}

	@Override
	@Transactional
	public String unfollowUser(String targetUsername) throws BadRequestException {
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		System.out.println("Geting Login Username " + loggedInUsername);
		User followUser = userRepository.findByUsername(loggedInUsername)// Getting logged in User Info
				.orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found"));

		System.out.println("Login Username := " + loggedInUsername);

		System.out.println("Geting Login Username " + targetUsername);
		User followeeUser = userRepository.findByUsername(targetUsername)// Getting target in User Info
				.orElseThrow(() -> new ResourceNotFoundException("Target user not found"));

		UserProfile followerProfile = followUser.getUserProfile();

		UserProfile followeeProfile = followeeUser.getUserProfile();

		boolean alreadyfollowing = followRepository.findByFollowerAndFollowee(followerProfile, followeeProfile)
				.isPresent();
		if (!alreadyfollowing) {
			throw new BadRequestException("You are not following the user(" + targetUsername + ")");
		}

		User user = userRepository.findByUsername(targetUsername)
				.orElseThrow(() -> new RuntimeException("You do not follow this user"));

		System.out.println("User now unfollowing the user(" + targetUsername + ")");
		followRepository.deleteByFollowerAndFollowee(followerProfile, followeeProfile);

		return "User(" + targetUsername + ") unfollowed Successful";
	}

	@Override
	public List<FollowResponseDto> followingList(String targetUsername) {
		List<Follow> followerRelationships = new ArrayList<Follow>();
		List<User> followerUserRelationships = new ArrayList<User>();

		User followeeUser = userRepository.findByUsername(targetUsername)
				.orElseThrow(() -> new ResourceNotFoundException("Target user not found"));

		UserProfile followeeProfile = followeeUser.getUserProfile();

		followerRelationships = followRepository.findByFollowee(followeeProfile);

		return followerRelationships.stream().map(follow -> {
			FollowResponseDto dto = new FollowResponseDto();
			dto.setFollowerUsername(follow.getFollower().getUser().getUsername());
			dto.setFolloweeUsername(follow.getFollowee().getUser().getUsername());
			dto.setFollowedAt(follow.getFollowedAt());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public long followersCount(String targetUsername) {
		User followers = userRepository.findByUsername(targetUsername)
				.orElseThrow(() -> new ResourceNotFoundException("0 followers"));

		long numberOfFollowers = followRepository.countByFollower_User_Username(targetUsername);

		return numberOfFollowers;
	}

	@Override
	public long followingCount(String targetUsername) {
		User followers = userRepository.findByUsername(targetUsername)
				.orElseThrow(() -> new ResourceNotFoundException("0 followers"));

		long numberofFollowingCount = followRepository.countByFollowee_User_Username(targetUsername);

		return numberofFollowingCount;
	}

}
