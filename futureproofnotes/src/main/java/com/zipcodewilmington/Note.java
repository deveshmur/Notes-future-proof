package com.zipcodewilmington;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Note {

    private String id; 
    private NoteMetadata metadata;
    private String body;

    public Note(String id, NoteMetadata metadata, String body) {
        this.id = id;
        this.metadata = metadata;
        this.body = body;
    }
    
    public Note(){
    }
    
    public String getId() { 
        return id; }

    public void setId(String id) {
        this.id = id;
    }

    public NoteMetadata getMetadata() { 
        return metadata; 
    }

    public void setMetadata(NoteMetadata metadata) {
        this.metadata = metadata;
    }

    public String getBody() { 
        return body; 
    }

    public void setBody(String body) { 
        this.body = body;
    }

    public void updateModified() {
        if (this.metadata != null) {
            this.metadata.setModified(LocalDateTime.now(ZoneOffset.UTC));
        }
    }
}
