package com.andersonmarques.youtubenotes.services;

import java.util.Optional;

import com.andersonmarques.youtubenotes.models.Note;
import com.andersonmarques.youtubenotes.repositories.NoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private VideoService videoService;

    public Note insert(Note note) {
        note.setId(null);
        return noteRepository.save(note);
    }

    public Note findById(Integer id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            if (hasAccessToNote(note.get())) {
                return note.get();
            }
        }
        throw new IllegalArgumentException("Invalid note id");
    }

    private boolean hasAccessToNote(Note note) {
        int videoId = note.getVideo().getId();
        return videoService.authenticatedUserIsOwnerOfVideoWithId(videoId);
    }

    public void deleteById(Integer id) {
        Note note = findById(id);
        if (note != null) {
            noteRepository.deleteById(id);
        }
    }

    public Note update(Note note) {
        if (note.getId() != null && hasAccessToNote(note)) {
            return noteRepository.save(note);
        }
        throw new IllegalArgumentException("Invalid note for update");
    }
}
