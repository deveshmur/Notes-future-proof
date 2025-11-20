package com.zipcodewilmington;

import notes.domain.Note;
import notes.storage.NoteRepository;

import java.util.List;
import java.util.Map;


public class StatisticsService {
    
    private final NoteRepository repository;

    public StatisticsService(NoteRepository repository) {
        this.repository = repository; 
    }

    public int countNotes() {
        return 0;
    }

    public int countTags() {
        return 0;
    }

    public Map<String, Integer> mostCommonTags() {
        return Map.of();
    }

    public double averageNoteSize() {
        return 0.0;
    }

    public Note oldestnote() {
        return null; 
    }

    public Note newestnote() {
        return null;
    }


}