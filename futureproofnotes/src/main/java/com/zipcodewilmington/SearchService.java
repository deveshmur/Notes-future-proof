package com.zipcodewilmington;

import java.util.List;

public class SearchService {
    
    private final NoteRepository repository; 

    public SearchService(NoteRepository repository) {
        this.repository = repository; 

    }
    public List<Note> searchByKeyword(String keyword) {
        return List.of();
    }

    public List<Note> searchByTag(String keyword) {
        return List.of();
    } 

    public boolean match(Note not, String keyword) {
        return false; 
    }


}
