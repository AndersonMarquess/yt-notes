package com.andersonmarques.youtubenotes.services;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.expiration.millis}")
	private long expirationMillis;
	@Value("${jwt.secret}")
	private String secret;
	public final String TOKEN_PREFIX = "Bearer ";
	public final String HEADER_KEY = "Authorization";

	public void addAuthenticationToResponse(HttpServletResponse response, String username) {
		String token = generateToken(username);
		response.addHeader(HEADER_KEY, token);
		response.addHeader("access-control-expose-headers", HEADER_KEY);
	}

	private String generateToken(String username) {
		return Jwts.builder()
			.setIssuedAt(new Date())
			.setExpiration(new Date(expirationMillis))
			.setIssuer("YouTube Notes API")
			.setSubject(username)
			.signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
			.compact();
	}
}
