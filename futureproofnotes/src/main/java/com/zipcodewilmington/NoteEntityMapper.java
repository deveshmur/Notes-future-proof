package com.zipcodewilmington;

public class NoteEntityMapper {
    public static Note toDomain(NoteEntity entity) {
        if (entity == null) return null;

        Note note = new Note();
        note.setId(entity.getId());
        note.setBody(entity.getBody());

        NoteMetadata m = new NoteMetadata();
        NoteMetadataEmbeddable em = entity.getMetadata();

        if (em != null) {
            m.setTitle(em.getTitle());
            m.setAuthor(em.getAuthor());
            m.setStatus(em.getStatus());
            m.setPriority(em.getPriority());
            m.setCreated(em.getCreated());
            m.setModified(em.getModified());
            m.setTags(em.getTags());
        }

        note.setMetadata(m);

        return note;
    }

    public static NoteEntity toEntity(Note note) {
        if (note == null) return null;

        NoteEntity entity = new NoteEntity();
        entity.setId(note.getId());
        entity.setBody(note.getBody());

        NoteMetadataEmbeddable em = new NoteMetadataEmbeddable();
        NoteMetadata m = note.getMetadata();

        if (m != null) {
            em.setTitle(m.getTitle());
            em.setAuthor(m.getAuthor());
            em.setStatus(m.getStatus());
            em.setPriority(m.getPriority());
            em.setCreated(m.getCreated());
            em.setModified(m.getModified());
            em.setTags(m.getTags());
        }

        entity.setMetadata(em);

        return entity;
    }
}
