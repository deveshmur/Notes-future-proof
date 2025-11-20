package com.zipcodewilmington;

public class SearchNotesCommand implements CLICommand {

    private final SearchService searchService;

    public SearchNotesCommand(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public void execute(String[] args) {
    }
}
