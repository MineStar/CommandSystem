package de.minestar.maventest.commands;

import de.minestar.maventest.annotations.Description;
import de.minestar.maventest.annotations.Label;
import de.minestar.maventest.annotations.PermissionNode;
import de.minestar.maventest.annotations.Arguments;
import de.minestar.maventest.commandsystem.AbstractCommand;

@Label(label = "rollback")
@Arguments(arguments = "<MUSTHAVE> <MUSTHAVE2> [OPTIONAL]")
@PermissionNode(node = "test.node")
@Description(description = "This is a subcommand. It is a subcommand of '/tr' command with 2 needed parameters and 1 optional. The permissionnode is 'test.node'")
public class SubCommandExample extends AbstractCommand {

    @Override
    public void execute(String[] arguments) {
        String args = "";
        for (String t : arguments) {
            args += t + " ";
        }
        System.out.println("execute /tr rollback " + args);
    }
}
