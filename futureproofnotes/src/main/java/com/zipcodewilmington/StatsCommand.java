package com.zipcodewilmington;

public class StatsCommand implements CLICommand {

    private final StatisticsService statisticsService;

    public StatsCommand(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public void execute(String[] args) {
    }
}