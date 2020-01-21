package com.andersonmarques.youtubenotes.services;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
			.setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
			.setIssuer("YouTube Notes API")
			.setSubject(username)
			.signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
			.compact();
	}

	public Optional<Authentication> getAuthenticationFromRequest(HttpServletRequest request) {
		String token = request.getHeader(HEADER_KEY);
		try {
			String username = Jwts
				.parser()
				.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
				.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody()
				.getSubject();
			return Optional.of(buildAuthentication(username));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	private Authentication buildAuthentication(String username) {
		return new UsernamePasswordAuthenticationToken(username, "", Collections.emptyList());
	}
}
