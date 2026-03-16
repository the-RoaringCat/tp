package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.ui.Ui;

import java.util.List;
import java.util.Map;

/**
 * Represents a command to list all internship applications in the system.
 * This command displays a numbered list of all internships with their company
 * names and job titles.
 */
public class ListCommand implements Command {

    /** Reference to the internship list to display */
    private final Ui ui;
    private final Parser parser;
    private final InternshipList internshipList;
    private Map<String, List<String>> flagToParamMap;

    /**
     * Constructs a ListCommand with the specified internship list.
     *
     * @param internshipList The list of internships to be displayed
     */
    public ListCommand(Parser parser, InternshipList internshipList) {
        ui = new Ui();
        this.parser = parser;
        this.internshipList = internshipList;
        flagToParamMap = parser.getFlagToParamMap();
        //Config.registerFlag(FLAGS.toArray(new String[0]));
    }

    /**
     * Executes the list command.
     * Checks for valid flags and displays all internships in the list.
     *
     * @throws GoldenCompassException if invalid flags are provided
     */
    @Override
    public void execute() throws GoldenCompassException {
        // checkFlag(flagToParamMap, FLAGS);
        internshipList.list(); // Call the list method we just added
    }
}
