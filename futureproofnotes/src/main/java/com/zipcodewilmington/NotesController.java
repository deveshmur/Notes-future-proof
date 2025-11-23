package com.zipcodewilmington;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Get all notes", description = "Returns a list of all notes stored in the system.")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    @GetMapping
    public List<NoteDTO> listNotes() {
        return service.listNotes()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

   @Operation(summary = "Search notes by tag",
           description = "Returns notes whose metadata contains the specified tag.")
    @ApiResponse(responseCode = "200", description = "Notes returned successfully")
    @GetMapping("/tag/{tag}")
    public List<NoteDTO> searchByTag(@PathVariable String tag) {
        return searchService.searchByTag(tag)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Operation(summary = "Get a note by ID", description = "Retrieves a specific note.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note found"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable String id) {
        Note note = service.readNote(id);

        if (note == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(NoteMapper.toDTO(note));
    }

    @Operation(summary = "Create a new note", description = "Creates a new note with metadata and body content.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Note created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid note data")
    })      
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

    @Operation(summary = "Update a note", description = "Updates the fields of an existing note.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note updated"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote(
        @PathVariable String id,
        @RequestBody UpdateNoteRequest request) {

    Note updatedFields = new Note();
    NoteMetadata metadata = new NoteMetadata();

    metadata.setTitle(request.title);
    metadata.setAuthor(request.author);
    metadata.setStatus(request.status);
    metadata.setPriority(request.priority);
    metadata.setTags(request.tags);

    updatedFields.setMetadata(metadata);
    updatedFields.setBody(request.body);

    Note updated = service.updateNote(id, updatedFields);

    if (updated == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(NoteMapper.toDTO(updated));
}


    @Operation(summary = "Delete a note", description = "Deletes a note permanently.")
        @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Note deleted"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {

    boolean deleted = service.deleteNote(id);

    if (!deleted) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search notes", description = "Searches notes using keywords.")
    @ApiResponse(responseCode = "200", description = "Search results returned")    @GetMapping("/search")
    public List<NoteDTO> search(@RequestParam("q") String query) {
        return searchService.searchByKeyword(query)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
}


    @Operation(summary = "Export note to YAML",
            description = "Returns the full YAML-formatted content of a note.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note exported successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/export/{id}")
    public ResponseEntity<String> exportNote(@PathVariable String id) {
        String content = service.exportNote(id);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + id + ".note\"")
                .body(content);
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

    @Operation
            (summary = "List notes with pagination",
            description = "Returns a paginated list of notes. Optional sort field may be provided.")
    @ApiResponse(responseCode = "200", description = "Page returned successfully")
    @GetMapping("/paged")
    public List<NoteDTO> listPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {

        List<Note> notes;

        if (sort != null && !sort.isBlank()) {
            notes = service.listNotesPagedAndSorted(page, size, sort);
        } else {
            notes = service.listNotesPaged(page, size);
        }

        return notes.stream()
                    .map(NoteMapper::toDTO)
                    .toList();
    }

    

}
