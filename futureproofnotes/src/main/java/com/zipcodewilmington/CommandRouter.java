package com.zipcodewilmington;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class CommandRouter {

    private final Map<String, CLICommand> commands = new HashMap<>();

    public void registerCommand(String name, CLICommand command) {
        commands.put(name.toLowerCase(), command);
    }

    public void route(String[] args) {

        if (args == null || args.length == 0) {
            System.out.println("No command provided.");
            return;
        }

        String commandName = args[0].toLowerCase();
        CLICommand command = commands.get(commandName);

        if (command == null) {
            System.out.println("Unknown command: " + commandName);
            return;
        }
    String[] remainingArgs = Arrays.copyOfRange(args, 1, args.length);
    command.execute(remainingArgs);
    }
}
