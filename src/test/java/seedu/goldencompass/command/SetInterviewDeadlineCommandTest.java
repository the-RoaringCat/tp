package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SetInterviewDeadlineCommandTest {

    private Parser parser;
    private InterviewList interviewList;
    private SetInterviewDeadlineCommand setDeadlineCommand;

    @BeforeEach
    public void setUp() throws GoldenCompassException {
        parser = new Parser();
        interviewList = new InterviewList();
        setDeadlineCommand = new SetInterviewDeadlineCommand(parser, interviewList);

        Internship internship = new Internship("Software Engineer", "Google");
        Interview interview = new Interview(internship);
        interviewList.add(interview);
    }

    @Test
    public void execute_validIndexAndDate_setsDeadlineSuccessfully() throws GoldenCompassException {
        parser.parse("update-date 1 /d 2028-09-08");
        setDeadlineCommand.execute();
        assertEquals(LocalDate.parse("2028-09-08"), interviewList.get(0).getDate());
    }

    @Test
    public void execute_nonIntegerIndex_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date abc /d 2028-09-08");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index must be a valid integer, got: abc", e.getMessage());
        }
    }

    @Test
    public void execute_indexOutOfRangeHigh_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date 99 /d 2028-09-08");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index 99 is out of range. There are 1 interview(s).",
                    e.getMessage());
        }
    }

    @Test
    public void execute_indexOutOfRangeZero_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date 0 /d 2028-09-08");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index 0 is out of range. There are 1 interview(s).",
                    e.getMessage());
        }
    }

    @Test
    public void execute_invalidDateFormat_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date 1 /d 2028");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Invalid date format, expected yyyy-MM-dd, got: 2028",
                    e.getMessage());
        }
    }

    @Test
    public void execute_missingDateFlag_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date 1");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Please provide a date using the /d flag. "
                    + "Usage: update-date INDEX /d DATE", e.getMessage());
        }
    }

    @Test
    public void execute_wrongFlag_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date 1 /by 2028-09-08");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Please provide a date using the /d flag. "
                    + "Usage: update-date INDEX /d DATE", e.getMessage());
        }
    }

    @Test
    public void execute_missingIndex_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date /d 2028-09-08");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Please provide the index of the interview. "
                    + "Usage: update-date INDEX /d DATE", e.getMessage());
        }
    }
}
