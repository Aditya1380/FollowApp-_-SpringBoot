package com.adr.followapp.service.impl;


import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.adr.followapp.dto.UserProfileDto;
import com.adr.followapp.entity.User;
import com.adr.followapp.entity.UserProfile;
import com.adr.followapp.exception.ResourceNotFoundException;
import com.adr.followapp.repository.UserProfileRepository;
import com.adr.followapp.repository.UserRepository;
import com.adr.followapp.service.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService{

    private final AuthenticationManager authenticationManager;
	private final UserProfileRepository userProfileRepository;
	private final UserRepository userRepository;

	public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserRepository userRepository, AuthenticationManager authenticationManager) {
		this.userProfileRepository = userProfileRepository;
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public String addUserProfile(UserProfileDto userProfileDto) {
		// 1. Get the email of the currently authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return "User not authenticated.";
		}
		User user= userRepository.findByUsername(authentication.getName())
				 .orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found"));
		
//		2. Check if a profile already exists for this user
		Optional<UserProfile> existingProfile = userProfileRepository.findByUser(user);
		if (existingProfile.isPresent()) {
			return "User profile already exists for user: " + authentication.getName();
		}
		
//		3. Manually map DTO to a new UserProfile entity
		UserProfile userProfile = new UserProfile();
		userProfile.setUserEmail(user.getUserEmail()); // This might be redundant if the user can't change their email
		userProfile.setFirstName(userProfileDto.getFirstName());
		userProfile.setLastName(userProfileDto.getLastName());
		userProfile.setAddress(userProfileDto.getAddress());
		userProfile.setDateOfBirth(userProfileDto.getDateOfBirth());
		userProfile.setUser(user); // Set the one-to-one relationship
		
//	    4. Save the new user profile
		UserProfile newUserProfile = userProfileRepository.save(userProfile);

		return "User profile for " + newUserProfile.getFirstName() + " has been added successfully.";
	}

	@Override
	public String updateUserProfile(UserProfileDto userProfileDto) {
// 		1. Get the email of the currently authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new IllegalStateException("User not authenticated.");
		}
		
		User user = userRepository.findByUsername(authentication.getName()) .orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found"));
		
		UserProfile existingProfile = userProfileRepository.findByUser(user)
		        .orElseThrow(() -> new ResourceNotFoundException("User profile not found for this user. Cannot update."));
// 		2. Update the fields of the existing user profile from the DTO
		existingProfile.setUserEmail(user.getUserEmail()); 
		existingProfile.setFirstName(userProfileDto.getFirstName());
		existingProfile.setLastName(userProfileDto.getLastName());
		existingProfile.setAddress(userProfileDto.getAddress());
		existingProfile.setDateOfBirth(userProfileDto.getDateOfBirth());
		existingProfile.setUser(user);
// 		3. Save the updated user profile
		UserProfile updatedUserProfile = userProfileRepository.save(existingProfile);

		return "User profile for " + updatedUserProfile.getFirstName() + " has been updated successfully.";
	}
}
