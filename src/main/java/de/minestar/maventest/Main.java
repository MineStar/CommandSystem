package de.minestar.maventest;

import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.SyntaxTree;

public class Main {

    public static void runTest(SyntaxTree argTree, String[] arguments) {
        boolean result = argTree.checkSyntax(new ArgumentList(arguments));
        System.out.println(new ArgumentList(arguments).toString());
        System.out.println("valid: " + result + "\n");
    }

    public static void DoTest_1() {

        System.out.println("--------------------------------------------------------------");
        System.out.println("SYNTAX: " + "<RADIUS> [<PlayerName> [TIME|SINCE <1H2M3S>]]");
        System.out.println("--------------------------------------------------------------");

        SyntaxTree argTree;
        argTree = new SyntaxTree("<RADIUS> [<PlayerName> [TIME|SINCE <1H2M3S>]]");

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
        System.out.println("--------------------------------------------------------------");
        System.out.println("SYNTAX: " + "<PlayerName> [PlayerName ...]");
        System.out.println("--------------------------------------------------------------");

        SyntaxTree argTree;
        argTree = new SyntaxTree("<PlayerName> [PlayerName ...]");

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
        System.out.println("--------------------------------------------------------------");
        System.out.println("SYNTAX: " + "<RADIUS> [<PlayerName> [<PlayerName>]]");
        System.out.println("--------------------------------------------------------------");
        SyntaxTree argTree;
        argTree = new SyntaxTree("<RADIUS> [<PlayerName> [<PlayerName>]]");

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
//        DoTest_1();
        DoTest_2();
//        DoTest_3();
    }
}
