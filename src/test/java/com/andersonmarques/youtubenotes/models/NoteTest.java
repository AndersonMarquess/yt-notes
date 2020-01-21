package com.andersonmarques.youtubenotes.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class NoteTest {

    @Test
    public void createNote() {
        Note note = new Note();
        assertNotNull(note);
    }

    @Test
    public void verifyGettersAndSetters() {
        Note note = new Note();
        note.setId(1);
        note.setDescription("description");
        note.setSeconds(10);
        assertEquals(1, note.getId());
        assertEquals("description", note.getDescription());
        assertEquals(10, note.getSeconds());
    }

    @Test
    public void throwIllegalArgExcptionWhenSetEmptyValueInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Note(0, "", 0));
    }

    @Test
    public void throwIllegalArgExcptionWhenSetEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> new Note().setDescription(""));
    }

    @Test
    public void notAllowCreateNoteWithZeroSeconds() {
        assertThrows(IllegalArgumentException.class, () -> new Note().setSeconds(0));
    }
}
