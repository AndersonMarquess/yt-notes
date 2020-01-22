package com.andersonmarques.youtubenotes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import com.andersonmarques.youtubenotes.Utils.UserControllerUtil;
import com.andersonmarques.youtubenotes.builders.UserBuilder;
import com.andersonmarques.youtubenotes.models.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private TestRestTemplate client;
    private UserBuilder userBuilder;
    private UserControllerUtil userControllerUtil;

    @BeforeEach
    private void setupObjects() {
        userBuilder = new UserBuilder();
        userControllerUtil = new UserControllerUtil(client);
    }

    @Test
    public void receiveResourceLocationAfterCreateUser() {
        User user = userBuilder.withId(1594).build();
        ResponseEntity<String> response = userControllerUtil.postUser(user);
        User userCreated = userControllerUtil.extractUserFromResponse(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/v1/users/" + userCreated.getId(), response.getHeaders().getFirst("Location"));
        assertNotNull(userCreated);
    }

    @Test
    public void notAllowCreateUserWithAlreadySavedUsername() {
        User user = userBuilder.withId(1).withUsername("unique").build();
        User user2 = userBuilder.withId(2).withUsername("unique").build();
        ResponseEntity<String> response = userControllerUtil.postUser(user);
        ResponseEntity<String> invalidResponse = userControllerUtil.postUser(user2);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, invalidResponse.getStatusCode());
    }

    @Test
    public void notCreateUserWithUsernameLessThan3Chars() {
        User user = userBuilder.withUsername("tw").build();
        ResponseEntity<String> response = userControllerUtil.postUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void notCreateUserWithUsernameBiggerThan50Chars() {
        String giganticUsername = UUID.randomUUID().toString().concat(UUID.randomUUID().toString());
        User user = userBuilder.withUsername(giganticUsername).build();
        ResponseEntity<String> response = userControllerUtil.postUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void notCreateUserWithPasswordLessThan3Chars() {
        User user = userBuilder.withUsername("a").build();
        ResponseEntity<String> response = userControllerUtil.postUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void receiveJwtTokenAfterLogin() {
        User user = userControllerUtil.getDefaultRegistredUser();
        ResponseEntity<String> response = userControllerUtil.authenticate(user.getUsername(), "password");
        String token = response.getHeaders().getFirst("Authorization");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(token);
    }

    @Test
    public void notAllowLoginWithInvalidUser() {
        ResponseEntity<String> response = userControllerUtil.authenticate("invalid", "password");
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
