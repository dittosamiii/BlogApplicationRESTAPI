package com.springboot.blog.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.exception.BlogAPIException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app.jwt-expiration-milliseconds}")
	private Long jwtExpiration;

	// generate JWT token
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();

		Date currentDate = new Date();
		Date endDate = new Date(currentDate.getTime() + jwtExpiration);

		String token = Jwts.builder().subject(username).issuedAt(new Date()).expiration(endDate).signWith(key())
				.compact();
		return token;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// get user name from JWT Token
	public String getUsername(String token) {
		return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
	}

	// validate JWT Token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
			return true;
		} catch (MalformedJwtException exception) {
			throw new BlogAPIException("Invalid JWT Token!");
		} catch(ExpiredJwtException exception) {
			throw new BlogAPIException("JWT Token Expired!");
		} catch(UnsupportedJwtException exception) {
			throw new BlogAPIException("Unsupported JWT Token!");
		} catch(IllegalArgumentException exception) {
			throw new BlogAPIException("JWT claims string is null or empty!");
		}
	}

}
