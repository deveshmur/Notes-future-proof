package com.zipcodewilmington;

import org.springframework.cache.interceptor.NameMatchCacheOperationSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NotesController {
    private final NoteService service;
    private final NoteSearchService searchService;


    public NotesController(NoteService service, NoteSearchService searchService) {
        this.service = service;
        this.searchService = searchService;
    }

    @GetMapping
    public List<NoteDTO> listNotes() {
        return service.listNotes()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/tag/{tag}")
    public List<NoteDTO> searchByTag(@PathVariable String tag) {
        return searchService.searchByTag(tag)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
}
    
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable String id) {
        Note note = service.readNote(id);

        if (note == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(NoteMapper.toDTO(note));
    }

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody CreateNoteRequest req) {
        Note note = new Note();
        NoteMetadata metadata = new NoteMetadata();

        metadata.setTitle(req.title);
        metadata.setTags(req.tags);
        metadata.setAuthor(req.author);
        metadata.setStatus(req.status);
        metadata.setPriority(req.priority);

        note.setMetadata(metadata);
        note.setBody(req.body);

        Note created = service.createNote(note);

        return ResponseEntity.ok(toDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote
            (@PathVariable String id,
            @RequestBody UpdateNoteRequest req) {
        
        Note updatedFields = new Note();
        NoteMetadata md = new NoteMetadata();

        md.setTitle(req.title);
        md.setTags(req.tags);
        md.setAuthor(req.author);
        md.setStatus(req.status);
        md.setPriority(req.priority);

        updatedFields.setMetadata(md);
        updatedFields.setBody(req.body);

        Note updated = service.updateNote(id, updatedFields);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toDTO(updated));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        boolean deleted = service.deleteNote(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<NoteDTO> search(@RequestParam("q") String query) {
        return searchService.searchByKeyword(query)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
}


    private NoteDTO toDTO(Note note) {
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
