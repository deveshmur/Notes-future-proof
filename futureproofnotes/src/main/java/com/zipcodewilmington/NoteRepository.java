package com.zipcodewilmington;

import java.util.List;
import java.util.Optional;


public class NoteRepository {

    private final FileSystemManagement fileSystemManagement;
    private final NoteFileParser parser;

    public NoteRepository(FileSystemManagement fileSystemManagement, NoteFileParser parser) {
        this.fileSystemManagement = fileSystemManagement;
        this.parser = parser;
    }

    public List<Note> loadAll() {
        return List.of();
    }

    public Optional<Note> Optional() {
        return Optional.empty();
    }

    public void save (Note note) {
    }

    public void update (Note note) {
    }

    public boolean delete(String id) {
        return false;
    }

    public String generateNoteId(String title) {
        return "";
    } 
}
