package de.minestar.maventest.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;
import de.minestar.maventest.commandsystem.annotations.PermissionNode;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

@Label(label = "create")
@Arguments(arguments = "<Warpname>")
@PermissionNode(node = "test.node")
@Description(description = "This is a subcommand. It is a subcommand of '/tr' command with 2 needed parameters and 1 optional. The permissionnode is 'test.node'")
public class WarpCreateCommand extends AbstractCommand {

    @Override
    public void execute(Player player, ArgumentList argumentList) {
        String txt = "";
        for (String arg : argumentList.getIterator(String.class, 0)) {
            txt += " " + arg;
        }

        PlayerUtils.sendSuccess(player, "DONE: /warp create" + txt);
    }

    @Override
    public void execute(ConsoleCommandSender console, ArgumentList argumentList) {
        String txt = "";
        for (String arg : argumentList.getIterator(String.class, 0)) {
            txt += " " + arg;
        }

        ConsoleUtils.printInfo(pluginName, "DONE: /warp create" + txt);
    }
}
