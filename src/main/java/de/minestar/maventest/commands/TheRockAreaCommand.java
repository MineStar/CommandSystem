package de.minestar.maventest.commands;

import org.bukkit.command.ConsoleCommandSender;

import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;
import de.minestar.maventest.commandsystem.annotations.PermissionNode;
import de.minestar.minestarlibrary.utils.ConsoleUtils;

@Label(label = "area")
@Arguments(arguments = "<RADIUS> [<PlayerName> [TIME|SINCE <1H2M3S>]]")
@PermissionNode(node = "")
@Description(description = "/tr <RADIUS> [<PlayerName> [TIME|SINCE <1H2M3S>]]")
public class TheRockAreaCommand extends AbstractCommand {

    @Override
    public void execute(ConsoleCommandSender console, ArgumentList argumentList) {
        String txt = "";
        for (String arg : argumentList.getIterator(String.class, 0)) {
            txt += " " + arg;
        }

        ConsoleUtils.printInfo(pluginName, "DONE : /tr area" + txt);
    }
}
