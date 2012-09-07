package de.minestar.maventest;

import de.minestar.maventest.commands.NormalCommandExample;
import de.minestar.maventest.commands.SuperCommandExample;
import de.minestar.maventest.commandsystem.CommandHandler;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        CommandHandler commandHandler = new CommandHandler("PluginName");
        commandHandler.registerCommand(new SuperCommandExample());
        commandHandler.registerCommand(new NormalCommandExample());

        commandHandler.listCommands();

    }

}
