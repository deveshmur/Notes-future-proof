package com.zipcodewilmington;


public class CreateNoteCommand implements CLICommand {
    
    private final NoteService noteService; 

    public CreateNoteCommand (NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public void execute(String[] args){
    }
}
