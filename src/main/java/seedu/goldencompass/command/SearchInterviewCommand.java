package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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

    private static final String FLAG_COMPANY = "/c";
    private static final String FLAG_TITLE = "/t";
    private static final String FLAG_DATE = "/d";
    private static final String COMMAND_DESCRIPTION =
            "Searches interviews by company, role, and/or date.\n"
            + "Format: search-interview [/c COMPANY] [/t ROLE] [/d DATE]";
    private static final String FLAG_DESCRIPTION =
            "Flags:\n"
            + "/c - search by company name (substring match).\n"
            + "/t - search by role/title (substring match).\n"
            + "/d - search by date (yyyy-MM-dd, exact match).";

    private final InterviewList interviewList;

    public SearchInterviewCommand(Parser parser, InterviewList interviewList) {
        super(parser);
        this.interviewList = interviewList;
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
        assert parser != null : "Parser should not be null";
        assert interviewList != null : "InterviewList should not be null";

        List<String> companyParams = parser.getParamsOf(FLAG_COMPANY);
        List<String> titleParams = parser.getParamsOf(FLAG_TITLE);
        List<String> dateParams = parser.getParamsOf(FLAG_DATE);

        String companyKeyword = getKeyword(companyParams);
        String titleKeyword = getKeyword(titleParams);
        String dateKeyword = getKeyword(dateParams);

        if (companyKeyword == null && titleKeyword == null && dateKeyword == null) {
            throw new GoldenCompassException("Error: Please provide at least one search flag. "
                    + "Usage: search-interview [/c COMPANY] [/t ROLE] [/d DATE]");
        }

        LocalDate date = parseDate(dateKeyword);
        List<Interview> results = interviewList.getInterviews().stream()
                .filter(i -> i.matches(companyKeyword, titleKeyword, date))
                .toList();

        if (results.isEmpty()) {
            ui.print("No interviews found matching the search criteria.");
            return;
        }

        ui.print("Found " + results.size() + " interview(s):");
        for (int i = 0; i < results.size(); i++) {
            ui.print((i + 1) + ". " + results.get(i).toString());
        }
    }

    private LocalDate parseDate(String dateKeyword) throws GoldenCompassException {
        if (dateKeyword == null) {
            return null;
        }
        try {
            return LocalDate.parse(dateKeyword);
        } catch (DateTimeParseException e) {
            throw new GoldenCompassException(
                    "Error: Invalid date format, expected yyyy-MM-dd, got: " + dateKeyword);
        }
    }

    private String getKeyword(List<String> params) {
        if (params == null || params.isEmpty() || params.get(0).isBlank()) {
            return null;
        }
        return params.get(0).trim();
    }
}
