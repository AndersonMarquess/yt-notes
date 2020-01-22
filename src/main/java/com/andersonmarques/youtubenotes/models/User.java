package com.andersonmarques.youtubenotes.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "field {0} required")
    @Size(min = 3, max = 50, message = "field {0} should be between {min} and {max} characters")
    private String username;
    @NotEmpty(message = "field {0} required")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Video> videos = new HashSet<>();

    public User() {
    }

    public User(Integer id, String username, String password) {
        this.id = id;
        setUsername(username);
        setPassword(password);
    }

    public void setPassword(String password) {
        if (password.trim().isEmpty() || password.length() < 3) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void addVideo(Video video) {
        if (video.getTitle() == null || video.getAuthor() == null) {
            throw new IllegalArgumentException("Invalid video");
        }
        video.setUser(this);
        this.videos.add(video);
    }

    public void removeVideo(Video video) {
        this.videos.remove(video);
    }

    public Set<Video> getVideos() {
        return Collections.unmodifiableSet(videos);
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }
}
