package com.zipcodewilmington;


public class ListNotesCommand implements CLICommand{
    
    private final NoteService noteService;

    public ListNotesCommand(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override 
    public void execute(String[]args) {
    }

}
