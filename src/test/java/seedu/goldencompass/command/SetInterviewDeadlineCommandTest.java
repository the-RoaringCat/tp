package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.time.LocalDateTime;

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
        parser.parse("update-date 1 /d 2028-09-08 10:00");
        setDeadlineCommand.execute();
        assertEquals(LocalDateTime.parse("2028-09-08T10:00"), interviewList.get(0).getDate());
    }

    @Test
    public void execute_nonIntegerIndex_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date abc /d 2028-09-08 10:00");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index must be a valid integer, got: abc", e.getMessage());
        }
    }

    @Test
    public void execute_indexOutOfRangeHigh_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date 99 /d 2028-09-08 10:00");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index 99 is out of range. There are 1 interview(s). "
                            + "Use list-interview to see valid indexes.",
                    e.getMessage());
        }
    }

    @Test
    public void execute_indexOutOfRangeZero_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date 0 /d 2028-09-08 10:00");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index 0 is out of range. There are 1 interview(s). "
                            + "Use list-interview to see valid indexes.",
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
            assertEquals("Error: Invalid date format, expected yyyy-MM-dd HH:mm, got: 2028",
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
        parser.parse("update-date /d 2028-09-08 10:00");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Please provide the index of the interview. "
                    + "Usage: update-date INDEX /d DATE", e.getMessage());
        }
    }

    @Test
    public void execute_pastDate_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date 1 /d 2020-01-01 10:00");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Interview date 2020-01-01 10:00 is in the past. "
                    + "Please provide a future date.", e.getMessage());
        }
    }

    @Test
    public void execute_invalidCalendarDate_exceptionThrown() throws GoldenCompassException {
        parser.parse("update-date 1 /d 2099-02-30 10:00");
        try {
            setDeadlineCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: 2099-02-30 10:00 is not a valid date.", e.getMessage());
        }
    }

    /**
     * Regression test for PE-D issue #192. Mirrors the tester's scenario:
     * McDonalds and KFC interviews are added, McDonalds's date is pushed past KFC's,
     * then update-date 1 is issued again. The second update must target the interview
     * at index 1 of the (now re-sorted) list, not a stale local sort.
     */
    @Test
    public void execute_sequentialUpdatesAfterDateReorder_updatesCorrectInterview()
            throws GoldenCompassException {
        InterviewList list = new InterviewList();
        Internship mcd = new Internship("Cashier", "McDonalds");
        Internship kfc = new Internship("CEO", "KFC");
        list.add(new Interview(mcd, LocalDateTime.parse("2026-10-10T10:00")));
        list.add(new Interview(kfc, LocalDateTime.parse("2026-10-10T11:00")));

        // Step 1: push McDonalds's date later than KFC's.
        Parser p1 = new Parser();
        p1.parse("update-date 1 /d 2027-10-10 11:00");
        new SetInterviewDeadlineCommand(p1, list).execute();

        // Backing list must be re-sorted after mutation: KFC (earlier) before McDonalds (later).
        assertEquals("KFC", list.get(0).getInternship().getCompanyName());
        assertEquals("McDonalds", list.get(1).getInternship().getCompanyName());
        assertEquals(LocalDateTime.parse("2027-10-10T11:00"), list.get(1).getDate());

        // Step 2: update-date 1 now refers to KFC in the re-sorted list.
        Parser p2 = new Parser();
        p2.parse("update-date 1 /d 2028-10-10 11:00");
        new SetInterviewDeadlineCommand(p2, list).execute();

        // KFC should have been updated to 2028; McDonalds should still be 2027.
        // After re-sort: McDonalds (2027) before KFC (2028).
        assertEquals("McDonalds", list.get(0).getInternship().getCompanyName());
        assertEquals(LocalDateTime.parse("2027-10-10T11:00"), list.get(0).getDate());
        assertEquals("KFC", list.get(1).getInternship().getCompanyName());
        assertEquals(LocalDateTime.parse("2028-10-10T11:00"), list.get(1).getDate());
    }

    @Test
    public void execute_noInterviewsScheduled_suggestsAddInterview() throws GoldenCompassException {
        InterviewList emptyList = new InterviewList();
        Parser p = new Parser();
        p.parse("update-date 1 /d 2028-09-08 10:00");
        try {
            new SetInterviewDeadlineCommand(p, emptyList).execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: You have no interviews scheduled. "
                    + "Use add-interview to schedule one first.", e.getMessage());
        }
    }
}
