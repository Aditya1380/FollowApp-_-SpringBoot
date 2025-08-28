package com.adr.followapp.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

	private final JwtUtils jwtUtils;

	private final CustomUserDetailService customUserDetailService;

	public JwtRequestFilter(JwtUtils jwtUtils, CustomUserDetailService customUserDetailService) {
		super();
		this.jwtUtils = jwtUtils;
		this.customUserDetailService = customUserDetailService;
	}

	private static final List<String> PUBLIC_PATHS = Arrays.asList("/api/auth/register", "/api/auth/login",
			"/swagger-ui", "/swagger-ui.html", "/v3/api-docs");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (isPublicPath(request.getRequestURI())) {
			logger.warn("This is a public Request");
			filterChain.doFilter(request, response);
			return; // Exit the filter early
		}

		try {
			String jwtToken = null;
			String username = null;
			String authHeader = request.getHeader("Authorization");

			// Check for valid Authorization header and extract the JWT token
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				jwtToken = authHeader.substring(7);
				try {
					username = jwtUtils.extractUsername(jwtToken);
				} catch (Exception e) {

					logger.warn("JWT token extraction failed. Token: {}", jwtToken, e);
					filterChain.doFilter(request, response);
					return;
				}
			}

			// If username is found and no authentication is set in the context
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				// Load UserDetails from your service
				UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);

				logger.info("going to validate method.!!");
				if (jwtUtils.validateToken(jwtToken, userDetails)) {
					logger.info("Into validate method.!!");
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					logger.info("Authentication successful for user: {}", username);
				} else {
					logger.warn("JWT token validation failed for user: {}", username);
				}
			}
			filterChain.doFilter(request, response);

		} catch (Exception e) {
			logger.error("Error during JWT authentication filter processing.", e);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private boolean isPublicPath(String uri) {
		return PUBLIC_PATHS.stream().anyMatch(path -> uri.startsWith(path));
	}

}
