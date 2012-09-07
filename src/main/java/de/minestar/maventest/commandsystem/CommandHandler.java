package de.minestar.maventest.commandsystem;

import java.util.HashMap;

import de.minestar.minestarlibrary.utils.ConsoleUtils;

public class CommandHandler {

    private final String pluginName;

    public HashMap<String, AbstractCommand> registeredCommands;

    /**
     * Constructor. The given pluginname will be used in the registered commands.
     * 
     * @param pluginName
     */
    public CommandHandler(String pluginName) {
        this.pluginName = pluginName;
        this.registeredCommands = new HashMap<String, AbstractCommand>();
    }

    /**
     * Register a new command
     * 
     * @param command
     * @return <b>true</b> if the command is registered successfully, otherwise <b>false</b>
     */
    public boolean registerCommand(AbstractCommand command) {
        // set the pluginame
        command.setPluginName(pluginName);

        // CHECK: is the command already registered?
        if (this.registeredCommands.containsKey(command.getLabel())) {
            ConsoleUtils.printError(pluginName, "ERROR: Command '" + command.getLabel() + "' is already registered in '" + command.getCommand() + "'!");
            return false;
        }

        // register the command
        this.registeredCommands.put(command.getLabel(), command);
        ConsoleUtils.printInfo(pluginName, "OK --> Registered command '" + command.getLabel() + "'!");

        // initialize the subcommands for the given command
        command.initializeSubCommands();
        return true;
    }

    /**
     * Handle a command for this CommandHandler
     */
    public boolean handleCommand(String label, String[] arguments) {
        // TODO: Update this method for the use with Bukkit

        // cast the label to lowercase
        label = label.toLowerCase();

        // in this case: the label needs to start with an '/'
        if (!label.startsWith("/")) {
            label = "/" + label;
        }

        // lookup the command
        AbstractCommand command = this.registeredCommands.get(label);
        if (command == null) {
            ConsoleUtils.printError(pluginName, "Command '" + label + "' not found.");
            return false;
        }

        // (1) : we have a supercommand
        // (2) : We have a normal command

        if (command.isSuperCommand()) {
            // (1)

            // (1.1) -> if we have arguments : copy the array and make the first argument the new label. Then we let the command handle the execution.
            // (1.2) -> otherwise : we will see if the command should be executed (see annotations) and react to it. We will execute it (1.2.1) or just print the syntax (1.2.2).
            if (arguments.length > 0) {
                // (1.1)

                // make the first argument the label
                label = arguments[0];

                // copy the rest of the arguments to a new array
                String[] newArguments = new String[arguments.length - 1];
                System.arraycopy(arguments, 1, newArguments, 0, newArguments.length);

                // handle the command
                command.handleCommand(label, newArguments);
            } else {
                // (1.2)
                if (command.isExecuteSuperCommand()) {
                    // (1.2.1)

                    // execute the command
                    command.execute(arguments);
                } else {
                    // (1.2.2)

                    // print the syntax
                    command.printSyntax();
                }
            }
            return true;
        } else {
            // (2)

            // check the argumentcount to see if the passed argumentcount is correct for this command. Execute the command if it is true (2.1) , otherwise print the syntax (2.2).

            // check argumentcount & try to execute it
            if ((!command.hasOptionalArguments() && arguments.length == command.getMinimumArgumentCount()) || (command.hasOptionalArguments() && arguments.length >= command.getMinimumArgumentCount() && arguments.length <= command.getMaximumArgumentCount())) {
                // (2.1)

                // execute the command
                command.execute(arguments);
            } else {
                // (2.2)

                // print the syntax
                command.printSyntax();
            }
            return true;
        }
    }

    /**
     * List all registered commands for this CommandHandler
     */
    public void listCommands() {
        // TODO: Update this method for the use with Bukkit

        ConsoleUtils.printInfo("");
        ConsoleUtils.printInfo("------------------------------------------");
        ConsoleUtils.printInfo("List of registered commands:");
        ConsoleUtils.printInfo("------------------------------------------");
        for (AbstractCommand command : this.registeredCommands.values()) {
            command.listCommand();
        }
    }
}