package com.andersonmarques.youtubenotes.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    public User() {
    }

    public User(int id, String username, String password) {
        this.id = id;
        setUsername(username);
        setPassword(password);
    }

    public void setPassword(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Invalid password");
        }
        this.password = encodePassword(password);
    }

    private String encodePassword(String password) {
        if (isPasswordEncoded(password)) {
            return password;
        } else {
            return new BCryptPasswordEncoder().encode(password);
        }
    }

    private boolean isPasswordEncoded(String password) {
        String bCryptPrefix = "$2a$10$";
        int bCryptLength = 60;
        return password.startsWith(bCryptPrefix) && password.length() == bCryptLength;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Invalid username");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
}
