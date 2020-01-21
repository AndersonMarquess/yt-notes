package com.andersonmarques.youtubenotes.builders;

import java.time.LocalDateTime;
import java.util.UUID;

import com.andersonmarques.youtubenotes.models.Note;

public class NoteBuilder {

	private int id;
	private String description;
	private int seconds;

	public NoteBuilder() {
		this.id = LocalDateTime.now().getNano();
		this.description = UUID.randomUUID().toString();
		this.seconds = LocalDateTime.now().getNano();
	}

	public NoteBuilder withId(int id) {
		this.id = id;
		return this;
	}

	public NoteBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public NoteBuilder withSeconds(int seconds) {
		this.seconds = seconds;
		return this;
	}

	public Note build() {
		return new Note(id, description, seconds);
	}
}
