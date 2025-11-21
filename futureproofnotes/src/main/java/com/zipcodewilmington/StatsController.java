package com.zipcodewilmington;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class StatsController {
    
    private final NoteStatsService statsService;

    public StatsController (NoteStatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/stats")
    public NoteStatsDTO getStats() {
        return statsService.generateStats();
    }
}
