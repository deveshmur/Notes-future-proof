package com.zipcodewilmington;

import java.time.LocalDateTime;
import java.util.List; 


public class NoteMetadata {
 
    private String title;
    private LocalDateTime created;
    private LocalDateTime modified;
    private List<String> tags;
    private String author;
    private String status;
    private int priority; 

   
    public NoteMetadata() { 
    }


    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }

    public LocalDateTime getModified() { return modified; }
    public void setModified(LocalDateTime modified) { this.modified = modified; }

    

}