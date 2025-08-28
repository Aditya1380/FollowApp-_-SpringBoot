package com.adr.followapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JwtRequestFilter jwtRequestFilter;
	
	public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
		super();
		this.jwtRequestFilter = jwtRequestFilter;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf -> csrf.disable())
		    .authorizeHttpRequests(auth -> auth
		    	.requestMatchers("/api/logged/hello").hasRole("USER")
		        .requestMatchers(
		            "/api/auth/**",
		            "/api/role/**",
		            "/swagger-ui/**",
		            "/swagger-ui.html",
		            "/v3/api-docs/**"
		        ).permitAll()
		        .anyRequest().authenticated()
		    ).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}

