package de.minestar.maventest.commandsystem;

import java.util.ArrayList;

public class ArgumentTree {
    private static String KEYS_MUST_ARGS = "<>";
    private static String KEYS_OPT_ARGS = "[]";
    private static String KEYS_INF_ARG = "...";

    private int minArguments, countOptArgs, maxArguments;
    private boolean endless = false;
    private ArgumentTree child;
    private final String syntax;

    private ArrayList<ArgumentType> argList;
    private ArrayList<String> singleArgs;

    public ArgumentTree(String syntax) {
        this.syntax = syntax;
        this.prepareSyntax();
    }

    private void prepareSyntax() {
        this.minArguments = 0;
        this.argList = new ArrayList<ArgumentType>();
        this.singleArgs = new ArrayList<String>();
        ArrayList<String> arguments = getArguments(this.syntax);
        ArgumentType argType;
        int index = 0;
        for (String singleArg : arguments) {
            argType = getArgumentType(singleArg, index);
            index++;

            if (argType.equals(ArgumentType.NONE)) {
                continue;
            }

            argList.add(argType);
            singleArgs.add(("|" + singleArg + "|").toLowerCase());

            if (!argType.equals(ArgumentType.OPTIONAL) && !argType.equals(ArgumentType.ENDLESS) && !argType.equals(ArgumentType.NONE)) {
                ++this.minArguments;
            }
            if (argType.equals(ArgumentType.OPTIONAL)) {
                ++countOptArgs;
                this.child = new ArgumentTree(getArgumentWithoutArg(singleArg));
                break;
            } else if (argType.equals(ArgumentType.ENDLESS)) {
                endless = true;
                countOptArgs = Integer.MAX_VALUE;
                break;
            }
        }

        this.maxArguments = getMaxArgumentCount();
    }

    public boolean validate(ArgumentList argumentList) {
        if (argumentList.length() < this.getMinArguments() || argumentList.length() > this.calculateMaxArgumentCount()) {
            return endless && argumentList.length() >= this.getMinArguments();
        }

        int argCount = this.calculateMaxArgumentCount();

        ArgumentType type;
        String singleArg, currentArg;
        int newLength = argumentList.length() - 1;
        for (int index = 0; index < argCount && index < argumentList.length(); index++) {
            type = this.argList.get(index);
            singleArg = this.singleArgs.get(index);
            currentArg = argumentList.getString(index);
            if (type.equals(ArgumentType.OPTIONAL)) {
                if (this.child != null) {
                    if (newLength < child.getMinArguments() || newLength > child.calculateMaxArgumentCount()) {
                        return false;
                    } else {
                        return child.validate(new ArgumentList(argumentList, 1));
                    }
                }
            } else {
                if (type.equals(ArgumentType.KEYWORD)) {
                    if (!singleArg.contains("|" + currentArg.toLowerCase() + "|")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int getMinArguments() {
        return minArguments;
    }

    public int getMaxArgumentCount() {
        return this.maxArguments;
    }

    public int calculateMaxArgumentCount() {
        if (child != null) {
            return countOptArgs + child.calculateMaxArgumentCount();
        }
        return this.minArguments + countOptArgs;
    }

    private static String getArgumentWithoutArg(String syntax) {
        if (syntax.startsWith(" ")) {
            syntax = syntax.substring(1, syntax.length() - 1);
        }
        return syntax.substring(1, syntax.length() - 1);
    }

    public static ArrayList<String> getArguments(String syntax) {
        ArrayList<String> arguments = new ArrayList<String>();
        char key;
        String argument = "";
        boolean mustLocked = false, optLocked = false;
        int lockIndex = 0;
        for (int index = 0; index < syntax.length(); index++) {
            key = syntax.charAt(index);
            argument += key;

            if (key == KEYS_MUST_ARGS.charAt(0) && !mustLocked && !optLocked) {
                mustLocked = true;
                lockIndex = 1;
                continue;
            }
            if (key == KEYS_MUST_ARGS.charAt(1) && mustLocked) {
                --lockIndex;
                if (lockIndex == 0) {
                    mustLocked = false;
                    arguments.add(argument);
                    argument = "";
                }
                continue;
            }
            if (key == KEYS_OPT_ARGS.charAt(0) && !mustLocked) {
                if (optLocked) {
                    ++lockIndex;
                } else {
                    optLocked = true;
                    lockIndex = 1;
                }
                continue;
            }

            if (key == KEYS_OPT_ARGS.charAt(1) && optLocked) {
                --lockIndex;
                if (lockIndex == 0) {
                    mustLocked = false;
                    arguments.add(argument);
                    argument = "";
                }
                continue;
            }

            if (key == ' ' && !optLocked && !mustLocked) {
                argument = argument.replace(" ", "");
                if (!argument.equalsIgnoreCase(" ") && !argument.equalsIgnoreCase("")) {
                    arguments.add(argument);
                }
                argument = "";
                continue;
            }
        }

        if (argument.length() > 0) {
            arguments.add(argument);
        }
        return arguments;
    }

    private static ArgumentType getArgumentType(String text, int currentIndex) {
        if (text.startsWith(String.valueOf(KEYS_MUST_ARGS.charAt(0))) && text.endsWith(String.valueOf(KEYS_MUST_ARGS.charAt(1)))) {
            return ArgumentType.NEEDED;
        } else if (text.startsWith(String.valueOf(KEYS_OPT_ARGS.charAt(0))) && text.endsWith(String.valueOf(KEYS_OPT_ARGS.charAt(1)))) {
            if (text.contains(KEYS_INF_ARG)) {
                return ArgumentType.ENDLESS;
            } else {
                return ArgumentType.OPTIONAL;
            }
        } else if (currentIndex >= 0) {
            if (text.length() > 0)
                return ArgumentType.KEYWORD;
        }

        return ArgumentType.NONE;
    }
}
