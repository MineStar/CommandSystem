package de.minestar.maventest.commandsystem;

import java.util.HashMap;

import de.minestar.minestarlibrary.utils.ConsoleUtils;

public class CommandHandler {

    private final String pluginName;

    public HashMap<String, AbstractCommand> registeredCommands;

    public CommandHandler(String pluginName) {
        this.pluginName = pluginName;
        this.registeredCommands = new HashMap<String, AbstractCommand>();
    }

    public boolean registerCommand(AbstractCommand command) {
        command.setPluginName(pluginName);
        command.initialize();
        if (this.registeredCommands.containsKey(command.getCommandLabel())) {
            ConsoleUtils.printError(pluginName, "ERROR: Command '" + command.getCommandLabel() + "' is already registered in '" + command.getCompleteCommand() + "'!");
            return false;
        }
        ConsoleUtils.printInfo(pluginName, "OK --> Registered command '" + command.getCommandLabel() + "'!");
        this.registeredCommands.put(command.getCommandLabel(), command);
        return true;
    }

    public boolean handleCommand(String label, String[] arguments) {
        label = label.toLowerCase();
        if (!label.startsWith("/")) {
            label = "/" + label;
        }

        AbstractCommand command = this.registeredCommands.get(label);
        if (command == null) {
            ConsoleUtils.printError(pluginName, "Command '" + label + "' not found.");
            return false;
        }

        if (command.isSuperCommand()) {
            // handle supercommands
            if (arguments.length > 0) {
                label = arguments[0];
                String[] newArguments = new String[arguments.length - 1];
                System.arraycopy(arguments, 1, newArguments, 0, newArguments.length);
                command.handleCommand(label, newArguments);
            } else {
                command.printSyntax();
            }
            return true;
        } else {
            // handle normal command
            // check argumentcount & try to execute it
            if ((!command.hasOptionalCommands() && arguments.length == command.getMinArgumentCount()) || (command.hasOptionalCommands() && arguments.length >= command.getMinArgumentCount() && arguments.length <= command.getMaxArgumentCount())) {
                command.execute(arguments);
            } else if (command.hasOptionalCommands()) {
                command.printSyntax();
            } else {
                command.printSyntax();
            }
            return true;
        }
    }

    public void listCommands() {
        ConsoleUtils.printInfo("");
        ConsoleUtils.printInfo("------------------------------------------");
        ConsoleUtils.printInfo("List of registered commands:");
        ConsoleUtils.printInfo("------------------------------------------");
        for (AbstractCommand command : this.registeredCommands.values()) {
            command.listCommands();
        }
    }
}
