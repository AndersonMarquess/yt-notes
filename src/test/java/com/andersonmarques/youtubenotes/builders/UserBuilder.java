package com.andersonmarques.youtubenotes.builders;

import java.time.LocalDateTime;
import java.util.UUID;

import com.andersonmarques.youtubenotes.models.User;

public class UserBuilder {

    private int id;
    private String username;
    private String password;

    public UserBuilder() {
        this.id = LocalDateTime.now().getNano();
        this.username = UUID.randomUUID().toString();
        this.password = "password";
    }

    public UserBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(id, username, password);
    }
}
