package com.zipcodewilmington;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private final FileSystemManagement fileSystemManagement;
    private final NoteFileParser parser;

    public NoteRepository(FileSystemManagement fileSystemManagement, NoteFileParser parser) {
        this.fileSystemManagement = fileSystemManagement;
        this.parser = parser;
        this.fileSystemManagement.ensureNotesDirectory();
    }

    public List<Note> loadAllNotes() {
        List<Path> files = fileSystemManagement.listNoteFiles();
        List<Note> notes = new ArrayList<>();


        for (Path file: files) {
            try {
                Note note = parser.parse(file);
                notes.add(note);
            } catch (Exception e) {
                System.err.println("Error parsing note: " + file + "... " + e.getMessage());
            }
        }
        return notes;
    }

    public Note loadNoteById(String id) {
        Path path = fileSystemManagement.resolveNotePath(id);

        if (!Files.exists(path)) {
            return null;
        }

        return parser.parse(path);
    }


    public Note saveNewNote (Note note) {
        String id = generateNoteId();
        note.setId(id);

        Path filePath = fileSystemManagement.resolveNotePath(id);
        writeNoteToFile(note, filePath);

        return note;
    }

    public Note updateNote(Note note) {
        if (note.getId() == null || note.getId().isBlank()) {
            throw new RuntimeException("Can't update note without valid ID");
        }

        Path filePath = fileSystemManagement.resolveNotePath(note.getId());
        writeNoteToFile(note, filePath);

        return note;

    }
 
    public boolean deleteNoteById(String id) {
        Path path = fileSystemManagement.resolveNotePath(id);
        return fileSystemManagement.deleteFile(path);
    }

    private void writeNoteToFile(Note note, Path filePath) {
        String serialized = parser.serialize(note);

        try {
            Files.writeString(filePath, serialized, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write note to disk: " + filePath, e);
        }
    }

    private String generateNoteId() {
        long now = Instant.now().toEpochMilli();
        long random = (long)(Math.random() * 1_000_000);
        return "n" + now + "_" + random;
    }
}
