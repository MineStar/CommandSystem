package de.minestar.maventest.commandsystem;

public class SyntaxValidationResult {

    private final String reason;
    private final int errorIndex;

    public SyntaxValidationResult(String reason, int errorIndex) {
        this.reason = reason;
        this.errorIndex = errorIndex;
    }

    public String getReason() {
        return reason;
    }

    public int getErrorIndex() {
        return errorIndex;
    }
}
