package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MarkOfferCommandTest {

    private Parser parser;
    private InternshipList internshipList;
    private MarkOfferCommand markCommand;

    @BeforeEach
    public void setUp() throws GoldenCompassException {
        parser = new Parser();
        internshipList = new InternshipList();

        // We need to add at least one internship to the list so we have something to mark!
        // Remember: Constructor is Internship(title, companyName)
        internshipList.add(new Internship("Software Engineer", "Shopee"));

        markCommand = new MarkOfferCommand(parser, internshipList);
    }

    @Test
    public void execute_validIndex_marksOfferSuccessfully() throws GoldenCompassException {
        // Setup the parser with valid input
        parser.parse("mark 1");

        // Verify it starts as false
        assertFalse(internshipList.get(0).hasOffer());

        // Execute the command
        markCommand.execute();

        // Verify it successfully marked the offer as true
        assertTrue(internshipList.get(0).hasOffer());
    }

    @Test
    public void execute_invalidIndexOutOfBounds_throwsException() throws GoldenCompassException {
        // We only have 1 internship, so index 2 is out of bounds
        parser.parse("mark 2");

        assertThrows(GoldenCompassException.class, markCommand::execute);
    }

    @Test
    public void execute_invalidIndexNotANumber_throwsException() throws GoldenCompassException {
        // User types words instead of a number
        parser.parse("mark abc");

        assertThrows(GoldenCompassException.class, markCommand::execute);
    }

    @Test
    public void execute_missingIndex_throwsException() throws GoldenCompassException {
        // User forgets to type the number
        parser.parse("mark");

        assertThrows(GoldenCompassException.class, markCommand::execute);
    }

    @Test
    public void execute_alreadyOffered_throwsException() throws GoldenCompassException {
        // Mark it once successfully
        parser.parse("mark 1");
        markCommand.execute();

        // Try to mark the exact same internship again
        parser.parse("mark 1");
        assertThrows(GoldenCompassException.class, markCommand::execute);
    }

    @Test
    public void execute_alreadyRejected_throwsException() throws GoldenCompassException {
        // Directly alter the state of the internship to REJECTED for this test
        internshipList.get(0).markAsRejected();

        // Try to mark the rejected internship as an offer
        parser.parse("mark 1");
        assertThrows(GoldenCompassException.class, markCommand::execute);
    }
}
