package de.minestar.maventest;

import de.minestar.maventest.commands.NormalCommandExample;
import de.minestar.maventest.commands.WarpCommandExample;
import de.minestar.maventest.commandsystem.CommandHandler;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        CommandHandler commandHandler = new CommandHandler("PluginName");
        commandHandler.registerCommand(new WarpCommandExample());
        commandHandler.registerCommand(new NormalCommandExample());

        // commandHandler.listCommands();

        // TEST 1 : /warp create
        // this tests the execution of a subcommand with too less arguments
        String[] arguments = new String[1];
        arguments[0] = "create";
        System.out.println('\n' + "Executing /warp create");
        commandHandler.handleCommand("/warp", arguments);

        // TEST 2 : /warp create test
        // this tests the CORRECT execution of a subcommand
        arguments = new String[2];
        arguments[0] = "create";
        arguments[1] = "test";
        System.out.println('\n' + "Executing /warp create test");
        commandHandler.handleCommand("/warp", arguments);

        // TEST 3 : /warp create test haha
        // this tests the execution of a subcommand with too many arguments
        arguments = new String[3];
        arguments[0] = "create";
        arguments[1] = "test";
        arguments[2] = "haha";
        System.out.println('\n' + "Executing /warp create test haha");
        commandHandler.handleCommand("/warp", arguments);

        // TEST 4 : /warp test
        // this tests the execution of supercommands with the correct arguments
        arguments = new String[0];
        System.out.println('\n' + "Executing /warp");
        commandHandler.handleCommand("/warp", arguments);

        // TEST 5 : /warp test
        // this tests the execution of supercommands with the correct arguments
        arguments = new String[1];
        arguments[0] = "test";
        System.out.println('\n' + "Executing /warp test");
        commandHandler.handleCommand("/warp", arguments);

        // TEST 6 : /warp test
        // this tests the execution of supercommands with too many arguments
        arguments = new String[2];
        arguments[0] = "test";
        arguments[1] = "test-2";
        System.out.println('\n' + "Executing /warp test test-2");
        commandHandler.handleCommand("/warp", arguments);
    }

}
