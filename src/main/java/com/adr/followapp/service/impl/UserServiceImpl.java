package com.adr.followapp.service.impl;

import java.util.HashSet;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adr.followapp.dto.JwtAuthResponse;
import com.adr.followapp.dto.LoginRequest;
import com.adr.followapp.dto.RegisterRequest;
import com.adr.followapp.entity.Role;
import com.adr.followapp.entity.User;
import com.adr.followapp.repository.RoleRepository;
import com.adr.followapp.repository.UserRepository;
import com.adr.followapp.security.JwtUtils;
import com.adr.followapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	    
	}

	@Override
	@Transactional // Ensures the entire operation is atomic
	public String registerUser(RegisterRequest request) {
		String username = request.getUsername();
		String roleName = request.getRoleName();

		if (userRepository.existsByUsername(username)) {
			return "Username '" + username + "' already exists.";
		}

		Role role = roleRepository.findByRoleName(roleName);
		if (role == null) {
			return "role '" + roleName + "' does not exists.";
		}

		User user = new User();
		user.setUsername(username); // Use the validated username directly
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setUserEmail(request.getUserEmail());
		user.setRoles(new HashSet<>());
		user.getRoles().add(role);

		// Save the user
		userRepository.save(user);

		return "User '" + username + "' registered successfully!";
	}

	@Override
	public JwtAuthResponse loginUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUserName(),
						loginRequest.getPassword()
					)
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateToken(authentication);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token);
		return jwtAuthResponse;
	}
}
