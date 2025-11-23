package com.zipcodewilmington;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;


public class NoteService {
    
    private final NoteRepository repository;
    private final NoteFileParser parser;


     public NoteService(NoteRepository repository, NoteFileParser parser) {
        this.repository = repository;
        this.parser = parser;

    }

    public Note createNote(Note note) {
        validateNewNote(note);

        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        note.getMetadata().setCreated(now);
        note.getMetadata().setModified(now);

        return repository.saveNewNote(note);
    }

    public Note readNote (String id) { 
        return repository.loadNoteById(id);
    }

    public Note updateNote(String id, Note updatedFields) {
        Note existing = repository.loadNoteById(id);

        if (existing == null) {
            return null;
        }

        if (updatedFields.getMetadata().getTitle() != null) {
            existing.getMetadata().setTitle(updatedFields.getMetadata().getTitle());
        }
        if (updatedFields.getMetadata().getAuthor() != null) {
            existing.getMetadata().setAuthor(updatedFields.getMetadata().getAuthor());
        }
        if (updatedFields.getMetadata().getStatus() != null) {
            existing.getMetadata().setStatus(updatedFields.getMetadata().getStatus());
        }
        if (updatedFields.getMetadata().getPriority() != null) {
            existing.getMetadata().setPriority(updatedFields.getMetadata().getPriority());
        }
        if (updatedFields.getMetadata().getTags() != null) {
            existing.getMetadata().setTags(updatedFields.getMetadata().getTags());
        }

        
        if (updatedFields.getBody() != null) {
            existing.setBody(updatedFields.getBody());
        }

        existing.getMetadata().setModified(LocalDateTime.now(ZoneOffset.UTC));

        return repository.updateNote(existing);

    }

    public boolean deleteNote (String id) {
        return repository.deleteNoteById(id);
    }

    public List<Note> listNotes() {
        return repository.loadAllNotes();
    }

    public List<Note> listNotesPaged(int page, int size) {
        List<Note> all = listNotes();

        int from = page * size;
        int to = Math.min(from + size, all.size());

        if (from >= all.size()) {
            return List.of();
        }

        return all.subList(from, to);
    }

    public List<Note> listNotesPagedAndSorted(int page, int size, String sortField) {

        List<Note> all = listNotes();

        if ("created".equalsIgnoreCase(sortField)) {
            all.sort((a, b) -> a.getMetadata().getCreated().compareTo(b.getMetadata().getCreated()));
        } else if ("modified".equalsIgnoreCase(sortField)) {
            all.sort((a, b) -> a.getMetadata().getModified().compareTo(b.getMetadata().getModified()));
        } else if ("title".equalsIgnoreCase(sortField)) {
            all.sort((a, b) -> a.getMetadata().getTitle().compareToIgnoreCase(b.getMetadata().getTitle()));
        }

        int from = page * size;
        int to = Math.min(from + size, all.size());

        if (from >= all.size()) {
            return List.of();
        }

        return all.subList(from, to);
    }




    private void validateNewNote(Note note) {
        if (note== null || note.getMetadata() == null) {
            throw new IllegalArgumentException("Note and metadata cant be null");
        }
    
        String title = note.getMetadata().getTitle();
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("New notes must have a title.");
        }
    }

    public String exportNote(String id) {
        Note note = repository.loadNoteById(id);
        if (note == null) {
            throw new IllegalArgumentException("Note not found: " + id);
        }
        return parser.toFullNoteString(note);
    }
}
