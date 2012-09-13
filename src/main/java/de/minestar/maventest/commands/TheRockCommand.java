package de.minestar.maventest.commands;

import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;
import de.minestar.maventest.commandsystem.annotations.PermissionNode;

@Label(label = "/tr")
@Arguments(arguments = "")
@PermissionNode(node = "")
@Description(description = "/tr")
public class TheRockCommand extends AbstractCommand {

    @Override
    protected void createSubCommands() {
        this.registerCommand(new TheRockAreaCommand());
    }
}
