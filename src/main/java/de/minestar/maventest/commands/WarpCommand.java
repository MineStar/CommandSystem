package de.minestar.maventest.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.ExecuteSuperCommand;
import de.minestar.maventest.commandsystem.annotations.Label;
import de.minestar.maventest.commandsystem.annotations.PermissionNode;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

@Label(label = "/warp")
@Arguments(arguments = "<Warpname>")
@PermissionNode(node = "")
@Description(description = "This is a supercommand. It has no arguments and (in this case) no permissionnode.")
@ExecuteSuperCommand
public class WarpCommand extends AbstractCommand {

    @Override
    protected void createSubCommands() {
        this.registerCommand(new WarpCreateCommand());
    }

    @Override
    public void execute(Player player, ArgumentList argumentList) {
        String txt = "";
        for (String arg : argumentList.getIterator(String.class, 0)) {
            txt += " " + arg;
        }

        PlayerUtils.sendSuccess(player, "DONE: /warp" + txt);
    }

    @Override
    public void execute(ConsoleCommandSender console, ArgumentList argumentList) {
        String txt = "";
        for (String arg : argumentList.getIterator(String.class, 0)) {
            txt += " " + arg;
        }

        ConsoleUtils.printInfo(pluginName, "DONE: /warp" + txt);
    }
}
