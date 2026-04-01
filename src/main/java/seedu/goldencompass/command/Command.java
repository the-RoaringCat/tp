package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.ui.Ui;

public class Command implements Executable {
    private static final String FLAG_MISSING_MESSAGE = "Error: This command requires this flag: ";
    /** Package-private UI and parser */
    protected Ui ui;
    protected Parser parser;

    /** No-argument constructor, automatically called in the constructors of subclasses */
    public Command() {
        ui = new Ui();
    }

    public Command(Parser parser) {
        ui = new Ui();
        this.parser = parser;
    }

    @Override
    public void execute() throws GoldenCompassException {

    }

    private void printHelp(String commandDescription, String flagDescription) {
        ui.print(commandDescription, flagDescription);
    }

    protected boolean checkHelpFlag(String commandDescription, String flagDescription) {
        if(parser.isFlagExist("/help")) {
            printHelp(commandDescription, flagDescription);
            return true;
        }
        return false;
    }

    protected void checkFlagPresence(String... flags) throws GoldenCompassException {
        StringBuilder errorMessage = new StringBuilder();

        for(String flag : flags) {
            if(!parser.isFlagExist(flag)) {
                errorMessage.append(FLAG_MISSING_MESSAGE).append(flag).append(System.lineSeparator());
            }
        }

        if(!errorMessage.isEmpty()) {
            throw new GoldenCompassException(errorMessage.toString());
        }
    }

}
