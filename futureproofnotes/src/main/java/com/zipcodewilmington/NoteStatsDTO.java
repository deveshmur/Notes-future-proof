package com.zipcodewilmington;

import java.util.Map;


public class NoteStatsDTO {
    public int totalNotes;
    public int notesWithTags;
    public int totalTags;
    public Map<String, Integer> tagFrequency;
    public Map<Integer, Integer> priorityCount;
    
}
