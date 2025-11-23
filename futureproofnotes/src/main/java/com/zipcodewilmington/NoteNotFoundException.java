package com.zipcodewilmington;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String id) {
        super("Note not found: " + id);
    }
}
