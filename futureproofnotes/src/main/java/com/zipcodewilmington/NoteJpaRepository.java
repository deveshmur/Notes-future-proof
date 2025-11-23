package com.zipcodewilmington;

import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteJpaRepository extends JpaRepository<NoteEntity, String> {
    
}
