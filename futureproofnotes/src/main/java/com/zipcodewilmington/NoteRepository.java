package com.zipcodewilmington;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public class NoteRepository {

    private final NoteJpaRepository jpa;

    public List<Note> loadPagedNotes(Pageable pageable) {
    return jpa.findAll(pageable)
              .stream()
              .map(NoteEntityMapper::toDomain)
              .toList();
    }
    
    public NoteRepository(NoteJpaRepository jpa) {
        this.jpa = jpa;
    }

    public Note saveNewNote(Note note) {
        NoteEntity entity = NoteEntityMapper.toEntity(note);
        NoteEntity saved = jpa.save(entity);
        return NoteEntityMapper.toDomain(saved);
    }

    public Note loadNoteById(String id) {
        Optional<NoteEntity> found = jpa.findById(id);
        return found.map(NoteEntityMapper::toDomain).orElse(null);
    }

    public Note updateNote(Note note) {
        NoteEntity entity = NoteEntityMapper.toEntity(note);
        NoteEntity saved = jpa.save(entity);
        return NoteEntityMapper.toDomain(saved);
    }

    public boolean deleteNoteById(String id) {
        if (!jpa.existsById(id)) {
            return false;
        }
        jpa.deleteById(id);
        return true;
    }

    public List<Note> loadAllNotes() {
        return jpa.findAll()
                .stream()
                .map(NoteEntityMapper::toDomain)
                .toList();
    }

    public long count() {
        return jpa.count();
    }
    
}
