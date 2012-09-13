package de.minestar.maventest;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.minestar.maventest.commands.NormalCommand;
import de.minestar.maventest.commands.FlashCommand;
import de.minestar.maventest.commands.ClearInventoryCommand;
import de.minestar.maventest.commands.TheRockCommand;
import de.minestar.maventest.commands.WarpCommand;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.CommandHandler;

public class MavenTestCore extends JavaPlugin {

    public static final String NAME = "CommandSystem";
    private CommandHandler commandHandler;

    public MavenTestCore() {
        this.createCommands();
    }

    protected boolean createCommands() {
        commandHandler = new CommandHandler(NAME);

        try {
            this.registerCommand(new WarpCommand());
            this.registerCommand(new ClearInventoryCommand());
            this.registerCommand(new TheRockCommand());
            this.registerCommand(new NormalCommand());
            this.registerCommand(new FlashCommand());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public final void listCommands(ConsoleCommandSender sender) {
        this.commandHandler.listCommands(Bukkit.getConsoleSender());
    }

    public final void registerCommand(AbstractCommand command) {
        try {
            commandHandler.registerCommand(command);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return this.commandHandler.handleCommand(sender, label, args);
    }

}
