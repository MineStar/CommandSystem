package de.minestar.maventest.commands;

import org.bukkit.entity.Player;

import de.minestar.maventest.annotations.Arguments;
import de.minestar.maventest.annotations.Description;
import de.minestar.maventest.annotations.Label;
import de.minestar.maventest.annotations.PermissionNode;
import de.minestar.maventest.commandsystem.AbstractCommand;

@Label(label = "/normal")
@Arguments(arguments = "<MUST> [optional]")
@PermissionNode(node = "my.node")
@Description(description = "This is a normal command. It is a single command with 1 needed parameter and 1 optional. The permissionnode is 'my.node'.")
public class NormalCommandExample extends AbstractCommand {

    @Override
    public void execute(Player player, String[] arguments) {
        System.out.println("execute NormalCommand");
    }
}
