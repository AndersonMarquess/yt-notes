package com.andersonmarques.youtubenotes.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    @NotEmpty(message = "the description field is mandatory")
    @Size(min = 3, max = 500, message = "the description field should be between {min} and {max} characters")
    private String description;
    @NotNull(message = "the seconds field is mandatory")
    @Positive(message = "the seconds field must be greater than 1")
    private int seconds;
    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "the video field is mandatory")
    @JsonBackReference
    private Video video;

    public Note() {
    }

    public Note(Integer id, String description, int seconds) {
        this.id = id;
        setDescription(description);
        setSeconds(seconds);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Invalid description");
        }
        this.description = description;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        if (seconds <= 0) {
            throw new IllegalArgumentException("Invalid seconds");
        }
        this.seconds = seconds;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + seconds;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Note other = (Note) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (seconds != other.seconds)
            return false;
        return true;
    }
}
