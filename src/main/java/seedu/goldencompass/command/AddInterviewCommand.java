package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Adds an interview linked to an existing internship.
 * <p>
 * Command format: {@code add-interview INDEX /d DATE}
 * </p>
 */
public class AddInterviewCommand extends Command {

    public static final String COMMAND_WORD = "add-interview";
    private static final String FLAG_DATE = "/d";

    private final InternshipList internshipList;
    private final InterviewList interviewList;

    public AddInterviewCommand(Parser parser, InternshipList internshipList, InterviewList interviewList) {
        this.parser = parser;
        this.internshipList = internshipList;
        this.interviewList = interviewList;
    }

    @Override
    public void execute() throws GoldenCompassException {
        assert parser != null : "Parser should not be null";
        assert internshipList != null : "InternshipList should not be null";
        assert interviewList != null : "InterviewList should not be null";

        List<String> params = parser.getParamsOf(parser.getCommand());

        if (params == null || params.get(0).isBlank()) {
            throw new GoldenCompassException("Error: Please provide the index of the internship. "
                    + "Usage: add-interview INDEX /d DATE");
        }

        String indexParam = params.get(0).trim();
        assert !indexParam.isEmpty() : "Index parameter should not be empty after validation";

        List<String> dateParams = parser.getParamsOf(FLAG_DATE);
        if (dateParams == null || dateParams.get(0).isBlank()) {
            throw new GoldenCompassException("Error: Please provide a date using the /d flag. "
                    + "Usage: add-interview INDEX /d DATE");
        }
        String dateParam = dateParams.get(0).trim();
        assert !dateParam.isEmpty() : "Date parameter should not be empty after validation";

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

        assert index >= 1 && index <= internshipList.getSize() : "Index should be within valid range";

        LocalDateTime date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            date = LocalDateTime.parse(dateParam, formatter);
        } catch (DateTimeParseException e) {
            throw new GoldenCompassException(
                    "Error: Invalid date format, expected yyyy-MM-dd HH:mm, got: " + dateParam);
        }

        assert date != null : "Parsed date should not be null";

        Internship internship = internshipList.getInternships().get(index - 1);
        assert internship != null : "Retrieved internship should not be null";

        Interview interview = new Interview(internship, date);
        interviewList.add(interview);

        ui.print("Got it! I've added an interview on " + date + " for: " + internship);
    }
}
