package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.ui.Ui;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Represents a command to add a new internship to the GoldenCompass tracker.
 */
public class AddInternshipCommand implements Command {
    // LOGGING: Create the logger for this specific class
    private static final Logger logger = Logger.getLogger(AddInternshipCommand.class.getName());
    private final Ui ui;
    private final Parser parser;
    private final InternshipList internshipList;

    public AddInternshipCommand(Parser parser, InternshipList internshipList) {
        // ASSERTIONS: Fail fast if another part of the program accidentally passes null data
        assert parser != null : "Parser passed to AddInternshipCommand cannot be null";
        assert internshipList != null : "InternshipList passed to AddInternshipCommand cannot be null";
        ui = new Ui();
        this.parser = parser;
        this.internshipList = internshipList;
    }

    @Override
    public void execute() throws GoldenCompassException {
        // LOGGING: Track when the command actually starts running
        logger.log(Level.INFO, "Starting execution of AddInternshipCommand...");

        List<String> addParams = parser.getParamsOf("add");

        // DEFENSIVE PROGRAMMING: Prevent code crashes by checking for null or empty lists BEFORE getting index 0
        if (addParams == null || addParams.isEmpty() || addParams.get(0).trim().isEmpty()) {
            logger.log(Level.WARNING, "Failed to add internship: Company name is missing.");
            throw new GoldenCompassException("Company name cannot be empty!");
        }

        String companyName = parser.getParamsOf("add").get(0);
        String title = "";

        // Defensive check: Ensure the list exists AND is not empty before joining it
        if (parser.getParamsOf("/t") != null && !parser.getParamsOf("/t").isEmpty()) {
            title = String.join(" ", parser.getParamsOf("/t")).trim();
        }

        // EXCEPTION & LOGGING: Reject empty titles
        if (title.isEmpty()) {
            logger.log(Level.WARNING, "Failed to add internship: Title is missing.");
            throw new GoldenCompassException("Internship title cannot be empty!");
        }

        Internship newInternship = new Internship(title, companyName);
        internshipList.add(newInternship);

        // LOGGING: Secretly confirm the addition was successful in the background
        logger.log(Level.INFO, "Successfully added internship: [" + companyName + "] " + title);

        ui.print("Got it! I've added this internship to your compass:\n  " + newInternship);
    }

}
