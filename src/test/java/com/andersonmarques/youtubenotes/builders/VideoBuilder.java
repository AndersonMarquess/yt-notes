package com.andersonmarques.youtubenotes.builders;

import java.time.LocalDateTime;
import java.util.UUID;

import com.andersonmarques.youtubenotes.models.Video;

public class VideoBuilder {

    private Integer id;
    private String videoUrl;
    private String title;
    private String author;

    public VideoBuilder() {
        this.id = LocalDateTime.now().getNano();
        this.videoUrl = UUID.randomUUID().toString();
        this.title = UUID.randomUUID().toString();
        this.author = UUID.randomUUID().toString();
    }

    public VideoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public VideoBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public VideoBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public VideoBuilder withVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public Video build() {
        return new Video(id, videoUrl, title, author);
    }
}
