package com.andersonmarques.youtubenotes.utils;

import com.andersonmarques.youtubenotes.builders.UserBuilder;
import com.andersonmarques.youtubenotes.models.User;
import com.andersonmarques.youtubenotes.models.Video;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class VideoControllerUtil {

	private TestRestTemplate client;
	private UserControllerUtil userControllerUtil;
	private HttpHeaders headers;

	public VideoControllerUtil(TestRestTemplate client) {
		this.client = client;
		this.userControllerUtil = new UserControllerUtil(client);
		this.headers = new HttpHeaders();
	}

	public ResponseEntity<String> postVideoWithDefaultUser(Video video) {
		addDefaultJwtTokenToHeader();
		return client.postForEntity("/v1/videos", new HttpEntity<>(video, headers), String.class);
	}

	private void addDefaultJwtTokenToHeader() {
		User user = new UserBuilder().build();
		userControllerUtil.postUser(user);
		String token = userControllerUtil.getJwtTokenForCredentials(user.getUsername(), "password");
		headers.add("Authorization", token);
	}

	public Video extractVideoFromResponse(ResponseEntity<String> response) {
		try {
			return new ObjectMapper().readValue(response.getBody(), Video.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
