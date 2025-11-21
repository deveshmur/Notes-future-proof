package com.zipcodewilmington;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class NoteSearchService {

    private final NoteService noteService;

    public NoteSearchService(NoteService noteService) {
        this.noteService = noteService;
    }

    public List<Note> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }

        String search = keyword.toLowerCase(Locale.ROOT);

        return noteService.listNotes().stream()
                .filter(note -> matches(note, search))
                .collect(Collectors.toList());
    }

    private boolean matches(Note note, String search) {
        NoteMetadata m = note.getMetadata();

        if (m.getTitle() != null && m.getTitle().toLowerCase().contains(search))
            return true;

        if (m.getAuthor() != null && m.getAuthor().toLowerCase().contains(search))
            return true;

        if (m.getStatus() != null && m.getStatus().toLowerCase().contains(search))
            return true;

        if (note.getBody() != null && note.getBody().toLowerCase().contains(search))
            return true;

        if (m.getTags() != null) {
            for (String t : m.getTags()) {
                if (t.toLowerCase().contains(search)) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Note> searchByTag(String tag) {
        if (tag == null || tag.isBlank()) {
            return List.of();
        }

        String target = tag.toLowerCase(Locale.ROOT);

        return noteService.listNotes().stream()
                .filter(note -> hasTag(note, target))
                .collect(Collectors.toList());
    }

    private boolean hasTag(Note note, String target) {
        List<String> tags = note.getMetadata().getTags();
        if (tags == null) return false;

        return tags.stream()
                .anyMatch(t -> t.toLowerCase().contains(target));
    }
}
