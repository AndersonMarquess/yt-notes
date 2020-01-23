package com.andersonmarques.youtubenotes.services;

import java.util.Optional;

import com.andersonmarques.youtubenotes.models.Video;
import com.andersonmarques.youtubenotes.repositories.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserService userService;

    public Video insert(Video video) {
        video.setId(null);
        setOwner(video);
        return videoRepository.save(video);
    }

    private void setOwner(Video video) {
        video.setUser(userService.getAuthenticatedUser());
    }

    public Video findById(Integer id) {
        Optional<Video> video = videoRepository.findOne(buildVideoExampleWithId(id));
        if (!video.isPresent()) {
            throw new IllegalArgumentException("Invalid video id");
        }
        return video.get();
    }

    private Example<Video> buildVideoExampleWithId(Integer id) {
        Video videoExample = new Video();
        setOwner(videoExample);
        videoExample.setId(id);
        return Example.of(videoExample);
    }
}
