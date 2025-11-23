package com.zipcodewilmington;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "notes")
public class NoteEntity {

 @Id
    private String id;

    @Embedded
    private NoteMetadataEmbeddable metadata;

    @Column(columnDefinition = "TEXT")
    private String body;

     public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NoteMetadataEmbeddable getMetadata() {
        return metadata;
    }

    public void setMetadata(NoteMetadataEmbeddable metadata) {
        this.metadata = metadata;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
}
