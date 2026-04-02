package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

/**
 * Represents a command to search for internships by company name or title.
 */
public class SearchInternshipCommand extends Command {

    private static final Logger logger = Logger.getLogger(SearchInternshipCommand.class.getName());
    private static final String FLAG_COMPANY = "/c";
    private static final String FLAG_TITLE = "/t";

    private final InternshipList internshipList;

    public SearchInternshipCommand(Parser parser, InternshipList internshipList) {
        super(parser);
        this.internshipList = internshipList;
    }

    @Override
    public void execute() throws GoldenCompassException {
        logger.info("Executing SearchInternshipCommand");

        // Get search parameters
        List<String> companyParams = parser.getParamsOf(FLAG_COMPANY);
        List<String> titleParams = parser.getParamsOf(FLAG_TITLE);

        String companySearch = (companyParams != null && !companyParams.isEmpty())
                ? companyParams.get(0).trim().toLowerCase() : null;
        String titleSearch = (titleParams != null && !titleParams.isEmpty())
                ? titleParams.get(0).trim().toLowerCase() : null;

        // Validate at least one search parameter
        if ((companySearch == null || companySearch.isEmpty())
                && (titleSearch == null || titleSearch.isEmpty())) {
            throw new GoldenCompassException(
                    "Please provide at least one search criteria!\n"
                            + "Usage: search [/c COMPANY] [/t TITLE]\n"
                            + "Example: search /c Google\n"
                            + "Example: search /t Engineer\n"
                            + "Example: search /c Google /t Software");
        }

        // Perform search
        List<Internship> results = internshipList.getInternships().stream()
                .filter(internship -> {
                    boolean matches = true;
                    if (companySearch != null && !companySearch.isEmpty()) {
                        matches = matches && internship.getCompanyName()
                                .toLowerCase().contains(companySearch);
                    }
                    if (titleSearch != null && !titleSearch.isEmpty()) {
                        matches = matches && internship.getTitle()
                                .toLowerCase().contains(titleSearch);
                    }
                    return matches;
                })
                .collect(Collectors.toList());

        // Display results
        if (results.isEmpty()) {
            ui.print("No internships found matching your search criteria.");
            logger.info("Search found 0 results");
            return;
        }

        ui.print("Found " + results.size() + " matching internship(s):");
        for (int i = 0; i < results.size(); i++) {
            Internship intern = results.get(i);
            ui.print((i + 1) + ". " + intern.getCompanyName() + " - " + intern.getTitle());
        }

        logger.info("Search found " + results.size() + " results");
    }
}
