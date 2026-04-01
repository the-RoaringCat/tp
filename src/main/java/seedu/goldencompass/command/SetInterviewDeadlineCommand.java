package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
/**
 * Sets the deadline date of an interview identified by its 1-based index in the interview list.
 * <p>
 * Command format: {@code update-date INDEX /d DATE}
 * </p>
 */
public class SetInterviewDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "update-date";

    private static final String FLAG_DATE = "/d";

    private final InterviewList interviewList;

    /**
     * Constructs a {@code SetInterviewDeadlineCommand} with the given {@code InterviewList}.
     * <p>
     *     Registers the command word with the global command set on construction.
     * </p>
     * @param interviewList the list of interviews to operate on.
     */
    public SetInterviewDeadlineCommand(Parser parser, InterviewList interviewList) {
        super(parser);
        this.interviewList = interviewList;
    }

    /**
     * Sets the deadline of the interview at the specified index.
     * <p>
     *     Expects a 1-based integer index as the default argument and {@code /d} with a date in
     *     ISO format (yyyy-MM-dd).
     * </p>
     * @throws GoldenCompassException if flags are invalid, the index is not a valid integer,
     *     the index is out of range, or the date format is invalid.
     */
    @Override
    public void execute() throws GoldenCompassException {
        assert parser != null : "Parser should not be null";
        assert interviewList != null : "InterviewList should not be null";

        List<String> indexParams = parser.getParamsOf(COMMAND_WORD);
        if (indexParams == null || indexParams.get(0).isBlank()) {
            throw new GoldenCompassException("Error: Please provide the index of the interview. "
                    + "Usage: update-date INDEX /d DATE");
        }
        String indexParam = indexParams.get(0).trim();
        assert !indexParam.isEmpty() : "Index parameter should not be empty after validation";

        List<String> dateParams = parser.getParamsOf(FLAG_DATE);
        if (dateParams == null || dateParams.get(0).isBlank()) {
            throw new GoldenCompassException("Error: Please provide a date using the /d flag. "
                    + "Usage: update-date INDEX /d DATE");
        }
        String dateParam = dateParams.get(0).trim();
        assert !dateParam.isEmpty() : "Date parameter should not be empty after validation";

        int index;
        try {
            index = Integer.parseInt(indexParam);
        } catch (NumberFormatException e) {
            throw new GoldenCompassException("Error: Index must be a valid integer, got: " + indexParam);
        }

        if (!interviewList.isValidIndex(index)) {
            throw new GoldenCompassException(
                    "Error: Index " + index + " is out of range. There are " + interviewList.size() + " interview(s).");
        }

        assert interviewList.isValidIndex(index) : "Index should be valid after validation";

        LocalDate date;
        try {
            date = LocalDate.parse(dateParam);
        } catch (DateTimeParseException e) {
            throw new GoldenCompassException("Error: Invalid date format, expected yyyy-MM-dd, got: " + dateParam);
        }

        assert date != null : "Parsed date should not be null";

        List<Interview> sortedInterviews = interviewList.getInterviews().stream()
                .sorted(Comparator.comparing(Interview::getDate))
                .toList();
        Interview interview = sortedInterviews.get(index - 1);
        assert interview != null : "Retrieved interview should not be null";

        interview.setDate(date);

        ui.print("Deadline set for interview " + index + ": " + date);
    }
}
