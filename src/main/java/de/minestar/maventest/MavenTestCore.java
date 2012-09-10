package de.minestar.maventest;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.minestar.maventest.commands.NormalCommandExample;
import de.minestar.maventest.commands.WarpCommandExample;
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
            this.registerCommand(new WarpCommandExample());
            this.registerCommand(new NormalCommandExample());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        this.listCommands(Bukkit.getConsoleSender());
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
