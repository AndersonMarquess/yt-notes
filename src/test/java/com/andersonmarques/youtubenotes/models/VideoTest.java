package com.andersonmarques.youtubenotes.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class VideoTest {

    @Test
    public void createVideo() {
        Video video = new Video();
        assertNotNull(video);
    }

    @Test
    public void verifyGettersAndSetters() {
        Video video = new Video();
        video.setId(1);
        video.setTitle("title");
        video.setAuthor("author");
        assertEquals(1, video.getId());
        assertEquals("title", video.getTitle());
        assertEquals("author", video.getAuthor());
    }

    @Test
    public void throwIllegalArgExcptionWhenSetEmptyValueInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Video(0, "", ""));
    }

    @Test
    public void throwIllegalArgExcptionWhenSetEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> new Video().setAuthor(""));
        assertThrows(IllegalArgumentException.class, () -> new Video().setTitle(""));
    }
}
