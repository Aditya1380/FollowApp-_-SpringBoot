package com.adr.followapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adr.followapp.entity.Follow;
import com.adr.followapp.entity.UserProfile;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	List<Follow> findByFollower(UserProfile follower);

	List<Follow> findByFollowee(UserProfile followee);
	
	void deleteByFollowerAndFollowee(UserProfile follower, UserProfile followee);
	
	long countByFollowee_User_Username(String targetUsername);
	
	long countByFollower_User_Username(String targetUsername);

	Optional<Follow> findByFollowerAndFollowee(UserProfile follower, UserProfile followee);
	
}
