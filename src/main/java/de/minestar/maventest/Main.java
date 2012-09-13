package de.minestar.maventest;

import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.ArgumentTree;

public class Main {

    public static void runTest(ArgumentTree argTree, String[] arguments) {
        System.out.println("\n");
        boolean result = argTree.validate(new ArgumentList(arguments));
        System.out.println("valid syntax: " + result);
    }

    public static void DoTest_1() {
        ArgumentTree argTree;
        argTree = new ArgumentTree("<RADIUS> [<PlayerName> [TIME|SINCE <1H2M3S>]]");

        String[] args;

        args = new String[0];
        runTest(argTree, args);
        //
        args = new String[1];
        args[0] = "20";
        runTest(argTree, args);
        //
        args = new String[2];
        args[0] = "20";
        args[1] = "GeMoschen";
        runTest(argTree, args);

        args = new String[3];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "TIME";
        runTest(argTree, args);

        args = new String[3];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "SINCE";
        runTest(argTree, args);

        //
        args = new String[4];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "TIME";
        args[3] = "1D";
        runTest(argTree, args);
        //
        args = new String[4];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "TIM";
        args[3] = "1D";
        runTest(argTree, args);

        args = new String[4];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "SINCE";
        args[3] = "1D";
        runTest(argTree, args);
    }

    public static void DoTest_2() {
        ArgumentTree argTree;
        argTree = new ArgumentTree("<PlayerName> [PlayerName ...] ");

        String[] args;

        args = new String[0];
        runTest(argTree, args);
        //
        args = new String[1];
        args[0] = "gemoschen";
        runTest(argTree, args);

        //
        args = new String[2];
        args[0] = "meldanor";
        args[1] = "GeMoschen";
        runTest(argTree, args);

        //
        args = new String[3];
        args[0] = "meldanor";
        args[1] = "GeMoschen";
        args[2] = "leif";
        runTest(argTree, args);
    }

    public static void DoTest_3() {
        ArgumentTree argTree;
        argTree = new ArgumentTree("<RADIUS> [<PlayerName> [<PlayerName>]] ");

        String[] args;

        args = new String[0];
        runTest(argTree, args);
        //
        args = new String[1];
        args[0] = "20";
        runTest(argTree, args);

        //
        args = new String[2];
        args[0] = "20";
        args[1] = "GeMoschen";
        runTest(argTree, args);

        //
        args = new String[3];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "leif";
        runTest(argTree, args);

        args = new String[4];
        args[0] = "20";
        args[1] = "GeMoschen";
        args[2] = "leif";
        args[3] = "mel";
        runTest(argTree, args);
    }

    /**
     * @param args
     */
    public static void main(String[] arguments) {
        DoTest_1();
//         DoTest_2();
//        DoTest_3();
    }
}
