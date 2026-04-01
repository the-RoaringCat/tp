package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpcomingCommandTest {

    private Parser parser;
    private InterviewList interviewList;
    private UpcomingCommand upcomingCommand;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private LocalDate now;

    @BeforeEach
    public void setUp() throws GoldenCompassException {
        parser = new Parser();
        interviewList = new InterviewList();
        upcomingCommand = new UpcomingCommand(parser, interviewList);
        now = LocalDate.now(ZoneId.systemDefault());
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void execute_upcomingWithoutParameter_printsUpcomingInterviewsCorrectly() throws GoldenCompassException {
        interviewList.add(new Interview(new Internship("Software Engineer", "Google"), now));
        interviewList.add(new Interview(new Internship("Frontend Developer", "Meta"), now.plusDays(3)));
        interviewList.add(new Interview(new Internship("Backend Developer", "Amazon"), now.plusDays(6)));
        interviewList.add(new Interview(new Internship("Bus Driver", "NUS"), now.plusDays(20)));

        parser.parse("upcoming");
        upcomingCommand.execute();
        String output = outputStream.toString().trim();

        assertTrue(output.contains("Google - Software Engineer @ " + now));
        assertTrue(output.contains("Meta - Frontend Developer @ " + now.plusDays(3)));
        assertFalse(output.contains("Amazon - Backend Developer @ "));
        assertFalse(output.contains("NUS - Bus Driver @ "));
    }

    @Test
    public void execute_upcomingWithIntegerParameter_printsUpcomingInterviewsCorrectly() throws GoldenCompassException {
        interviewList.add(new Interview(new Internship("Software Engineer", "Google"), now));
        interviewList.add(new Interview(new Internship("Frontend Developer", "Meta"), now.plusDays(3)));
        interviewList.add(new Interview(new Internship("Backend Developer", "Amazon"), now.plusDays(6)));
        interviewList.add(new Interview(new Internship("Bus Driver", "NUS"), now.plusDays(20)));

        parser.parse("upcoming 7");
        upcomingCommand.execute();
        String output = outputStream.toString().trim();

        assertTrue(output.contains("Google - Software Engineer @ " + now));
        assertTrue(output.contains("Meta - Frontend Developer @ " + now.plusDays(3)));
        assertTrue(output.contains("Amazon - Backend Developer @ " + now.plusDays(6)));
        assertFalse(output.contains("NUS - Bus Driver @ "));

        parser.parse("upcoming 21");
        upcomingCommand.execute();
        output = outputStream.toString().trim();

        assertTrue(output.contains("Google - Software Engineer @ " + now));
        assertTrue(output.contains("Meta - Frontend Developer @ " + now.plusDays(3)));
        assertTrue(output.contains("Amazon - Backend Developer @ " + now.plusDays(6)));
        assertTrue(output.contains("NUS - Bus Driver @ " + now.plusDays(20)));
    }

    @Test
    public void execute_emptyFilteredList_printsNoUpcomingInterviewsMessage() throws GoldenCompassException {
        interviewList.add(new Interview(new Internship("Software Engineer", "Google"), now.minusDays(1)));
        interviewList.add(new Interview(new Internship("Frontend Developer", "Meta"), now.minusDays(3)));

        parser.parse("upcoming 1");
        upcomingCommand.execute();
        String output = outputStream.toString().trim();

        assertEquals("You don't have any upcoming interviews.", output);
    }

    @Test
    public void upcoming_nonIntegerParameter_throwsException() throws GoldenCompassException {
        parser.parse("upcoming abc");
        GoldenCompassException exception = assertThrows(GoldenCompassException.class, () -> upcomingCommand.execute());
        assertEquals("The parameter of the upcoming command must be an integer", exception.getMessage());
    }

}
