package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

public class SearchInternshipCommand extends Command {

    private static final Logger logger = Logger.getLogger(SearchInternshipCommand.class.getName());
    private static final String FLAG_COMPANY = "/c";
    private static final String FLAG_TITLE = "/t";

    private final InternshipList internshipList;

    public SearchInternshipCommand(Parser parser, InternshipList internshipList) {
        super(parser);
        assert parser != null : "Parser cannot be null";
        assert internshipList != null : "InternshipList cannot be null";
        this.internshipList = internshipList;
    }

    @Override
    public void execute() throws GoldenCompassException {

        logger.info("Executing SearchInternshipCommand");

        List<String> companyParams = parser.getParamsOf(FLAG_COMPANY);
        List<String> titleParams = parser.getParamsOf(FLAG_TITLE);

        String companySearch = (companyParams != null && !companyParams.isEmpty())
                ? companyParams.get(0).trim() : null;
        String titleSearch = (titleParams != null && !titleParams.isEmpty())
                ? titleParams.get(0).trim() : null;

        if ((companySearch == null || companySearch.isEmpty())
                && (titleSearch == null || titleSearch.isEmpty())) {
            throw new GoldenCompassException(
                    "Please provide at least one search criteria!\n"
                            + "Usage: search [/c COMPANY] [/t TITLE]\n"
                            + "Example: search /c Google\n"
                            + "Example: search /t Engineer");
        }

        // Use the matches() method
        List<Internship> results = internshipList.getInternships().stream()
                .filter(internship -> internship.matches(companySearch, titleSearch))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            ui.print("No internships found matching your search criteria.");
            logger.info("Search found 0 results");
            return;
        }

        ui.print("Found " + results.size() + " matching internship(s):");
        for (int i = 0; i < results.size(); i++) {
            ui.print((i + 1) + ". " + results.get(i).toString());
        }

        logger.info("Search found " + results.size() + " results");
    }

    @Override
    protected String getCommandDescription() {
        return "Searches for internships by company name and/or title.";
    }

    @Override
    protected String getFlagDescription() {
        return "Format: search [/c COMPANY] [/t TITLE]\n\n"
                + "Flags:\n"
                + "  /c - search by company name (case-insensitive, partial match)\n"
                + "  /t - search by job title (case-insensitive, partial match)\n\n"
                + "Examples:\n"
                + "  search /c Google\n"
                + "  search /t Engineer\n"
                + "  search /c Google /t Software\n\n"
                + "Note: At least one search criterion must be provided.";
    }
}
