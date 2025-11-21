package com.zipcodewilmington;

public class StatsCommand implements CLICommand {

    private final NoteStatsService statsService;

    public StatsCommand(NoteStatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    public void execute(String[] args) {
        NoteStatsDTO dto = statsService.generateStats();

        System.out.println("=== NOTE STATISTICS ===");
        System.out.println("Total Notes: " + dto.totalNotes);
        System.out.println("Notes With Tags: " + dto.notesWithTags);
        System.out.println("Total Tags Used: " + dto.totalTags);
        System.out.println("Tag Frequency: " + dto.tagFrequency);
        System.out.println("Priority Count: " + dto.priorityCount);
    }
}