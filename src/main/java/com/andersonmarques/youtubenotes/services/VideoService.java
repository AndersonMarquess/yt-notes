package com.andersonmarques.youtubenotes.services;

import com.andersonmarques.youtubenotes.models.Video;
import com.andersonmarques.youtubenotes.repositories.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserService userService;

    public Video insert(Video video) {
        setOwner(video);
        return videoRepository.save(video);
    }

    private void setOwner(Video video) {
        video.setUser(userService.getAuthenticatedUser());
    }
}
