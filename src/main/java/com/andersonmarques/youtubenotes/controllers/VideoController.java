package com.andersonmarques.youtubenotes.controllers;

import javax.validation.Valid;

import com.andersonmarques.youtubenotes.models.Video;
import com.andersonmarques.youtubenotes.services.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping(path = BASE_URI + "/{id}", produces = APPLICATION_JSON)
    public ResponseEntity<Video> findById(@PathVariable("id") Integer id) {
        Video video = videoService.findById(id);
        return ResponseEntity.ok(video);
    }

    @PutMapping(path = BASE_URI, consumes = APPLICATION_JSON)
    public ResponseEntity<Video> update(@RequestBody @Valid Video video) {
        Video videoUpdated = videoService.update(video);
        return ResponseEntity.ok(videoUpdated);
    }

    @DeleteMapping(path = BASE_URI + "/{id}")
    public ResponseEntity<Void> DeleteById(@PathVariable("id") Integer id) {
        videoService.DeleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
