package com.mvoro.developer.springmvcrecipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mvoro.developer.springmvcrecipeproject.commands.NoteCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Note;

import static org.junit.jupiter.api.Assertions.*;

class NoteCommandToNoteTest {

    private NoteCommandToNote noteCommandToNote;

    private NoteCommand noteCommand;

    @BeforeEach
    void setUp() {
        noteCommandToNote = new NoteCommandToNote();

        noteCommand = new NoteCommand();
        noteCommand.setId(1L);
        noteCommand.setNote("This is a note");
    }

    @Test
    void convert() {
        Note note = noteCommandToNote.convert(noteCommand);

        assertNotNull(note);
        assertEquals(noteCommand.getNote(), note.getNote());
    }
}