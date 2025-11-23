package com.zipcodewilmington;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final NoteSearchService searchService;

    public SearchController(NoteSearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public List<NoteDTO> keyword(@RequestParam String q) {
        return searchService.searchByKeyword(q)
                .stream()
                .map(NoteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/tags")
    public List<String> getAllTags() {
        return searchService.listAllTags();
    }
}
