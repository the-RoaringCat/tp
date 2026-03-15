package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
//import seedu.goldencompass.preparser.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Represents a command to list all internship applications in the system.
 * This command displays a numbered list of all internships with their company
 * names and job titles.
 */
public class ListCommand implements Executable {
    /** List of flags accepted by this command */
    private static final ArrayList<String> FLAGS = new ArrayList<>(Arrays.asList("/list"));

    /** Reference to the internship list to display */
    private InternshipList internshipList;

    /**
     * Constructs a ListCommand with the specified internship list.
     *
     * @param internshipList The list of internships to be displayed
     */
    public ListCommand(InternshipList internshipList) {
        this.internshipList = internshipList;
        //Config.registerFlag(FLAGS.toArray(new String[0]));
    }

    /**
     * Executes the list command.
     * Checks for valid flags and displays all internships in the list.
     *
     * @param flagToParamMap Map containing command flags and their parameters
     * @throws GoldenCompassException if invalid flags are provided
     */
    @Override
    public void execute(Map<String, List<String>> flagToParamMap) throws GoldenCompassException {
        checkFlag(flagToParamMap, FLAGS);
        internshipList.list(); // Call the list method we just added
    }
}
