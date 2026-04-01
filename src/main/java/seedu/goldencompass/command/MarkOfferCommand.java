package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Represents a command to mark an existing internship as "Offer Received".
 */
public class MarkOfferCommand extends Command {
    private static final Logger logger = Logger.getLogger(MarkOfferCommand.class.getName());
    private final InternshipList internshipList;

    public MarkOfferCommand(Parser parser, InternshipList internshipList) {
        // Pass the parser up to CommandClass to initialize 'ui' and 'parser' for us
        super(parser);

        assert parser != null : "Parser passed to MarkOfferCommand cannot be null";
        assert internshipList != null : "InternshipList passed to MarkOfferCommand cannot be null";
        this.internshipList = internshipList;
    }

    /**
     * Executes the command to mark an internship as offered.
     * Extracts the index from the parsed user input and updates the corresponding Internship.
     * @throws GoldenCompassException If the index is missing, not a number, or out of bounds.
     */
    @Override
    public void execute() throws GoldenCompassException {
        logger.log(Level.INFO, "Starting execution of MarkOfferCommand...");

        // Note: Change "mark-offer" if your team uses a different command word (e.g., "mark")
        List<String> params = parser.getParamsOf("mark");

        // 1. DEFENSIVE CHECK: Missing index
        if (params == null || params.isEmpty() || params.get(0).trim().isEmpty()) {
            logger.log(Level.WARNING, "Failed to mark offer: Index is missing.");
            throw new GoldenCompassException("Please provide the index of the internship! (e.g., mark-offer 1)");
        }

        int index;
        // 2. DEFENSIVE CHECK: Not a number
        try {
            index = Integer.parseInt(params.get(0).trim());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Failed to mark offer: Index is not a number.");
            throw new GoldenCompassException("The index must be a number! (e.g., mark 1)");
        }

        // 3. DEFENSIVE CHECK: Out of bounds
        // (If your list uses .size() instead of .getSize(), change it here!)
        if (index < 1 || index > internshipList.getSize()) {
            logger.log(Level.WARNING, "Failed to mark offer: Index out of bounds.");
            throw new GoldenCompassException("Invalid index! Please check your internship list.");
        }

        // --- If the code reaches here, the input is flawless ---

        // Get the internship (0-indexed)
        Internship internship = internshipList.get(index - 1);

        // Mark it as offered!
        internship.markAsOffer();

        logger.log(Level.INFO, "Successfully marked internship as offer received.");
        ui.print("HUGE CONGRATS! 🥳 Marked this internship as [OFFER RECEIVED]:\n  " + internship);
    }
}
