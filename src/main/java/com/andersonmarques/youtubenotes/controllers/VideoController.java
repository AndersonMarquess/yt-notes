package com.andersonmarques.youtubenotes.controllers;

import javax.validation.Valid;

import com.andersonmarques.youtubenotes.models.Video;
import com.andersonmarques.youtubenotes.services.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

    private final String BASE_URI = "/v1/videos";
    private final String APPLICATION_JSON = "application/json";

    @Autowired
    private VideoService videoService;

    @PostMapping(path = BASE_URI, consumes = APPLICATION_JSON)
    public ResponseEntity<Video> create(@RequestBody @Valid Video video) {
        Video videoCreated = videoService.insert(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(videoCreated);
    }
}
