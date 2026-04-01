package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;

/**
 * All command should implement this interface in order to be executed.
 */
public interface Executable {

    void execute() throws GoldenCompassException;
}
