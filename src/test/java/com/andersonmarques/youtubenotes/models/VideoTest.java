package com.andersonmarques.youtubenotes.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.andersonmarques.youtubenotes.builders.NoteBuilder;
import com.andersonmarques.youtubenotes.builders.VideoBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class VideoTest {

    private VideoBuilder videoBuilder;
    private NoteBuilder noteBuilder;

    @BeforeEach
    public void setupObjects() {
        videoBuilder = new VideoBuilder();
        noteBuilder = new NoteBuilder();
    }

    @Test
    public void createVideo() {
        Video video = new Video();
        assertNotNull(video);
    }

    @Test
    public void verifyGettersAndSetters() {
        Video video = new Video();
        video.setId("1");
        video.setTitle("title");
        video.setAuthor("author");
        assertEquals("1", video.getId());
        assertEquals("title", video.getTitle());
        assertEquals("author", video.getAuthor());
    }

    @Test
    public void throwIllegalArgExcptionWhenSetEmptyValueInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Video("", "", ""));
    }

    @Test
    public void throwIllegalArgExcptionWhenSetEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> new Video().setId(""));
        assertThrows(IllegalArgumentException.class, () -> new Video().setAuthor(""));
        assertThrows(IllegalArgumentException.class, () -> new Video().setTitle(""));
    }

    @Test
    public void addNoteToVideo() {
        Video video = videoBuilder.build();
        Note note = noteBuilder.build();
        video.addNote(note);
        assertTrue(video.getNotes().contains(note));
    }

    @Test
    public void notAddSameNoteToVideoTwice() {
        Video video = videoBuilder.build();
        Note note = noteBuilder.build();
        video.addNote(note);
        video.addNote(note);
        assertEquals(1, video.getNotes().size());
    }

    @Test
    public void removeNoteFromView() {
        Video video = videoBuilder.build();
        Note note = noteBuilder.build();
        video.addNote(note);
        video.removeNote(note);
        assertFalse(video.getNotes().contains(note));
    }

    @Test
    public void notRemoveUnsavedNoteFromVideo() {
        Video video = videoBuilder.build();
        Note note = noteBuilder.build();
        Note note2 = noteBuilder.withId(12).withDescription("description2").build();
        video.addNote(note);
        video.removeNote(note2);
        assertTrue(video.getNotes().contains(note));
    }

    @Test
    public void thorwExceptionWhenTryToModifyNoteFromVideoGetter() {
        Video video = videoBuilder.build();
        Note note = noteBuilder.build();
        video.addNote(note);
        assertThrows(UnsupportedOperationException.class, () -> video.getNotes().remove(note));
        assertTrue(video.getNotes().contains(note));
    }
}
