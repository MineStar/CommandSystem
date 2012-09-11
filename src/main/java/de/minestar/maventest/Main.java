package de.minestar.maventest;

import de.minestar.maventest.commandsystem.ArgumentTree;

public class Main {

    public static void test(ArgumentTree argTree, String[] arguments) {
        System.out.println("\n");
        boolean result = argTree.validate(arguments);
        System.out.println("valid syntax: " + result);
    }

    /**
     * @param args
     */
    public static void main(String[] arguments) {
        ArgumentTree argTree;
        argTree = new ArgumentTree("<RADIUS> [<PlayerName> [TIME <1H2M3S>]] ");

        String[] args;

        args = new String[0];
        test(argTree, args);
        //
        args = new String[1];
        args[0] = "20";
        test(argTree, args);
        //
        args = new String[2];
        args[0] = "20";
        args[1] = "GeMoschen";
        test(argTree, args);

        args = new String[3];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "TIME";
        test(argTree, args);
        //
        args = new String[4];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "TIME";
        args[3] = "1D";
        test(argTree, args);
        //
        args = new String[4];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "TIM";
        args[3] = "1D";
        test(argTree, args);
    }
}
