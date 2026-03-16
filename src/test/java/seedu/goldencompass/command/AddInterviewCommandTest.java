package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddInterviewCommandTest {

    private Parser parser;
    private InternshipList internshipList;
    private InterviewList interviewList;
    private AddInterviewCommand addInterviewCommand;

    @BeforeEach
    public void setUp() throws GoldenCompassException {
        parser = new Parser();
        internshipList = new InternshipList();
        interviewList = new InterviewList();
        addInterviewCommand = new AddInterviewCommand(parser, internshipList, interviewList);

        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Data Analyst", "Meta"));
    }

    @Test
    public void execute_validIndex_addsInterviewSuccessfully() throws GoldenCompassException {
        parser.parse("add-interview 1");
        addInterviewCommand.execute();
        assertEquals(1, interviewList.size());
    }

    @Test
    public void execute_validIndexSecondInternship_addsInterviewSuccessfully() throws GoldenCompassException {
        parser.parse("add-interview 2");
        addInterviewCommand.execute();
        assertEquals(1, interviewList.size());
    }

    @Test
    public void execute_nonIntegerIndex_exceptionThrown() throws GoldenCompassException {
        parser.parse("add-interview abc");
        try {
            addInterviewCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index must be a valid integer, got: abc", e.getMessage());
        }
    }

    @Test
    public void execute_indexOutOfRangeHigh_exceptionThrown() throws GoldenCompassException {
        parser.parse("add-interview 99");
        try {
            addInterviewCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index 99 is out of range. There are 2 internship(s).",
                    e.getMessage());
        }
    }

    @Test
    public void execute_indexOutOfRangeZero_exceptionThrown() throws GoldenCompassException {
        parser.parse("add-interview 0");
        try {
            addInterviewCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index 0 is out of range. There are 2 internship(s).",
                    e.getMessage());
        }
    }

    @Test
    public void execute_negativeIndex_exceptionThrown() throws GoldenCompassException {
        parser.parse("add-interview -1");
        try {
            addInterviewCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index -1 is out of range. There are 2 internship(s).",
                    e.getMessage());
        }
    }

    @Test
    public void execute_missingIndex_exceptionThrown() throws GoldenCompassException {
        parser.parse("add-interview");
        try {
            addInterviewCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Please provide the index of the internship.", e.getMessage());
        }
    }
}
