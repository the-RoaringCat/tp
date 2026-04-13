package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class DeleteInterviewCommand extends Command {

    private static final Logger logger = Logger.getLogger(DeleteInterviewCommand.class.getName());
    private final InternshipList internshipList;
    private final InterviewList interviewList;

    public DeleteInterviewCommand(Parser parser, InternshipList internshipList, InterviewList interviewList) {
        super(parser);
        assert parser != null : "Parser cannot be null";
        assert internshipList != null : "InternshipList cannot be null";
        assert interviewList != null : "InterviewList cannot be null";
        this.internshipList = internshipList;
        this.interviewList = interviewList;
    }

    @Override
    public void execute() throws GoldenCompassException {

        List<String> params = parser.getParamsOf(parser.getCommand());

        // Check if index is provided
        if (params == null || params.isEmpty() || params.get(0).trim().isEmpty()) {
            throw new GoldenCompassException("Please provide the index of the interview to delete!");
        }

        int interviewIndex;
        try {
            interviewIndex = Integer.parseInt(params.get(0).trim());
        } catch (NumberFormatException e) {
            throw new GoldenCompassException("The index must be a number!");
        }

        // Check if there are any interviews
        if (interviewList.size() == 0) {
            throw new GoldenCompassException("There are no interviews to delete.");
        }

        // Validate interview index range (1-based to 0-based conversion)
        if (interviewIndex < 1 || interviewIndex > interviewList.size()) {
            throw new GoldenCompassException("Invalid index! Please enter a number between 1 and "
                    + interviewList.size());
        }

        // Get the interview at the specified index from InterviewList (0-based)
        Interview interviewToDelete = interviewList.get(interviewIndex - 1);

        // Get the associated internship
        Internship internship = interviewToDelete.getInternship();
        if (internship == null) {
            // Still delete the corrupted interview
            interviewList.getInterviews().remove(interviewToDelete);
            throw new GoldenCompassException("Associated internship not found. Corrupted interview has been removed.");
        }

        // Format the date for display
        String formattedDate = interviewToDelete.getDate() != null
                ? interviewToDelete.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "No date set";

        // Remove from InterviewList
        interviewList.getInterviews().remove(interviewToDelete);

        // Clear the interview reference from the internship
        internship.deleteInterview();

        // Print confirmation message
        ui.print("Deleted interview for " + internship.getCompanyName()
                + " (scheduled on: " + formattedDate + ")");

        logger.info("Deleted interview at index " + interviewIndex + " for internship: " + internship.getCompanyName());
    }

    @Override
    protected String getCommandDescription() {
        return "Deletes an interview from the interview list.";
    }

    @Override
    protected String getFlagDescription() {
        return "Format: delete-interview INDEX\n"
                + "Example: delete-interview 1\n\n"
                + "Note: INDEX refers to the position shown in 'list-interview' command.\n"
                + "Only the interview is deleted. The internship remains in your list.";
    }
}
