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

@Label(label = "/flash")
@Arguments(arguments = "<PlayerName> [PlayerName...]")
@PermissionNode(node = "my.node")
@Description(description = "This is a normal command. It is a single command with 1 needed parameter and endless optional parameters. The permissionnode is 'my.node'.")
public class FlashCommand extends AbstractCommand {

    @Override
    public void execute(Player player, ArgumentList argumentList) {
        String txt = "";
        for (String arg : argumentList.getIterator(String.class, 0)) {
            txt += " " + arg;
        }
        PlayerUtils.sendSuccess(player, "DONE: /flash" + txt);
    }

    @Override
    public void execute(ConsoleCommandSender console, ArgumentList argumentList) {
        String txt = "";
        for (String arg : argumentList.getIterator(String.class, 0)) {
            txt += " " + arg;
        }
        ConsoleUtils.printInfo(pluginName, "DONE: /flash" + txt);
    }
}
