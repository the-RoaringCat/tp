package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.util.List;

/**
 * Adds an interview linked to an existing internship.
 * <p>
 * Command format: {@code add-interview INDEX}
 * </p>
 */
public class AddInterviewCommand extends CommandClass {

    public static final String COMMAND_WORD = "add-interview";

    private final InternshipList internshipList;
    private final InterviewList interviewList;

    public AddInterviewCommand(Parser parser, InternshipList internshipList, InterviewList interviewList) {
        this.parser = parser;
        this.internshipList = internshipList;
        this.interviewList = interviewList;
    }

    @Override
    public void execute() throws GoldenCompassException {
        List<String> params = parser.getParamsOf(COMMAND_WORD);

        if (params == null || params.get(0).trim().isEmpty()) {
            throw new GoldenCompassException("Error: Please provide the index of the internship.");
        }

        String indexParam = params.get(0).trim();

        int index;
        try {
            index = Integer.parseInt(indexParam);
        } catch (NumberFormatException e) {
            throw new GoldenCompassException("Error: Index must be a valid integer, got: " + indexParam);
        }

        if (index < 1 || index > internshipList.getSize()) {
            throw new GoldenCompassException(
                    "Error: Index " + index + " is out of range. There are "
                            + internshipList.getSize() + " internship(s).");
        }

        Internship internship = internshipList.getInternships().get(index - 1);
        Interview interview = new Interview(internship);
        interviewList.add(interview);

        ui.print("Got it! I've added an interview for: " + internship);
    }
}
