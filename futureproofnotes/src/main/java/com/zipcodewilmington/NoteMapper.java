package com.zipcodewilmington;

import java.time.ZoneOffset;

public class NoteMapper {

    public static NoteDTO toDTO(Note note) {
        NoteDTO dto = new NoteDTO();

        dto.id = note.getId();
        dto.title = note.getMetadata().getTitle();

        dto.created = note.getMetadata()
                .getCreated()
                .atOffset(ZoneOffset.UTC)
                .toInstant()
                .toString();

        dto.modified = note.getMetadata()
                .getModified()
                .atOffset(ZoneOffset.UTC)
                .toInstant()
                .toString();

        dto.tags = note.getMetadata().getTags();
        dto.author = note.getMetadata().getAuthor();
        dto.status = note.getMetadata().getStatus();
        dto.priority = note.getMetadata().getPriority();
        dto.body = note.getBody();

        return dto;
    }
}
