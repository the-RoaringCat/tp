package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddInternshipCommandTest {

    private Parser parser;
    private InternshipList internshipList;
    private AddInternshipCommand addInternshipCommand;

    @BeforeEach
    public void setUp() throws GoldenCompassException {
        parser = new Parser();
        internshipList = new InternshipList();
        addInternshipCommand = new AddInternshipCommand(parser, internshipList);
    }

    @Test
    public void execute_validInput_addsInternshipSuccessfully() throws GoldenCompassException {

        parser.parse("add Shopee /t Software Engineer");

        addInternshipCommand.execute();

        assertEquals(1, internshipList.getSize());
    }

    @Test
    public void execute_emptyCompanyName_throwsException() throws GoldenCompassException {

        parser.parse("add     /t Software Engineer");

        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);

    }

    @Test
    public void execute_emptyTitle_throwsException() throws GoldenCompassException {

        parser.parse("add Shopee /t    ");

        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);

    }

    // Wrong flag used
    @Test
    public void execute_invalidFlag_throwsException() throws GoldenCompassException {
        parser.parse("add Shopee /c Software Engineer");
        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);
    }

    // Missing flag
    @Test
    public void execute_missingTitleFlag_throwsException() throws GoldenCompassException {
        parser.parse("add Shopee");
        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);
    }

    // Company name completely missing before the flag
    @Test
    public void execute_missingCompanyName_throwsException() throws GoldenCompassException {
        parser.parse("add /t Software Engineer");
        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);
    }

    // Just typing "add" with absolutely nothing else
    @Test
    public void execute_bareAddCommand_throwsException() throws GoldenCompassException {
        parser.parse("add");
        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);
    }

    @Test
    public void execute_duplicateInternship_throwsException() throws GoldenCompassException {
        // Add it the first time (should succeed)
        parser.parse("add Shopee /t Software Engineer");
        addInternshipCommand.execute();

        // Try to add the exact same one again (should fail)
        parser.parse("add Shopee /t Software Engineer");
        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);
    }

    @Test
    public void execute_companyNameTooShort_throwsException() throws GoldenCompassException {
        parser.parse("add S /t Software Engineer");
        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);
    }

    @Test
    public void execute_companyNameTooLong_throwsException() throws GoldenCompassException {
        String longName = "A".repeat(41);
        parser.parse("add " + longName + " /t Software Engineer");
        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);
    }

    @Test
    public void execute_invalidSpecialCharacters_throwsException() throws GoldenCompassException {
        // The pipe character "|" should trigger the exception
        parser.parse("add Meta | Google /t SWE");
        assertThrows(GoldenCompassException.class, addInternshipCommand::execute);
    }
}
