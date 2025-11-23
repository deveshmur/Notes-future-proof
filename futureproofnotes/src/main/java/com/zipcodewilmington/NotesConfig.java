package com.zipcodewilmington;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotesConfig {
    
    @Bean
    public NoteFileParser noteFileParser() {
        return new NoteFileParser();
    }

    @Bean
    public NoteRepository noteRepository(NoteJpaRepository jpa) {
        return new NoteRepository(jpa);
    }

    @Bean
    public NoteService noteService(NoteRepository repo, NoteFileParser parser) {
            return new NoteService(repo, parser);
    }


    @Bean
    public NoteSearchService noteSearchService(NoteService service) {
        return new NoteSearchService(service);
    }

    @Bean
    public NoteStatsService noteStatsService(NoteService noteService) {
    return new NoteStatsService(noteService);
    }


}
