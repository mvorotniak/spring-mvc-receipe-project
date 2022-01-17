package com.mvoro.developer.springmvcrecipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mvoro.developer.springmvcrecipeproject.commands.NoteCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Note;

import static org.junit.jupiter.api.Assertions.*;

class NoteToNoteCommandTest {

    private NoteToNoteCommand noteToNoteCommand;

    private Note note;

    @BeforeEach
    void setUp() {
        noteToNoteCommand = new NoteToNoteCommand();

        note = new Note();
        note.setId(1L);
        note.setNote("This is a note");
    }

    @Test
    void convert() {
        NoteCommand noteCommand = noteToNoteCommand.convert(note);

        assertNotNull(noteCommand);
        assertEquals(note.getNote(), noteCommand.getNote());
    }
}