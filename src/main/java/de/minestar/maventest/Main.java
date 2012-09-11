package de.minestar.maventest;

import de.minestar.maventest.commandsystem.ArgumentTree;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] arguments) {
        ArgumentTree argTree;
        argTree = new ArgumentTree("<RADIUS> [<PlayerName> [TIME <1H2M3S>]] ");

        String[] args;
        args = new String[0];
        System.out.println("\nvalid 0: " + argTree.validate(args));

        args = new String[1];
        args[0] = "20";
        argTree.validate(args);
        System.out.println("\nvalid 1: " + argTree.validate(args));

        args = new String[2];
        args[0] = "20";
        args[1] = "GeMoschen";
        System.out.println("\nvalid 2: " + argTree.validate(args));

        args = new String[3];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "TIME";
        System.out.println("\nvalid 3: " + argTree.validate(args));

        args = new String[4];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "TIME";
        args[3] = "1D";
        System.out.println("\nvalid 4 : " + argTree.validate(args));
    }
}
