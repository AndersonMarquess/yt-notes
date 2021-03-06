package com.andersonmarques.youtubenotes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

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
    public void notAllowCreateVideoWithoutUrl() {
        assertThrows(IllegalArgumentException.class, () -> new Video(0, "", "author", "title"));
    }

    @Test
    public void findAllVideosWithSuccess() {
        ResponseEntity<String> response = videoControllerUtil.postVideoWithDefaultUser(videoBuilder.build());
        ResponseEntity<List<Video>> allVideos = videoControllerUtil.findAll();
        assertEquals(HttpStatus.OK, allVideos.getStatusCode());
        Video videoCreated = videoControllerUtil.extractVideoFromResponse(response);
        assertNotNull(allVideos.getBody());
        assertEquals(videoCreated, allVideos.getBody().stream().findFirst().get());
    }

    @Test
    public void getDetailsOfCreatedVideoById() {
        ResponseEntity<String> response = videoControllerUtil.postVideoWithDefaultUser(videoBuilder.build());
        Video videoCreated = videoControllerUtil.extractVideoFromResponse(response);
        ResponseEntity<String> responseDetails = videoControllerUtil
                .findDetailsByIdWithDefaultUser(videoCreated.getId());
        Video videoDetails = videoControllerUtil.extractVideoFromResponse(responseDetails);
        assertNotNull(videoDetails);
        assertEquals(videoCreated.getId(), videoDetails.getId());
    }

    @Test
    public void returnErrorWhenTryToAccessVideoWithouValidId() {
        ResponseEntity<String> response = videoControllerUtil.findDetailsByIdWithDefaultUser(54321);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateVideoDetails() {
        ResponseEntity<String> response = videoControllerUtil.postVideoWithDefaultUser(videoBuilder.build());
        Video video = videoControllerUtil.extractVideoFromResponse(response);
        assertNotNull(video);
        video.setUrl("http://www.youtube.com/atualizado");
        ResponseEntity<String> responseUpdate = videoControllerUtil.updateVideoWIthDefaultUser(video);
        assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());
        Video videoUpdated = videoControllerUtil.extractVideoFromResponse(responseUpdate);
        assertEquals("http://www.youtube.com/atualizado", videoUpdated.getUrl());
    }

    @Test
    public void notAllowUpdateVideoWithoutId() {
        ResponseEntity<String> response = videoControllerUtil.postVideoWithDefaultUser(videoBuilder.build());
        Video video = videoControllerUtil.extractVideoFromResponse(response);
        assertNotNull(video);
        video.setId(null);
        ResponseEntity<String> responseUpdate = videoControllerUtil.updateVideoWIthDefaultUser(video);
        assertEquals(HttpStatus.BAD_REQUEST, responseUpdate.getStatusCode());
    }

    @Test
    public void deleteVideoById() {
        ResponseEntity<String> response = videoControllerUtil.postVideoWithDefaultUser(videoBuilder.build());
        Video video = videoControllerUtil.extractVideoFromResponse(response);
        ResponseEntity<String> responseDelete = videoControllerUtil.deleteVideoByIdWithDefaultUser(video.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseDelete.getStatusCode());
    }

    @Test
    public void notAllowDeleteVideoWithInvalidId() {
        ResponseEntity<String> responseDelete = videoControllerUtil.deleteVideoByIdWithDefaultUser(54321);
        assertEquals(HttpStatus.BAD_REQUEST, responseDelete.getStatusCode());
    }
}
