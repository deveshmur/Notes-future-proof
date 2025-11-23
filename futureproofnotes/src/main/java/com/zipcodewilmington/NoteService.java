package com.zipcodewilmington;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


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

    NoteMetadata existingMeta = existing.getMetadata();
    NoteMetadata incoming = updatedFields.getMetadata();

    if (incoming.getTitle() != null) existingMeta.setTitle(incoming.getTitle());
    if (incoming.getAuthor() != null) existingMeta.setAuthor(incoming.getAuthor());
    if (incoming.getStatus() != null) existingMeta.setStatus(incoming.getStatus());
    if (incoming.getPriority() != null) existingMeta.setPriority(incoming.getPriority());
    if (incoming.getTags() != null) existingMeta.setTags(incoming.getTags());

    if (updatedFields.getBody() != null) existing.setBody(updatedFields.getBody());

    existingMeta.setModified(LocalDateTime.now(ZoneOffset.UTC));

    return repository.updateNote(existing);
}


    public boolean deleteNote (String id) {
        return repository.deleteNoteById(id);
    }

    public List<Note> listNotes() {
        return repository.loadAllNotes();
    }

    public String exportNote(String id) {
        Note note = repository.loadNoteById(id);
        if (note == null) {
            throw new IllegalArgumentException("Note not found: " + id);
        }
        return parser.toFullNoteString(note);
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

    Sort sort;

    if ("created".equalsIgnoreCase(sortField)) {
        sort = Sort.by("metadata.created").ascending();
    } else if ("modified".equalsIgnoreCase(sortField)) {
        sort = Sort.by("metadata.modified").ascending();
    } else if ("title".equalsIgnoreCase(sortField)) {
        sort = Sort.by("metadata.title").ascending();
    } else {
        sort = Sort.unsorted();
    }

    Pageable pageable = PageRequest.of(page, size, sort);

    return repository.loadPagedNotes(pageable);
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
}
