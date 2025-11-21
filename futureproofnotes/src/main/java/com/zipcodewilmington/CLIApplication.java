package com.zipcodewilmington;


public class CLIApplication {
    public static void main( String[] args ) {
        FileSystemManagement fs = new FileSystemManagement("futureproofnotes/notes");
        NoteFileParser parser = new NoteFileParser();
        NoteRepository repo = new NoteRepository(fs, parser);
        NoteService noteService = new NoteService(repo);

        CommandRouter router = new CommandRouter();

    }

    private void initializeServices(){
    }
}