package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.ui.Ui;

import java.util.List;
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
    /**
     * Executes the command to add a new internship to the tracker.
     * Extracts and validates the company name and the internship title from the parsed user input.
     * If all inputs are valid, a new {@code Internship} is created and appended to the {@code InternshipList}.
     * @throws GoldenCompassException If company name, flag, or title is missing or invalid.
     */
    @Override
    public void execute() throws GoldenCompassException {
        // LOGGING: Track when the command actually starts running
        logger.log(Level.INFO, "Starting execution of AddInternshipCommand...");

        List<String> addParams = parser.getParamsOf("add");
        List<String> titleParams = parser.getParamsOf("/t");

        // Create a "bucket" to collect all our error messages
        StringBuilder errorMessage = new StringBuilder();

        // 1. DEFENSIVE CHECK: Company Name
        if (addParams == null || addParams.isEmpty() || addParams.get(0).trim().isEmpty()) {
            logger.log(Level.WARNING, "Failed to add internship: Company name is missing.");
            errorMessage.append("Company name cannot be empty!\n");
        }

        // 2. DEFENSIVE CHECK: Missing or invalid flags
        if (titleParams == null) {
            logger.log(Level.WARNING, "Failed to add internship: Invalid flag or missing /t flag.");
            errorMessage.append("Invalid flag or missing title! Please use the '/t' flag for the role.\n");
        } else {
            // 3. DEFENSIVE CHECK: Empty title text (only check if the /t flag actually exists)
            String titleCheck = String.join(" ", titleParams).trim();
            if (titleCheck.isEmpty()) {
                logger.log(Level.WARNING, "Failed to add internship: Title text is missing after /t.");
                errorMessage.append("Internship title cannot be empty!\n");
            }
        }

        // 4. THE MOMENT OF TRUTH: If our error bucket has anything in it, throw everything at once!
        if (!errorMessage.isEmpty()) {
            throw new GoldenCompassException(errorMessage.toString().trim());
        }

        // --- If the code reaches here, the input is 100% flawless ---

        String companyName = addParams.get(0).trim();
        String title = String.join(" ", titleParams).trim();

        Internship newInternship = new Internship(title, companyName);
        internshipList.add(newInternship);

        // LOGGING: Secretly confirm the addition was successful in the background
        logger.log(Level.INFO, "Successfully added internship: [" + companyName + "] " + title);

        ui.print("Got it! I've added this internship to your compass:\n  " + newInternship);
    }

}
