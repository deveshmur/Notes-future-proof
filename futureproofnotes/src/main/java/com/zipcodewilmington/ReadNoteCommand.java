package com.zipcodewilmington;

public class ReadNoteCommand implements CLICommand {

    private final NoteService noteService;

    public ReadNoteCommand(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public void execute(String[] args) {
    }

}
