package com.zipcodewilmington;

import java.util.HashMap;
import java.util.Map;


public class CommandRouter {

    private final Map<String, CLICommand> commands = new HashMap<>();

    public void reisterCommand(String name, CLICommand command) {
        commands.put(name, command);
    }

    public void route(String[] args){
    }

}
