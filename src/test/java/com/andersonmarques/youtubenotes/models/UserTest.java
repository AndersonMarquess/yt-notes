package com.andersonmarques.youtubenotes.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.andersonmarques.youtubenotes.builders.UserBuilder;
import com.andersonmarques.youtubenotes.builders.VideoBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserTest {

    private UserBuilder userBuilder;
    private VideoBuilder videoBuilder;

    @BeforeEach()
    public void setupObjects() {
        this.userBuilder = new UserBuilder();
        this.videoBuilder = new VideoBuilder();
    }

    @Test
    public void createUser() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void verifyGettersAndSetter() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("password");
        assertEquals(1, user.getId());
        assertEquals("username", user.getUsername());
        assertNotEquals("password", user.getPassword());
    }

    @Test
    public void encondeRawPassword() {
        User user = userBuilder.withPassword("password").build();
        assertTrue(BCrypt.checkpw("password", user.getPassword()));
    }

    @Test
    public void notEncodePasswordTwice() {
        User user = userBuilder.withPassword("password").build();
        String encodedPassword = user.getPassword();
        user.setPassword(encodedPassword);
        assertTrue(BCrypt.checkpw("password", user.getPassword()));
    }

    @Test
    public void throwIllegalArgExcptionWhenSetEmptyValueInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new User(1, "", ""));
    }

    @Test
    public void throwIllegalArgExcptionWhenSetEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.build().setUsername(""));
        assertThrows(IllegalArgumentException.class, () -> userBuilder.build().setPassword(""));
    }

    @Test
    public void addVideoInUser() {
        User user = userBuilder.build();
        Video video = videoBuilder.build();
        user.addVideo(video);
        assertTrue(user.getVideos().contains(video));
    }

    @Test
    public void thorwExceptionWhenTryToAddEmptyVideoInUser() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.build().addVideo(new Video()));
    }

    @Test
    public void notAddSameVideoInUserTwice() {
        Video video = videoBuilder.build();
        User user = userBuilder.build();
        user.addVideo(video);
        user.addVideo(video);
        assertEquals(1, user.getVideos().size());
    }

    @Test
    public void removeVideoFromUser() {
        Video video = videoBuilder.build();
        User user = userBuilder.build();
        user.addVideo(video);
        user.removeVideo(video);
        assertTrue(user.getVideos().isEmpty());
    }

    @Test
    public void notRemoveUnsavedVideoFromUser() {
        Video video = videoBuilder.build();
        Video video2 = videoBuilder.withId(2).build();
        User user = userBuilder.build();
        user.addVideo(video);
        user.removeVideo(video2);
        assertEquals(1, user.getVideos().size());
    }

    @Test
    public void thorwExceptionWhenTryToModifyUserVideosFromGetter() {
        User user = userBuilder.build();
        Video video = videoBuilder.build();
        user.addVideo(video);
        assertThrows(UnsupportedOperationException.class, () -> user.getVideos().remove(video));
        assertTrue(user.getVideos().contains(video));
    }
}
