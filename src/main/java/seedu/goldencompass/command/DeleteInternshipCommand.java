package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Represents a command to delete an internship application from the system.
 * This command removes an internship by its index number.
 */
public class DeleteInternshipCommand extends Command {

    private static final Logger logger = Logger.getLogger(DeleteInternshipCommand.class.getName());
    private final InternshipList internshipList;

    public DeleteInternshipCommand(Parser parser, InternshipList internshipList) {
        super(parser);
        //assert parser != null : "Parser passed to DeleteInternshipCommand cannot be null";
        assert internshipList != null : "InternshipList passed to DeleteInternshipCommand cannot be null";

        this.internshipList = internshipList;
        logger.log(Level.INFO, "DeleteInternshipCommand initialized");
    }

    /**
     * Executes the command to delete an internship.
     * Extracts the index from the parsed user input and deletes the corresponding Internship.
     *
     * @throws GoldenCompassException If the index is missing, not a number, or out of bounds.
     */
    @Override
    public void execute() throws GoldenCompassException {
        logger.log(Level.INFO, "Starting execution of DeleteInternshipCommand...");

        // Get the parameters for the "delete" command
        List<String> params = parser.getParamsOf("delete");

        // 1. DEFENSIVE CHECK: Missing index
        if (params == null || params.isEmpty() || params.get(0).trim().isEmpty()) {
            logger.log(Level.WARNING, "Failed to delete: Index is missing.");
            throw new GoldenCompassException("Please provide the index of the internship to delete! (e.g., delete 1)");
        }

        int index;
        // 2. DEFENSIVE CHECK: Not a number
        try {
            index = Integer.parseInt(params.get(0).trim());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Failed to delete: Index is not a number.");
            throw new GoldenCompassException("The index must be a number! (e.g., delete 1)");
        }

        // 3. DEFENSIVE CHECK: Out of bounds
        if (index < 1 || index > internshipList.getSize()) {
            logger.log(Level.WARNING, "Failed to delete: Index out of bounds.");
            throw new GoldenCompassException("Invalid index! Please enter a number between 1 and "
                    + internshipList.getSize());
        }

        // --- If the code reaches here, the input is valid ---

        // Get the internship (0-indexed)
        Internship internship = internshipList.get(index - 1);
        String internshipName = internship.getCompanyName() + " - " + internship.getTitle();

        // Delete the internship
        internshipList.delete(index - 1);

        logger.log(Level.INFO, "Successfully deleted internship at index " + index);
        ui.print("✓ Deleted internship: " + internshipName);
    }
}
