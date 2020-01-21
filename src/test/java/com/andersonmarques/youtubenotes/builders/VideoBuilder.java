package com.andersonmarques.youtubenotes.builders;

import java.time.LocalDateTime;
import java.util.UUID;

import com.andersonmarques.youtubenotes.models.Video;

public class VideoBuilder {

    private int id;
    private String title;
    private String author;

    public VideoBuilder() {
        this.id = LocalDateTime.now().getNano();
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

    public VideoBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public Video build() {
        return new Video(id, title, author);
    }
}
