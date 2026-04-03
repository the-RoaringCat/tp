package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Searches the interview list by company name, role, and/or date.
 * <p>
 * Command format: {@code search-interview [/c COMPANY] [/t ROLE] [/d DATE]}
 * At least one flag must be provided. Multiple flags narrow the results (AND logic).
 * Text matching is case-insensitive substring matching.
 * </p>
 */
public class SearchInterviewCommand extends Command {

    public static final String COMMAND_WORD = "search-interview";

    private static final Logger logger = Logger.getLogger(SearchInterviewCommand.class.getName());

    private static final String FLAG_COMPANY = "/c";
    private static final String FLAG_TITLE = "/t";
    private static final String FLAG_DATE = "/d";
    private static final String COMMAND_DESCRIPTION =
            "Searches interviews by company, role, and/or date.\n"
            + "Format: search-interview [/c COMPANY] [/t ROLE] [/d DATE]\n"
            + "Example: search-interview /c Google /d 2025-06-15";
    private static final String FLAG_DESCRIPTION =
            "Flags:\n"
            + "/c - search by company name (case-insensitive substring match).\n"
            + "/t - search by role/title (case-insensitive substring match).\n"
            + "/d - search by date (yyyy-MM-dd, exact match).";

    private final InterviewList interviewList;

    /**
     * Constructs a {@code SearchInterviewCommand}.
     *
     * @param parser        the parser containing the parsed user input.
     * @param interviewList the list of interviews to search through.
     */
    public SearchInterviewCommand(Parser parser, InterviewList interviewList) {
        super(parser);
        assert parser != null : "Parser passed to SearchInterviewCommand cannot be null";
        assert interviewList != null : "InterviewList passed to SearchInterviewCommand cannot be null";
        this.interviewList = interviewList;
    }

    /**
     * Executes the search-interview command.
     * Retrieves search criteria from flags, filters the interview list,
     * and prints matching results.
     *
     * @throws GoldenCompassException if no flags are provided or the date format is invalid.
     */
    @Override
    public void execute() throws GoldenCompassException {
        logger.log(Level.INFO, "Starting execution of SearchInterviewCommand...");

        assert parser != null : "Parser should not be null";
        assert interviewList != null : "InterviewList should not be null";

        List<String> companyParams = parser.getParamsOf(FLAG_COMPANY);
        List<String> titleParams = parser.getParamsOf(FLAG_TITLE);
        List<String> dateParams = parser.getParamsOf(FLAG_DATE);

        String companyKeyword = getKeyword(companyParams);
        String titleKeyword = getKeyword(titleParams);
        String dateKeyword = getKeyword(dateParams);

        if (companyKeyword == null && titleKeyword == null && dateKeyword == null) {
            logger.log(Level.WARNING, "Failed to search: no search flags provided.");
            throw new GoldenCompassException("Error: Please provide at least one search flag. "
                    + "Usage: search-interview [/c COMPANY] [/t ROLE] [/d DATE]");
        }

        LocalDate date = parseDate(dateKeyword);

        logger.log(Level.INFO, "Searching interviews with company=" + companyKeyword
                + ", title=" + titleKeyword + ", date=" + date);

        List<Interview> results = interviewList.getInterviews().stream()
                .filter(i -> i.matches(companyKeyword, titleKeyword, date))
                .toList();

        if (results.isEmpty()) {
            logger.log(Level.INFO, "Search found 0 results.");
            ui.print("No interviews found matching the search criteria.");
            return;
        }

        logger.log(Level.INFO, "Search found " + results.size() + " result(s).");
        ui.print("Found " + results.size() + " interview(s):");
        for (int i = 0; i < results.size(); i++) {
            ui.print((i + 1) + ". " + results.get(i).toString());
        }
    }

    /**
     * Parses a date keyword string into a {@code LocalDate}.
     *
     * @param dateKeyword the date string in yyyy-MM-dd format, or null to skip.
     * @return the parsed {@code LocalDate}, or null if dateKeyword is null.
     * @throws GoldenCompassException if the date format is invalid.
     */
    private LocalDate parseDate(String dateKeyword) throws GoldenCompassException {
        if (dateKeyword == null) {
            return null;
        }
        try {
            return LocalDate.parse(dateKeyword);
        } catch (DateTimeParseException e) {
            logger.log(Level.WARNING, "Failed to search: invalid date format.");
            throw new GoldenCompassException(
                    "Error: Invalid date format, expected yyyy-MM-dd, got: " + dateKeyword);
        }
    }

    /**
     * Extracts a keyword from a parameter list.
     *
     * @param params the list of parameters for a flag, or null if the flag was not provided.
     * @return the trimmed keyword string, or null if not provided or blank.
     */
    private String getKeyword(List<String> params) {
        if (params == null || params.isEmpty() || params.get(0).isBlank()) {
            return null;
        }
        return params.get(0).trim();
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
