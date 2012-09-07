package de.minestar.maventest;

import de.minestar.maventest.commands.NormalCommandExample;
import de.minestar.maventest.commands.WarpCommandExample;
import de.minestar.maventest.commandsystem.CommandHandler;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        CommandHandler commandHandler = new CommandHandler("PluginName");
        commandHandler.registerCommand(new WarpCommandExample());
        commandHandler.registerCommand(new NormalCommandExample());

        commandHandler.listCommands();

        String[] arguments = new String[2];
        arguments[0] = "create";
        arguments[1] = "test";

        System.out.println('\n');
        commandHandler.handleCommand("/warp", arguments);

        arguments = new String[3];
        arguments[0] = "test-1";
        arguments[1] = "test-2";
        arguments[2] = "test-3";

        System.out.println('\n');
        commandHandler.handleCommand("/warp", arguments);
    }

}
