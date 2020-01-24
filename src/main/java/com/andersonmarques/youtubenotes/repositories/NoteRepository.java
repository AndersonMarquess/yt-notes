package com.andersonmarques.youtubenotes.repositories;

import com.andersonmarques.youtubenotes.models.Note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

}
