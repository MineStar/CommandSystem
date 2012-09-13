package de.minestar.maventest.commandsystem;

import java.util.ArrayList;

public class SyntaxTree {

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

        ArrayList<String> arguments = SyntaxHelper.getArguments(this.syntax);
        String singleArg;
        for (int index = 0; index < arguments.size(); index++) {
            singleArg = arguments.get(index);
            Argument argument = new Argument(singleArg, SyntaxHelper.getArgumentType(singleArg, index));

            if (argument.equals(ArgumentType.NONE)) {
                continue;
            }

            this.arguments.add(argument);

            if (!argument.isOptional() && !argument.isEndless()) {
                ++this.minArguments;
            }
            if (argument.isOptional()) {
                ++countOptArgs;
                this.child = new SyntaxTree(SyntaxHelper.removeSyntaxKeys(singleArg));
                break;
            } else if (argument.isEndless()) {
                endless = true;
                countOptArgs = Integer.MAX_VALUE;
                break;
            }
        }
        this.maxArguments = this.precalculateMaxArgumentCount();
    }

    public boolean validate(ArgumentList argumentList) {
        if (argumentList.length() < this.minArguments || argumentList.length() > this.maxArguments) {
            return endless && argumentList.length() >= this.minArguments;
        }

        int argCount = this.maxArguments;

        Argument argument;
        String currentArg;
        int newLength = argumentList.length() - 1;
        for (int index = 0; index < argCount && index < argumentList.length(); index++) {
            argument = this.arguments.get(index);
            currentArg = argumentList.getString(index);
            if (argument.isOptional()) {
                if (this.child != null) {
                    if (newLength < child.minArguments || newLength > child.maxArguments) {
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

    private int precalculateMaxArgumentCount() {
        if (child != null) {
            return countOptArgs + child.precalculateMaxArgumentCount();
        }
        return this.minArguments + countOptArgs;
    }

}
