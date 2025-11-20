package com.zipcodewilmington;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


public class FileSystemManagement {
    
    private final Path notesDirectory;

    
    public FileSystemManagement (Path notesDirectory) {
        this.notesDirectory = Paths.get("notes");
    }

    public void ensureNotesDirectory() {
        try {
            if (!Files.exists(notesDirectory)) {
                Files.createDirectories(notesDirectory);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create notes directory" + notesDirectory, e);
        }
    }

    public List<Path> listNoteFiles() {
        List<Path> files = new ArrayList<>();

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(notesDirectory, "*.note")) {
            for (Path entry : stream) {
                files.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading notes directory", e);
        }
        return files;
    }

    public boolean deleteFile (Path filePath) {
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    public Path resolveNotePath(String noteId) {
        return notesDirectory.resolve(noteId + ".note");
    }

    public Path getNotesDirectory() {
        return notesDirectory;
    }

    public void openInEditor(Path filePath) {
    }
}
