package com.zipcodewilmington;

public class Note {

    private String id; 
    private NoteMetadata metadata;
    private String body;

    public Note(String id, NoteMetadata metadata, String body) {
        this.id = id;
        this.metadata = metadata;
        this.body = body;
    }
    
    public String getId() { return id; }
    public NoteMetadata getMetadata() { return metadata; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public void updateModified() { 
    }


}
