package de.minestar.maventest.commands;

import org.bukkit.entity.Player;

import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.ExecuteSuperCommand;
import de.minestar.maventest.commandsystem.annotations.Label;
import de.minestar.maventest.commandsystem.annotations.PermissionNode;
import de.minestar.minestarlibrary.utils.PlayerUtils;

@Label(label = "/warp")
@Arguments(arguments = "<Warpname>")
@PermissionNode(node = "")
@Description(description = "This is a supercommand. It has no arguments and (in this case) no permissionnode.")
@ExecuteSuperCommand
public class WarpCommandExample extends AbstractCommand {

    @Override
    protected void createSubCommands() {
        this.registerCommand(new WarpCreateCommandExample());
    }

    @Override
    public void execute(Player player, String[] arguments) {
        String args = "";
        for (String t : arguments) {
            args += t + " ";
        }
        PlayerUtils.sendSuccess(player, "DONE: /warp " + args);
    }
}
