package com.zipcodewilmington;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteJpaRepository extends JpaRepository<NoteEntity, String> {
    
}
