package de.minestar.maventest.commands;

import de.minestar.maventest.annotations.Description;
import de.minestar.maventest.annotations.Execution;
import de.minestar.maventest.annotations.Label;
import de.minestar.maventest.annotations.PermissionNode;
import de.minestar.maventest.annotations.Arguments;
import de.minestar.maventest.commandsystem.AbstractCommand;

@Label(label = "/warp")
@Arguments(arguments = "")
@PermissionNode(node = "")
@Description(description = "This is a supercommand. It has no arguments and (in this case) no permissionnode.")
@Execution(executeSuperCommand = true)
public class WarpCommandExample extends AbstractCommand {

    @Override
    protected void createSubCommands() {
        this.registerCommand(new WarpCreateCommandExample());
    }

    @Override
    public void execute(String[] arguments) {
        String args = "";
        for (String t : arguments) {
            args += t + " ";
        }
        System.out.println("execute /warp " + args);
    }
}
