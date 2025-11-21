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
    private Integer priority; 

   
    public NoteMetadata() { 
    }


    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }

    public LocalDateTime getModified() { return modified; }
    public void setModified(LocalDateTime modified) { this.modified = modified; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getAuthor() { return author;} 
    public void setAuthor(String author) { this.author = author;}
    
    public String getStatus() { return status;}
    public void setStatus(String status) { this.status = status;}

    public Integer getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }



}