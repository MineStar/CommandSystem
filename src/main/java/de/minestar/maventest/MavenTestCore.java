package de.minestar.maventest;

import de.minestar.maventest.commands.NormalCommandExample;
import de.minestar.maventest.commands.SuperCommandExample;
import de.minestar.maventest.commandsystem.CommandHandler;
import de.minestar.minestarlibrary.AbstractCore;

public class MavenTestCore extends AbstractCore {

    public static final String NAME = "CommandSystem";

    @Override
    protected boolean createCommands() {
        CommandHandler commandHandler = new CommandHandler(NAME);
        commandHandler.registerCommand(new SuperCommandExample());
        commandHandler.registerCommand(new NormalCommandExample());

        commandHandler.listCommands();

        return super.createCommands();
    }
}
