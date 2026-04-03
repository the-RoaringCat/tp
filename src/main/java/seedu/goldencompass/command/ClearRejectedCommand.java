package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Removes all internships marked as rejected from the list.
 * Also deletes any associated interviews to prevent orphaned records.
 * <p>
 * Command format: {@code clear-rejected}
 * </p>
 */
public class ClearRejectedCommand extends Command {

    public static final String COMMAND_WORD = "clear-rejected";

    private static final Logger logger = Logger.getLogger(ClearRejectedCommand.class.getName());

    private final InternshipList internshipList;
    private final InterviewList interviewList;

    /**
     * Constructs a {@code ClearRejectedCommand}.
     *
     * @param internshipList the list of internships to clear rejected entries from.
     * @param interviewList  the list of interviews, used to delete orphaned interviews.
     */
    public ClearRejectedCommand(InternshipList internshipList, InterviewList interviewList) {
        assert internshipList != null : "InternshipList passed to ClearRejectedCommand cannot be null";
        assert interviewList != null : "InterviewList passed to ClearRejectedCommand cannot be null";
        this.internshipList = internshipList;
        this.interviewList = interviewList;
    }

    /**
     * Executes the clear-rejected command.
     * Finds all rejected internships, deletes their associated interviews,
     * removes them from the list, and prints a summary.
     *
     * @throws GoldenCompassException if an unexpected error occurs.
     */
    @Override
    public void execute() throws GoldenCompassException {
        logger.log(Level.INFO, "Starting execution of ClearRejectedCommand...");

        assert internshipList != null : "InternshipList should not be null";
        assert interviewList != null : "InterviewList should not be null";

        List<Internship> internships = internshipList.getInternships();
        assert internships != null : "Internships list should not be null";

        List<Internship> rejected = internships.stream()
                .filter(Internship::isRejected)
                .toList();

        if (rejected.isEmpty()) {
            logger.log(Level.INFO, "No rejected internships to clear.");
            ui.print("No rejected internships to clear.");
            return;
        }

        logger.log(Level.INFO, "Found " + rejected.size() + " rejected internship(s) to clear.");

        for (Internship internship : rejected) {
            if (internship.getInterview() != null) {
                interviewList.deleteByInternship(internship);
                logger.log(Level.INFO, "Deleted associated interview for: "
                        + internship.getCompanyName());
            }
        }

        internships.removeAll(rejected);

        logger.log(Level.INFO, "Successfully cleared " + rejected.size() + " rejected internship(s).");
        ui.print("Cleared " + rejected.size() + " rejected internship(s):");
        for (Internship internship : rejected) {
            ui.print("  - " + internship);
        }
    }

    @Override
    protected String getCommandDescription() {
        return "Removes all internships marked as rejected from the list.\n"
                + "Format: clear-rejected";
    }

    @Override
    protected String getFlagDescription() {
        return "This command does not take any flags.";
    }
}
