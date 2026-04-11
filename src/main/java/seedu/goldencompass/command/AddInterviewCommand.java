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
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds an interview linked to an existing internship.
 * <p>
 * Command format: {@code add-interview INDEX /d DATE}
 * </p>
 */
public class AddInterviewCommand extends Command {

    public static final String COMMAND_WORD = "add-interview";

    private static final Logger logger = Logger.getLogger(AddInterviewCommand.class.getName());
    private static final String FLAG_DATE = "/d";
    private static final String COMMAND_DESCRIPTION =
            "Adds an interview linked to an existing internship.\n"
            + "Format: add-interview INDEX /d DATE\n"
            + "Example: add-interview 2 /d 2025-06-15 10:00";
    private static final String FLAG_DESCRIPTION =
            "Flags:\n"
            + "/d - specifies the interview date and time (yyyy-MM-dd HH:mm).";

    private final InternshipList internshipList;
    private final InterviewList interviewList;

    /**
     * Constructs an {@code AddInterviewCommand}.
     *
     * @param parser         the parser containing the parsed user input.
     * @param internshipList the list of internships to look up by index.
     * @param interviewList  the list of interviews to add the new interview to.
     */
    public AddInterviewCommand(Parser parser, InternshipList internshipList, InterviewList interviewList) {
        assert parser != null : "Parser passed to AddInterviewCommand cannot be null";
        assert internshipList != null : "InternshipList passed to AddInterviewCommand cannot be null";
        assert interviewList != null : "InterviewList passed to AddInterviewCommand cannot be null";
        this.parser = parser;
        this.internshipList = internshipList;
        this.interviewList = interviewList;
    }

    /**
     * Executes the add-interview command.
     * Validates the index and date, checks for duplicate interviews,
     * then creates and registers the new {@code Interview}.
     *
     * @throws GoldenCompassException if validation fails or the internship already has an interview.
     */
    @Override
    public void execute() throws GoldenCompassException {
        logger.log(Level.INFO, "Starting execution of AddInterviewCommand...");

        assert parser != null : "Parser should not be null";
        assert internshipList != null : "InternshipList should not be null";
        assert interviewList != null : "InterviewList should not be null";

        List<String> params = parser.getParamsOf(parser.getCommand());

        if (params == null || params.get(0).isBlank()) {
            logger.log(Level.WARNING, "Failed to add interview: index is missing.");
            throw new GoldenCompassException("Error: Please provide the index of the internship. "
                    + "Usage: add-interview INDEX /d DATE");
        }

        String indexParam = params.get(0).trim();
        assert !indexParam.isEmpty() : "Index parameter should not be empty after validation";

        List<String> dateParams = parser.getParamsOf(FLAG_DATE);
        if (dateParams == null || dateParams.get(0).isBlank()) {
            logger.log(Level.WARNING, "Failed to add interview: date flag is missing.");
            throw new GoldenCompassException("Error: Please provide a date using the /d flag. "
                    + "Usage: add-interview INDEX /d DATE");
        }
        String dateParam = dateParams.get(0).trim();
        assert !dateParam.isEmpty() : "Date parameter should not be empty after validation";

        int index;
        try {
            index = Integer.parseInt(indexParam);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Failed to add interview: index is not a number.");
            throw new GoldenCompassException("Error: Index must be a valid integer, got: " + indexParam);
        }

        if (index < 1 || index > internshipList.getSize()) {
            logger.log(Level.WARNING, "Failed to add interview: index out of bounds.");
            throw new GoldenCompassException(
                    "Error: Index " + index + " is out of range. There are "
                            + internshipList.getSize() + " internship(s).");
        }

        assert index >= 1 && index <= internshipList.getSize() : "Index should be within valid range";

        if (!dateParam.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
            logger.log(Level.WARNING, "Failed to add interview: invalid date format.");
            throw new GoldenCompassException(
                    "Error: Invalid date format, expected yyyy-MM-dd HH:mm, got: " + dateParam);
        }

        LocalDateTime date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);
            date = LocalDateTime.parse(dateParam, formatter);
        } catch (DateTimeParseException e) {
            logger.log(Level.WARNING, "Failed to add interview: date is not a valid calendar date.");
            throw new GoldenCompassException(
                    "Error: " + dateParam + " is not a valid date.");
        }

        assert date != null : "Parsed date should not be null";

        if (date.isBefore(LocalDateTime.now())) {
            logger.log(Level.WARNING, "Failed to add interview: date is in the past.");
            throw new GoldenCompassException(
                    "Error: Interview date " + dateParam + " is in the past. "
                            + "Please provide a future date.");
        }

        Internship internship = internshipList.getInternships().get(index - 1);
        assert internship != null : "Retrieved internship should not be null";

        if (internship.getInterview() != null) {
            logger.log(Level.WARNING, "Failed to add interview: internship already has an interview.");
            throw new GoldenCompassException(
                    "Error: " + internship.getCompanyName() + " - " + internship.getTitle()
                            + " already has an interview scheduled. "
                            + "Use update-date to change the date, or delete-interview first.");
        }

        Interview interview = new Interview(internship, date);
        internship.setInterview(interview);
        interviewList.add(interview);

        logger.log(Level.INFO, "Successfully added interview for: " + internship);
        ui.print("Got it! I've added an interview on " + date + " for: " + internship);
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
