package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sets the deadline date of an interview identified by its 1-based index in the interview list.
 * <p>
 * Command format: {@code update-date INDEX /d DATE}
 * </p>
 */
public class SetInterviewDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "update-date";

    private static final Logger logger = Logger.getLogger(SetInterviewDeadlineCommand.class.getName());

    private static final String FLAG_DATE = "/d";
    private static final String COMMAND_DESCRIPTION =
            "Sets the deadline date and time of an interview.\n"
            + "Format: update-date INDEX /d DATE\n"
            + "Example: update-date 1 /d 2025-04-15 14:00";
    private static final String FLAG_DESCRIPTION =
            "Flags:\n"
            + "/d - specifies the date and time (yyyy-MM-dd HH:mm).";

    private final InterviewList interviewList;

    /**
     * Constructs a {@code SetInterviewDeadlineCommand} with the given {@code InterviewList}.
     *
     * @param parser        the parser containing the parsed user input.
     * @param interviewList the list of interviews to operate on.
     */
    public SetInterviewDeadlineCommand(Parser parser, InterviewList interviewList) {
        super(parser);
        assert parser != null : "Parser passed to SetInterviewDeadlineCommand cannot be null";
        assert interviewList != null : "InterviewList passed to SetInterviewDeadlineCommand cannot be null";
        this.interviewList = interviewList;
    }

    /**
     * Sets the deadline of the interview at the specified index.
     * <p>
     *     Expects a 1-based integer index as the default argument and {@code /d} with a date in
     *     format {@code yyyy-MM-dd HH:mm}.
     * </p>
     *
     * @throws GoldenCompassException if the index is missing, not a valid integer,
     *     out of range, or the date format is invalid.
     */
    @Override
    public void execute() throws GoldenCompassException {
        logger.log(Level.INFO, "Starting execution of SetInterviewDeadlineCommand...");

        assert parser != null : "Parser should not be null";
        assert interviewList != null : "InterviewList should not be null";

        List<String> indexParams = parser.getParamsOf(parser.getCommand());
        if (indexParams == null || indexParams.get(0).isBlank()) {
            logger.log(Level.WARNING, "Failed to update date: index is missing.");
            throw new GoldenCompassException("Error: Please provide the index of the interview. "
                    + "Usage: update-date INDEX /d DATE");
        }
        String indexParam = indexParams.get(0).trim();
        assert !indexParam.isEmpty() : "Index parameter should not be empty after validation";

        List<String> dateParams = parser.getParamsOf(FLAG_DATE);
        if (dateParams == null || dateParams.get(0).isBlank()) {
            logger.log(Level.WARNING, "Failed to update date: date flag is missing.");
            throw new GoldenCompassException("Error: Please provide a date using the /d flag. "
                    + "Usage: update-date INDEX /d DATE");
        }
        String dateParam = dateParams.get(0).trim();
        assert !dateParam.isEmpty() : "Date parameter should not be empty after validation";

        int index;
        try {
            index = Integer.parseInt(indexParam);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Failed to update date: index is not a number.");
            throw new GoldenCompassException("Error: Index must be a valid integer, got: " + indexParam);
        }

        if (!interviewList.isValidIndex(index)) {
            logger.log(Level.WARNING, "Failed to update date: index out of bounds.");
            throw new GoldenCompassException(
                    "Error: Index " + index + " is out of range. There are "
                            + interviewList.size() + " interview(s).");
        }

        assert interviewList.isValidIndex(index) : "Index should be valid after validation";

        if (!dateParam.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
            logger.log(Level.WARNING, "Failed to update date: invalid date format.");
            throw new GoldenCompassException(
                    "Error: Invalid date format, expected yyyy-MM-dd HH:mm, got: " + dateParam);
        }

        LocalDateTime date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);
            date = LocalDateTime.parse(dateParam, formatter);
        } catch (DateTimeParseException e) {
            logger.log(Level.WARNING, "Failed to update date: date is not a valid calendar date.");
            throw new GoldenCompassException(
                    "Error: " + dateParam + " is not a valid date.");
        }

        assert date != null : "Parsed date should not be null";

        if (date.isBefore(LocalDateTime.now())) {
            logger.log(Level.WARNING, "Failed to update date: date is in the past.");
            throw new GoldenCompassException(
                    "Error: Interview date " + dateParam + " is in the past. "
                            + "Please provide a future date.");
        }

        interviewList.setDateFor(index - 1, date);

        logger.log(Level.INFO, "Successfully updated interview " + index + " to " + date);
        ui.print("Deadline set for interview " + index + ": " + date);
    }

    @Override
    protected String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    protected String getFlagDescription() {
        return FLAG_DESCRIPTION;
    }
}
