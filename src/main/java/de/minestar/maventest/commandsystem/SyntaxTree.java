package de.minestar.maventest.commandsystem;

import java.util.ArrayList;

public class SyntaxTree {
    private static String KEYS_MUST_ARGS = "<>";
    private static String KEYS_OPT_ARGS = "[]";
    private static String KEYS_INF_ARG = "...";

    private int minArguments, countOptArgs, maxArguments;
    private boolean endless = false;
    private SyntaxTree child;
    private final String syntax;

    private ArrayList<Argument> arguments;

    public SyntaxTree(String syntax) {
        this.syntax = syntax;
        this.analyzeSyntax();
    }

    private void analyzeSyntax() {
        this.minArguments = 0;
        this.arguments = new ArrayList<Argument>();

        ArrayList<String> arguments = getArguments(this.syntax);
        String singleArg;
        for (int index = 0; index < arguments.size(); index++) {
            singleArg = arguments.get(index);
            Argument argument = new Argument(singleArg, getArgumentType(singleArg, index));

            if (argument.equals(ArgumentType.NONE)) {
                continue;
            }

            this.arguments.add(argument);

            if (!argument.isOptional() && !argument.isEndless()) {
                ++this.minArguments;
            }
            if (argument.isOptional()) {
                ++countOptArgs;
                this.child = new SyntaxTree(removeSyntaxKeys(singleArg));
                break;
            } else if (argument.isEndless()) {
                endless = true;
                countOptArgs = Integer.MAX_VALUE;
                break;
            }
        }
        this.maxArguments = getMaxArgumentCount();
    }

    public boolean validate(ArgumentList argumentList) {
        if (argumentList.length() < this.minArguments || argumentList.length() > this.calculateMaxArgumentCount()) {
            return endless && argumentList.length() >= this.minArguments;
        }

        int argCount = this.calculateMaxArgumentCount();

        Argument argument;
        String currentArg;
        int newLength = argumentList.length() - 1;
        for (int index = 0; index < argCount && index < argumentList.length(); index++) {
            argument = this.arguments.get(index);
            currentArg = argumentList.getString(index);
            if (argument.isOptional()) {
                if (this.child != null) {
                    if (newLength < child.minArguments || newLength > child.calculateMaxArgumentCount()) {
                        return false;
                    } else {
                        return child.validate(new ArgumentList(argumentList, 1));
                    }
                }
            } else {
                if (argument.isKeyword()) {
                    if (!argument.getArgument().contains("|" + currentArg.toLowerCase() + "|")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int getMaxArgumentCount() {
        return this.maxArguments;
    }

    private int calculateMaxArgumentCount() {
        if (child != null) {
            return countOptArgs + child.calculateMaxArgumentCount();
        }
        return this.minArguments + countOptArgs;
    }

    // ///////////////////////////////////////////////////////////////
    //
    // STATIC METHODS
    //
    // ///////////////////////////////////////////////////////////////

    /**
     * Remove the syntaxkeys from a given String
     * 
     * @param syntax
     * @return Return the syntax without the syntaxkeys.<br />
     *         <b>Example:</b><br />
     *         [<Player> [Player...]] => <Player> [Player]
     */
    private static String removeSyntaxKeys(String syntax) {
        if (syntax.startsWith(" ")) {
            syntax = syntax.substring(1, syntax.length() - 1);
        }
        return syntax.substring(1, syntax.length() - 1);
    }

    /**
     * Get all arguments for a given Syntax
     * 
     * @param syntax
     * @return All arguments for a given Syntax
     */
    private static ArrayList<String> getArguments(String syntax) {
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

    /**
     * Get the ArgumentType for a given argument
     * 
     * @param argument
     * @param currentIndex
     * @return the ArgumentType for the given argument
     */
    private static ArgumentType getArgumentType(String argument, int currentIndex) {
        if (argument.startsWith(String.valueOf(KEYS_MUST_ARGS.charAt(0))) && argument.endsWith(String.valueOf(KEYS_MUST_ARGS.charAt(1)))) {
            return ArgumentType.NEEDED;
        } else if (argument.startsWith(String.valueOf(KEYS_OPT_ARGS.charAt(0))) && argument.endsWith(String.valueOf(KEYS_OPT_ARGS.charAt(1)))) {
            if (argument.contains(KEYS_INF_ARG)) {
                return ArgumentType.ENDLESS;
            } else {
                return ArgumentType.OPTIONAL;
            }
        } else if (currentIndex >= 0) {
            if (argument.length() > 0)
                return ArgumentType.KEYWORD;
        }

        return ArgumentType.NONE;
    }
}
