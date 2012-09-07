package de.minestar.maventest.commands;

import de.minestar.maventest.annotations.Description;
import de.minestar.maventest.annotations.Label;
import de.minestar.maventest.annotations.PermissionNode;
import de.minestar.maventest.annotations.Arguments;
import de.minestar.maventest.commandsystem.AbstractCommand;

@Label(label = "/tr")
@Arguments(arguments = "")
@PermissionNode(node = "")
@Description(description = "This is a supercommand. It has no arguments and (in this case) no permissionnode.")
public class SuperCommandExample extends AbstractCommand {

    @Override
    protected void createSubCommands() {
        this.registerCommand(new SubCommandExample());
    }
}
