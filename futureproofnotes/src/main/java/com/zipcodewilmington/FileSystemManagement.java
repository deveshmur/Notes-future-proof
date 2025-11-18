package com.zipcodewilmington;

import java.nio.file.Path;
import java.util.List;


public class FileSystemManagement {
    
    private final Path notesDirectory;

    
    public FileSystemManagement (Path notesDirectory) {
        this.notesDirectory = notesDirectory;
    }

    public void ensureNotesDirectory() {
    }

    public List<Path> listNoteFiles() {
        return List.of();
    }

    public boolean deleteFile (Path filePath) {
        return false;
    }

    public Path resolveNotePath(String noteId) {
        return null;
    }

    public void openInEditor(Path filePath) {
    }



}
