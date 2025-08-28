package com.adr.followapp.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.adr.followapp.dto.FollowResponseDto;
import com.adr.followapp.entity.Follow;
import com.adr.followapp.entity.User;

public interface FollowService {

	String followUser(String targetusername) throws BadRequestException;
	
	String unfollowUser(String targetUsername) throws BadRequestException;
	
	List<FollowResponseDto> followingList(String targetUsername);
	
	long followersCount(String targetUsername);
	
	long followingCount(String targetUsername);
}
