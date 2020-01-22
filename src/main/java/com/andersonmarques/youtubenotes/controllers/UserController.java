package com.andersonmarques.youtubenotes.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import com.andersonmarques.youtubenotes.models.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final String BASE_URI = "/v1/users";
    private final String APPLICATION_JSON = "application/json";

    @PostMapping(path = BASE_URI, produces = APPLICATION_JSON)
    public ResponseEntity<User> create(@RequestBody @Valid User user) throws URISyntaxException {
        user.setId(1);
        URI uri = new URI(BASE_URI + "/" + user.getId());
        return ResponseEntity.created(uri).body(user);
    }
}
