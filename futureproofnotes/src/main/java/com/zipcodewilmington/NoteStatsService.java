package com.zipcodewilmington;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteStatsService {
     private final NoteService noteService;

    public NoteStatsService(NoteService noteService) {
        this.noteService = noteService;
    }

    public NoteStatsDTO generateStats() {

        List<Note> notes = noteService.listNotes();

        int totalNotes = notes.size();
        int notesWithTags = 0;
        int totalTags = 0;

        Map<String, Integer> tagFrequency = new HashMap<>();
        Map<Integer, Integer> priorityCount = new HashMap<>();

        for (Note note : notes) {

            List<String> tags = note.getMetadata().getTags();
            if (tags != null && !tags.isEmpty()) {
                notesWithTags++;
                totalTags += tags.size();

                for (String tag : tags) {
                    tagFrequency.put(tag, tagFrequency.getOrDefault(tag, 0) + 1);
                }
            }

            Integer p = note.getMetadata().getPriority();
            if (p != null) {
                priorityCount.put(p, priorityCount.getOrDefault(p, 0) + 1);
            }
        }

        NoteStatsDTO dto = new NoteStatsDTO();
        dto.totalNotes = totalNotes;
        dto.notesWithTags = notesWithTags;
        dto.totalTags = totalTags;
        dto.tagFrequency = tagFrequency;
        dto.priorityCount = priorityCount;

        return dto;
    }
}
