package com.zipcodewilmington;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotesConfig {
    
    @Bean
    public FileSystemManagement fileSystemManagement() {
        return new FileSystemManagement(null);
    }

    @Bean
    public NoteFileParser noteFileParser() {
        return new NoteFileParser();
    }

    @Bean
    public NoteRepository noteRepository(FileSystemManagement fsm, NoteFileParser parser) {
        return new NoteRepository(fsm, parser);
    }

    @Bean
    public NoteService noteService(NoteRepository repository) {
        return new NoteService(repository);
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
