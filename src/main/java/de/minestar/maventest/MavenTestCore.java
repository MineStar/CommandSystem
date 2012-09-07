package de.minestar.maventest;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.minestar.maventest.commands.NormalCommandExample;
import de.minestar.maventest.commands.WarpCommandExample;
import de.minestar.maventest.commandsystem.CommandHandler;

public class MavenTestCore extends JavaPlugin {

    public static final String NAME = "CommandSystem";
    private CommandHandler commandHandler;

    public MavenTestCore() {
        this.createCommands();
    }

    protected boolean createCommands() {
        commandHandler = new CommandHandler(NAME);
        commandHandler.registerCommand(new WarpCommandExample());
        commandHandler.registerCommand(new NormalCommandExample());
        commandHandler.listCommands(Bukkit.getConsoleSender());
        return true;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return this.commandHandler.handleCommand(sender, label, args);
    }

}
