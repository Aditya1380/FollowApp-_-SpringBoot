package com.adr.followapp.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adr.followapp.entity.Role;
import com.adr.followapp.entity.User;
import com.adr.followapp.exception.ResourceNotFoundException;
import com.adr.followapp.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username) .orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found"));

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> 
			new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
			.collect(Collectors.toList());
	}

}
