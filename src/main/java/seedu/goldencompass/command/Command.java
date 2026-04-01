package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.ui.Ui;

public class Command implements Executable {

    /** Package-private UI and parser */
    Ui ui;
    Parser parser;

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

}
