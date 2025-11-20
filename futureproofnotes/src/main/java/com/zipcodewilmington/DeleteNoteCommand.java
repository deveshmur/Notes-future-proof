package com.zipcodewilmington;

public class DeleteNoteCommand implements CLICommand {

    private final NoteService noteService;

    public DeleteNoteCommand(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public void execute(String[] args) {
    }
}