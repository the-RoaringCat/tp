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

        if (params == null || params.isEmpty() || params.get(0).trim().isEmpty()) {
            throw new GoldenCompassException("Please provide the index of the internship to delete interview from!");
        }

        int internshipIndex;
        try {
            internshipIndex = Integer.parseInt(params.get(0).trim());
        } catch (NumberFormatException e) {
            throw new GoldenCompassException("The index must be a number!");
        }

        if (internshipIndex < 1 || internshipIndex > internshipList.getSize()) {
            throw new GoldenCompassException("Invalid index! Please enter a number between 1 and "
                    + internshipList.getSize());
        }

        Internship internship = internshipList.get(internshipIndex - 1);

        // Get interviews list with null check
        List<Interview> interviews = interviewList.getInterviews();
        if (interviews == null) {
            logger.severe("getInterviews() returned null");
            throw new GoldenCompassException("System error: Unable to retrieve interview list");
        }

        // Find interview by searching InterviewList
        Interview interviewToDelete = null;
        for (Interview i : interviewList.getInterviews()) {
            if (i.getInternship() != null && i.getInternship().getCompanyName().equals(internship.getCompanyName())) {
                interviewToDelete = i;
                break;
            }
        }

        if (interviewToDelete == null) {
            throw new GoldenCompassException("This internship does not have an interview scheduled.");
        }

        String formattedDate = interviewToDelete.getDate() != null
                ? interviewToDelete.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "No date set";

        // ONLY remove from InterviewList - DO NOT delete the internship!
        interviewList.getInterviews().remove(interviewToDelete);

        // Also clear the interview reference from the internship
        internship.deleteInterview();

        // Make sure the message says "Deleted interview", not "Deleted internship"
        ui.print("✓ Deleted interview for " + internship.getCompanyName()
                + " (scheduled on: " + formattedDate + ")");
    }

    @Override
    protected String getCommandDescription() {
        return "Deletes an interview from an internship application.";
    }

    @Override
    protected String getFlagDescription() {
        return "Format: delete-interview INDEX\n"
                + "Example: delete-interview 1\n\n"
                + "Note: Only the interview is deleted. The internship remains in your list.";
    }
}
