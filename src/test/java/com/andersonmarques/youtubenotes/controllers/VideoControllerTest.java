package com.andersonmarques.youtubenotes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.andersonmarques.youtubenotes.builders.VideoBuilder;
import com.andersonmarques.youtubenotes.models.Video;
import com.andersonmarques.youtubenotes.utils.VideoControllerUtil;

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
public class VideoControllerTest {

    @Autowired
    private TestRestTemplate client;
    private VideoBuilder videoBuilder;
    private VideoControllerUtil videoControllerUtil;

    @BeforeEach
    private void setupObjects() {
        videoBuilder = new VideoBuilder();
        videoControllerUtil = new VideoControllerUtil(client);
    }

    @Test
    public void createVideoWithSuccess() {
        Video video = videoBuilder.build();
        ResponseEntity<String> response = videoControllerUtil.postVideoWithDefaultUser(video);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Video videoCreated = videoControllerUtil.extractVideoFromResponse(response);
        assertNotNull(videoCreated);
    }

    @Test
    public void notAllowCreateVideoWithoutId() {
        Video video = new Video();
        video.setAuthor("author");
        video.setTitle("title");
        ResponseEntity<String> response = videoControllerUtil.postVideoWithDefaultUser(video);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
