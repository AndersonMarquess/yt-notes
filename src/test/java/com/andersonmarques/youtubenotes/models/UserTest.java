package com.andersonmarques.youtubenotes.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserTest {

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
        User user = new User();
        user.setPassword("password");
        assertTrue(BCrypt.checkpw("password", user.getPassword()));
    }

    @Test
    public void notEncodePasswordTwice() {
        User user = new User(1, "null", "password");
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
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(""));
        assertThrows(IllegalArgumentException.class, () -> user.setPassword(""));
    }
}
