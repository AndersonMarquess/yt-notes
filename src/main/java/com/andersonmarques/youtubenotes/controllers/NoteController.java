package com.andersonmarques.youtubenotes.controllers;

import javax.validation.Valid;

import com.andersonmarques.youtubenotes.models.Note;
import com.andersonmarques.youtubenotes.services.NoteService;

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
public class NoteController {

    private final String BASE_URI = "/v1/notes";
    private final String APPLICATION_JSON = "application/json";

    @Autowired
    private NoteService noteService;

    @PostMapping(path = BASE_URI, produces = APPLICATION_JSON)
    public ResponseEntity<Note> create(@RequestBody @Valid Note note) {
        Note noteCreated = noteService.insert(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(noteCreated);
    }

    @GetMapping(path = BASE_URI + "/{id}", produces = APPLICATION_JSON)
    public ResponseEntity<Note> findDetails(@PathVariable("id") Integer noteId) {
        return ResponseEntity.ok(noteService.findById(noteId));
    }

    @PutMapping(path = BASE_URI, produces = APPLICATION_JSON)
    public ResponseEntity<Note> update(@RequestBody @Valid Note note) {
        Note noteUpdated = noteService.update(note);
        return ResponseEntity.ok(noteUpdated);
    }

    @DeleteMapping(path = BASE_URI + "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
