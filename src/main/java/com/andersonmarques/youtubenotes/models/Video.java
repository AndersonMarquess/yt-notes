package com.andersonmarques.youtubenotes.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Video {

    @Id
    @NotEmpty(message = "field {0} required")
    private String id;
    @NotEmpty(message = "field {0} required")
    @Size(min = 3, max = 200, message = "field {0} should be between {min} and {max} characters")
    private String title;
    @NotEmpty(message = "field {0} required")
    @Size(min = 3, max = 200, message = "field {0} should be between {min} and {max} characters")
    private String author;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "video")
    private Set<Note> notes = new HashSet<>();

    public Video() {
    }

    public Video(String id, String title, String author) {
        setId(id);
        setTitle(title);
        setAuthor(author);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid title");
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid author");
        }
        this.author = author;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Note> getNotes() {
        return Collections.unmodifiableSet(notes);
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note) {
        if (note.getDescription() == null || note.getSeconds() < 0) {
            throw new IllegalArgumentException("Invalid note");
        }
        note.setVideo(this);
        this.notes.add(note);
    }

    public void removeNote(Note note) {
        this.notes.remove(note);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Video other = (Video) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
