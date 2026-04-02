package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DeleteInternshipCommand extends Command {

    private static final Logger logger = Logger.getLogger(DeleteInternshipCommand.class.getName());
    private final InternshipList internshipList;
    private final InterviewList interviewList;  // ADD THIS

    public DeleteInternshipCommand(Parser parser, InternshipList internshipList, InterviewList interviewList) {
        super(parser);

        assert parser != null : "Parser passed to DeleteInternshipCommand cannot be null";
        assert internshipList != null : "InternshipList passed to DeleteInternshipCommand cannot be null";

        this.internshipList = internshipList;
        this.interviewList = interviewList;  // ADD THIS
    }

    @Override
    public String getCommandDescription() {
        return "";
    }

    @Override
    public String getFlagDescription() {
        return "";
    }


    @Override
    public void execute() throws GoldenCompassException {
        // Check for /help flag first
        if (checkHelpFlag(
                "Deletes an internship application from the tracker.",
                "Format: delete INDEX\n"
                        + "Example: delete 2\n\n"
                        + "Note: When an internship is deleted, " +
                        "its corresponding interview will also be deleted automatically."
        )) {
            return;
        }
        logger.log(Level.INFO, "Starting execution of DeleteInternshipCommand...");

        List<String> params = parser.getParamsOf(parser.getCommand());

        if (params == null || params.isEmpty() || params.get(0).trim().isEmpty()) {
            throw new GoldenCompassException(
                    "Please provide the index of the internship to delete! (e.g., delete 1)");
        }

        int index;
        try {
            index = Integer.parseInt(params.get(0).trim());
        } catch (NumberFormatException e) {
            throw new GoldenCompassException("The index must be a number! (e.g., delete 1)");
        }

        if (index < 1 || index > internshipList.getSize()) {
            throw new GoldenCompassException("Invalid index! Please enter a number between 1 and "
                    + internshipList.getSize());
        }

        Internship internship = internshipList.get(index - 1);
        String internshipName = internship.getCompanyName() + " - " + internship.getTitle();

        // Delete associated interview if it exists
        if (internship.getInterview() != null) {
            interviewList.deleteByInternship(internship);
            logger.info("Deleted associated interview for: " + internship.getCompanyName());
        }

        // Delete the internship
        internshipList.delete(index - 1);

        logger.log(Level.INFO, "Successfully deleted internship at index " + index);
        ui.print("✓ Deleted internship: " + internshipName);
    }
}
