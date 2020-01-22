package com.andersonmarques.youtubenotes.Utils;

import com.andersonmarques.youtubenotes.builders.UserBuilder;
import com.andersonmarques.youtubenotes.models.AccountCredentials;
import com.andersonmarques.youtubenotes.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public class UserControllerUtil {

    private TestRestTemplate client;
    private UserBuilder userBuilder;

    public UserControllerUtil(TestRestTemplate client) {
        this.client = client;
        this.userBuilder = new UserBuilder();
    }

    public User getDefaultRegistredUser() {
        ResponseEntity<String> response = postUser(userBuilder.build());
        return extractUserFromResponse(response);
    }

    public ResponseEntity<String> postUser(User user) {
        return client.postForEntity("/v1/users", new HttpEntity<>(user), String.class);
    }

    public User extractUserFromResponse(ResponseEntity<String> response) {
        try {
            return new ObjectMapper().readValue(response.getBody(), User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<String> authenticate(String username, String password) {
        AccountCredentials credentials = new AccountCredentials();
        credentials.setUsername(username);
        credentials.setPassword(password);
        return client.postForEntity("/login", new HttpEntity<>(credentials), String.class);
    }
}
