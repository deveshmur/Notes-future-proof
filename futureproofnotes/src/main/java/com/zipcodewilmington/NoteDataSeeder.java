package com.zipcodewilmington;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Component
public class NoteDataSeeder implements CommandLineRunner {
    
    private final NoteJpaRepository jpa;

    public NoteDataSeeder(NoteJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void run(String... args) throws Exception {

        if (jpa.count() > 0) {
            return; 
        }

        Random random = new Random();

        List<String> sampleBodies = List.of(
            "This is a generated note for testing API functionality.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Meeting notes: Discuss project roadmap and timelines.",
            "Study notes: Review Spring Boot annotations and JPA.",
            "Random thought: Maybe try a new idea for the UI layout.",
            "Reminder: Always write clean, maintainable code.",
            "Experiment: Test the export feature with YAML formatting.",
            "Technical notes: Ensure CORS is configured properly.",
            "Draft idea for next project phase.",
            "Team sync summary: Completed pagination implementation."
        );

        List<String> sampleTags = List.of(
            "demo", "test", "auto", "seed",
            "project", "notes", "idea", "school",
            "java", "spring", "backend", "api",
            "angular", "frontend", "todo"
        );

        List<String> sampleStatuses = List.of(
            "draft", "in-progress", "completed", "archived"
        );

        List<String> sampleAuthors = List.of(
            "System", "Seeder", "TestBot", "Developer"
        );

        for (int i = 1; i <= 60; i++) {

            NoteEntity entity = new NoteEntity();
            entity.setId("seed_" + UUID.randomUUID());

            NoteMetadataEmbeddable meta = new NoteMetadataEmbeddable();
            meta.setTitle("Generated Note " + i);
            meta.setAuthor(sampleAuthors.get(random.nextInt(sampleAuthors.size())));
            meta.setStatus(sampleStatuses.get(random.nextInt(sampleStatuses.size())));
            meta.setPriority(random.nextInt(5) + 1);
            
            LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC)
                                             .minusDays(random.nextInt(90));

            meta.setCreated(now);
            meta.setModified(now.plusHours(random.nextInt(72)));

             meta.setTags(
                sampleTags.stream()
                        .sorted((a, b) -> random.nextInt(3) - 1)
                        .limit(1 + random.nextInt(3))
                        .toList()
            );

            entity.setMetadata(meta);


             entity.setBody(
                sampleBodies.get(random.nextInt(sampleBodies.size())) 
                + "\n\n(This note was auto-generated for demonstration.)"
            );

            jpa.save(entity);
        }
        System.out.println("Seeded 60 notes into PostgreSQL.");

    }

}
