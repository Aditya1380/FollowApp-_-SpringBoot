package com.adr.followapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adr.followapp.entity.User;
import com.adr.followapp.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
	Optional<UserProfile> findByUser(User user);
//	UserProfile findByUser1(User user);
//	UserProfile findByUserEmail(String userEmail);
}
