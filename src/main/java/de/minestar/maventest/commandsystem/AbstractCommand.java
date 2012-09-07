package de.minestar.maventest.commandsystem;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;

import de.minestar.maventest.annotations.Arguments;
import de.minestar.maventest.annotations.Description;
import de.minestar.maventest.annotations.Label;
import de.minestar.maventest.annotations.PermissionNode;
import de.minestar.minestarlibrary.utils.ConsoleUtils;

public class AbstractCommand {

    private String pluginName;

    private boolean superCommand = false;
    private AbstractCommand parentCommand;

    private int minArgumentCount = 0;
    private int maxArgumentCount = 0;
    private final String commandLabel;
    private String syntax, permissionNode, description;

    public HashMap<String, AbstractCommand> inheritedCommands;

    private Annotation getAnnotation(Class<? extends Annotation> clazz) {
        Annotation[] annos = this.getClass().getAnnotations();
        for (Annotation anno : annos) {
            if (clazz.getSimpleName().equalsIgnoreCase(anno.annotationType().getSimpleName())) {
                return anno;
            }
        }
        return null;
    }

    public AbstractCommand() {
        this.pluginName = "";
        this.parentCommand = null;

        Label labelAnnotation = this.getClass().getAnnotation(Label.class);
        Arguments syntaxAnnotation = this.getClass().getAnnotation(Arguments.class);
        PermissionNode nodeAnnotation = this.getClass().getAnnotation(PermissionNode.class);
        Description descriptionAnnotation = this.getClass().getAnnotation(Description.class);

        if (labelAnnotation == null) {
            this.commandLabel = "";
            throw new RuntimeException("Could not create command '" + this.getClass().getSimpleName() + "'! Commandlabel is missing!");
        } else {
            this.commandLabel = labelAnnotation.label().replace(" ", "");
            if (this.commandLabel.length() < 1) {
                throw new RuntimeException("Could not create command '" + this.getClass().getSimpleName() + "'! Commandlabel is missing!");
            }
        }

        if (syntaxAnnotation == null) {
            this.syntax = "";
        } else {
            this.syntax = syntaxAnnotation.arguments();
        }

        if (nodeAnnotation == null) {
            this.permissionNode = "";
        } else {
            this.permissionNode = nodeAnnotation.node();
        }

        if (descriptionAnnotation == null) {
            this.description = "";
        } else {
            this.description = descriptionAnnotation.description();
        }
        this.countArguments();

        this.inheritedCommands = new HashMap<String, AbstractCommand>();

    }
    private void countArguments() {
        char key;
        for (int i = 0; i < this.syntax.length(); ++i) {
            key = this.syntax.charAt(i);
            if (key == '<') {
                ++this.minArgumentCount;
                ++this.maxArgumentCount;
            } else if (key == '[') {
                ++this.maxArgumentCount;
            }
        }
    }

    public boolean hasOptionalCommands() {
        return (this.minArgumentCount != this.maxArgumentCount);
    }

    public int getMinArgumentCount() {
        return minArgumentCount;
    }

    public int getMaxArgumentCount() {
        return maxArgumentCount;
    }

    public void initialize() {
        this.createSubCommands();
        this.superCommand = (this.inheritedCommands.size() > 0);
    }

    public boolean isSuperCommand() {
        return superCommand;
    }

    protected void createSubCommands() {
    }

    public String getCompleteCommand() {
        String label = this.getCommandLabel();
        AbstractCommand parent = this.parentCommand;
        while (parent != null) {
            label = parent.getCommandLabel() + " " + label;
            parent = parent.getParentCommand();
        }
        return label;
    }

    public void setParentCommand(AbstractCommand command) {
        this.parentCommand = command;
        this.pluginName = command.pluginName;
    }

    public void updateInheritedCommands() {
        for (AbstractCommand command : this.inheritedCommands.values()) {
            command.setParentCommand(this);
        }
    }

    private AbstractCommand getParentCommand() {
        return parentCommand;
    }

    public String getCommandLabel() {
        return commandLabel;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public ArrayList<AbstractCommand> getInheritedCommands() {
        return new ArrayList<AbstractCommand>(inheritedCommands.values());
    }

    public String getSyntax() {
        return syntax;
    }

    public String getPermissionNode() {
        return permissionNode;
    }

    public String getDescription() {
        return description;
    }

    protected boolean registerCommand(AbstractCommand command) {
        if (this.inheritedCommands.containsKey(command.getCommandLabel())) {
            ConsoleUtils.printError("Command '" + command.getCommandLabel() + "' is already registered in '" + this.getCompleteCommand() + "'!");
            return false;
        }
        command.setPluginName(pluginName);
        command.setParentCommand(this);
        command.initialize();
        this.inheritedCommands.put(command.getCommandLabel(), command);
        ConsoleUtils.printInfo(this.pluginName, "OK --> Registered subcommand '" + command.getCommandLabel() + "' in '" + this.getCompleteCommand() + "'!");
        return true;
    }

    public void execute(String[] arguments) {
        // TODO Auto-generated method stub

    }

    public void printSyntax() {
        ConsoleUtils.printError(this.pluginName, "Wrong syntax!");
        ConsoleUtils.printInfo(this.getCompleteCommand() + " " + this.syntax);
    }

    public void listCommands() {
        ConsoleUtils.printInfo("");

        ConsoleUtils.printInfo(this.getCommandLabel() + '\t' + "-> Description: " + this.description + '\t' + "-> " + this.permissionNode);
        ArrayList<AbstractCommand> subCommands = getInheritedCommands();
        for (AbstractCommand subCommand : subCommands) {
            ConsoleUtils.printInfo("-> " + '\t' + subCommand.getCommandLabel() + " " + subCommand.getSyntax() + '\t' + "-> Description: " + subCommand.getDescription() + '\t' + "-> " + subCommand.getPermissionNode());
        }
    }
    public boolean handleCommand(String label, String[] arguments) {
        label = label.toLowerCase();

        AbstractCommand command = this.inheritedCommands.get(label);
        if (command == null) {
            ConsoleUtils.printError(pluginName, "Command '" + this.commandLabel + " " + label + "' not found.");
            return false;
        }

        if (command.isSuperCommand()) {
            // handle supercommands
            if (arguments.length > 0) {
                label = arguments[0];
                String[] newArguments = new String[arguments.length - 1];
                System.arraycopy(arguments, 1, newArguments, 0, newArguments.length);
                command.handleCommand(label, newArguments);
            } else {
                command.printSyntax();
            }
            return true;
        } else {
            // handle normal command
            // check argumentcount & try to execute it
            if ((!command.hasOptionalCommands() && arguments.length == command.getMinArgumentCount()) || (command.hasOptionalCommands() && arguments.length >= command.getMinArgumentCount() && arguments.length <= command.getMaxArgumentCount())) {
                command.execute(arguments);
            } else if (command.hasOptionalCommands()) {
                command.printSyntax();
            } else {
                command.printSyntax();
            }
            return true;
        }
    }

}
