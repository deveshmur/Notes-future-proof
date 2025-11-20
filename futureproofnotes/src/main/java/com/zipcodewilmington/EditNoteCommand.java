package com.zipcodewilmington;

import java.nio.file.Path;

public class EditNoteCommand implements CLICommand {

    private final NoteService noteService;
    private final FileSystemManagement fsManager;

    public EditNoteCommand(NoteService noteService, FileSystemManagement fsManager) {
        this.noteService = noteService;
        this.fsManager = fsManager;
    }

    @Override
    public void execute(String[] args) {
    }
}