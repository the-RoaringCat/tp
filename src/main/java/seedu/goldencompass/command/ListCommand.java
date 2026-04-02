package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;

import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a command to list all internship applications in the system.
 * This command displays a numbered list of all internships with their company
 * names and job titles.
 */
public class ListCommand extends Command {

    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());

    /** Reference to the internship list to display */
    private final InternshipList internshipList;

    /**
     * Constructs a ListCommand with the specified internship list.
     *
     * @param internshipList The list of internships to be displayed
     */
    public ListCommand(Parser parser, InternshipList internshipList) {

        // This gives access to checkHelpFlag()
        super(parser);

        // Defensive check: internshipList cannot be null
        if (internshipList == null) {
            logger.severe("Attempted to create ListCommand with null InternshipList");
            throw new IllegalArgumentException("InternshipList cannot be null");
        }

        this.internshipList = internshipList;
        logger.info("ListCommand initialized");

        // Assertion to verify state
        assert this.internshipList != null : "InternshipList should be set";
    }

    @Override
    public String getCommandDescription() {
        return "Lists all internships in the tracker.";
    }

    @Override
    public String getFlagDescription() {
        return "Format: list";
    }

    /**
     * Displays all internships in the list with their index numbers.
     * Shows company name and job title for each internship.
     * If the list is empty, displays a message indicating no internships.
     *
     * Checks for valid flags and displays all internships in the list.
     *
     * @throws GoldenCompassException if invalid flags are provided
     */
    @Override
    public void execute() throws GoldenCompassException {

        logger.info("Executing ListCommand");

        try {
            // Defensive check: ensure internshipList is valid
            if (internshipList == null) {
                logger.severe("InternshipList is null during execution");
                throw new GoldenCompassException("System error: Internship list not initialized");
            }

            // Get internships list
            List<Internship> internships = internshipList.getInternships();

            // Defensive check: internships list should not be null
            if (internships == null) {
                logger.severe("getInternships() returned null");
                throw new GoldenCompassException("System error: Unable to retrieve internship list");
            }

            //Check if list is empty
            if (internships.isEmpty()) {
                ui.print("No internships in the list.");
                logger.fine("List is empty, displayed message");
                return;
            }

            // Display header
            ui.print("Here are the internships you have added:");
            logger.finer("Displaying " + internships.size() + " internships");

            // Display each internship
            for (int i = 0; i < internships.size(); i++) {
                Internship intern = internships.get(i);

                // Defensive check: each internship should not be null
                if (intern == null) {
                    logger.warning("Null internship found at index " + i);
                    ui.print((i + 1) + ". [Error: Invalid internship data]");
                } else {
                    ui.print((i + 1) + ". " + intern);
                }
            }

            logger.info("ListCommand executed successfully, displayed " + internships.size() +
                    " internships");

        } catch (GoldenCompassException e) {
            // Re-throw GoldenCompassException
            throw e;
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            logger.severe("Unexpected error in ListCommand: " + e.getMessage());
            throw new GoldenCompassException("Error displaying internships: " + e.getMessage());
        }
    }
}
