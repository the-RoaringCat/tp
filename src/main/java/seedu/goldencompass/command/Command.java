package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.ui.Ui;

public abstract class Command implements Executable {
    private static final String FLAG_MISSING_MESSAGE = "Error: This command requires this flag: ";

    protected Ui ui;
    protected Parser parser;

    public Command() {
        ui = new Ui();
    }

    public Command(Parser parser) {
        this.ui = new Ui();
        this.parser = parser;
    }

    @Override
    public void execute() throws GoldenCompassException {
    }

    protected abstract String getCommandDescription();
    protected abstract String getFlagDescription();


    protected void printHelp() {
        ui.print(getCommandDescription(), getFlagDescription());
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
