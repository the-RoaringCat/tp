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

public class RejectOfferCommandTest {

    private Parser parser;
    private InternshipList internshipList;
    private RejectOfferCommand rejectCommand;

    @BeforeEach
    public void setUp() throws GoldenCompassException {
        parser = new Parser();
        internshipList = new InternshipList();

        // Add one internship to the list so we have something to reject
        internshipList.add(new Internship("Software Engineer", "Shopee"));

        rejectCommand = new RejectOfferCommand(parser, internshipList);
    }

    @Test
    public void execute_validIndex_marksRejectSuccessfully() throws GoldenCompassException {
        // Setup the parser with valid input
        parser.parse("reject 1");

        // Verify it starts as not rejected
        assertFalse(internshipList.get(0).isRejected());

        // Execute the command
        rejectCommand.execute();

        // Verify it successfully marked the rejection as true
        assertTrue(internshipList.get(0).isRejected());

        // Safety check: ensure it isn't also marked as an offer
        assertFalse(internshipList.get(0).hasOffer());
    }

    @Test
    public void execute_invalidIndexOutOfBounds_throwsException() throws GoldenCompassException {
        // We only have 1 internship, so index 2 is out of bounds
        parser.parse("reject 2");

        assertThrows(GoldenCompassException.class, rejectCommand::execute);
    }

    @Test
    public void execute_invalidIndexNotANumber_throwsException() throws GoldenCompassException {
        // User types words instead of a number
        parser.parse("reject abc");

        assertThrows(GoldenCompassException.class, rejectCommand::execute);
    }

    @Test
    public void execute_missingIndex_throwsException() throws GoldenCompassException {
        // User forgets to type the number
        parser.parse("reject");

        assertThrows(GoldenCompassException.class, rejectCommand::execute);
    }

    @Test
    public void execute_alreadyRejected_throwsException() throws GoldenCompassException {
        // Reject it once successfully
        parser.parse("reject 1");
        rejectCommand.execute();

        // Try to reject the exact same internship again
        parser.parse("reject 1");
        assertThrows(GoldenCompassException.class, rejectCommand::execute);
    }

    @Test
    public void execute_hasOffer_marksRejectSuccessfully() throws GoldenCompassException {
        // Manually set status to OFFER RECEIVED
        internshipList.get(0).markAsOffer();
        assertTrue(internshipList.get(0).hasOffer());

        // User decides to reject the offer
        parser.parse("reject 1");
        rejectCommand.execute();

        // Verify it is now REJECTED and no longer has the OFFER tag
        assertTrue(internshipList.get(0).isRejected());
        assertFalse(internshipList.get(0).hasOffer());
    }
}
