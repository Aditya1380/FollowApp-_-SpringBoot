package com.adr.followapp.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	private static final String SECRET_KEY = "c2VjcmV0X2tleXNlY3JldF9rZXlzZWNyZXRfa2V5c2VjcmV0X2tleXNlY3JldF9rZXlzZWNyZXRfa2V5c2VjcmV0X2tleQ==";

	private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	public String generateToken(Authentication authentication) {
		return Jwts.builder().subject(authentication.getName()).signWith(KEY).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20 )).compact();//1000(milli-seconds) * 60(seconds)
	}

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public Claims extractClaims(String token) {
		return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload();
	}

	public List<String> extractRoles(String token) {
	    Claims claims = extractAllClaims(token);
	    
	    // Check if the "roles" claim exists and is a List
	    if (claims.containsKey("roles") && claims.get("roles") instanceof List) {
	        // Safe casting to List<String>
	        return (List<String>) claims.get("roles");
	    }
	    
	    // Return an empty list instead of null if the claim is missing or invalid
	    return new ArrayList<>(); 
	}

	private Claims extractAllClaims(String token) {
		return (Claims) Jwts.parser().verifyWith(KEY).build().parse(token).getPayload();
	}

	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		try {

			final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
			
		} catch (MalformedJwtException e) {
			System.out.println("Invalid JWT token: " + e.getMessage());
		}catch(ExpiredJwtException e) {
			System.out.println("Expired JWT token:" + e.getMessage());
		}catch(UnsupportedJwtException e) {
			System.out.println("Unsupported JWT token:" + e.getMessage());
		}catch(IllegalArgumentException e) {
			System.out.println("IllegalArgument JWT token:" + e.getMessage());
		}		
		return false;
	}

}
