package com.zipcodewilmington;

import java.util.List;


public class NoteService {
    
    private final NoteRepository repository;

     public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public Note createNote(String title, String body, List<String> tags) {
        return null;
    }

    public Note readNote (String id) { 
        return null;
    }

    public Note updateNote(String id, String body, List<String> tags) {
        return null;
    }

    public boolean deleteNote (String id) {
        return false; 
    }

    public List<Note> listNotes() {
        return List.of();
    }

}
