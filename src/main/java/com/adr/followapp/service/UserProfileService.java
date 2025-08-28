package com.adr.followapp.service;

import com.adr.followapp.dto.UserProfileDto;

public interface UserProfileService {

	String addUserProfile(UserProfileDto userProfileDto);
	
	String updateUserProfile(UserProfileDto userProfileDto);
}
