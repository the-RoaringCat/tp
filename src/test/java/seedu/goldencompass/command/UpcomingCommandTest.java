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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpcomingCommandTest {

    private Parser parser;
    private InterviewList interviewList;
    private UpcomingCommand upcomingCommand;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private LocalDateTime now;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeEach
    public void setUp() throws GoldenCompassException {
        parser = new Parser();
        interviewList = new InterviewList();
        upcomingCommand = new UpcomingCommand(parser, interviewList);
        now = LocalDateTime.now(ZoneId.systemDefault());
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void execute_upcomingWithoutParameter_printsUpcomingInterviewsCorrectly() throws GoldenCompassException {
        interviewList.add(new Interview(new Internship("Software Engineer", "Google"), now.plusMinutes(1)));
        interviewList.add(new Interview(new Internship("Frontend Developer", "Meta"), now.plusDays(3)));
        interviewList.add(new Interview(new Internship("Backend Developer", "Amazon"), now.plusDays(6)));
        interviewList.add(new Interview(new Internship("Bus Driver", "NUS"), now.plusDays(20)));

        parser.parse("upcoming");
        upcomingCommand.execute();
        String output = outputStream.toString().trim();

        assertTrue(output.contains("You have the following interviews in the upcoming 5 day(s):"));
        assertTrue(output.contains("Google - Software Engineer @ " + now.plusMinutes(1).format(formatter)));
        assertTrue(output.contains("Meta - Frontend Developer @ " + now.plusDays(3).format(formatter)));
        assertFalse(output.contains("Amazon - Backend Developer @ "));
        assertFalse(output.contains("NUS - Bus Driver @ "));
    }

    @Test
    public void execute_upcomingWithIntegerParameter_printsUpcomingInterviewsCorrectly() throws GoldenCompassException {
        interviewList.add(new Interview(new Internship("Software Engineer", "Google"), now.plusMinutes(1)));
        interviewList.add(new Interview(new Internship("Frontend Developer", "Meta"), now.plusDays(3)));
        interviewList.add(new Interview(new Internship("Backend Developer", "Amazon"), now.plusDays(6)));
        interviewList.add(new Interview(new Internship("Bus Driver", "NUS"), now.plusDays(20)));

        parser.parse("upcoming 7");
        upcomingCommand.execute();
        String output = outputStream.toString().trim();

        assertTrue(output.contains("Google - Software Engineer @ " + now.plusMinutes(1).format(formatter)));
        assertTrue(output.contains("Meta - Frontend Developer @ " + now.plusDays(3).format(formatter)));
        assertTrue(output.contains("Amazon - Backend Developer @ " + now.plusDays(6).format(formatter)));
        assertFalse(output.contains("NUS - Bus Driver @ "));

        parser.parse("upcoming 21");
        upcomingCommand.execute();
        output = outputStream.toString().trim();

        assertTrue(output.contains("Google - Software Engineer @ " + now.plusMinutes(1).format(formatter)));
        assertTrue(output.contains("Meta - Frontend Developer @ " + now.plusDays(3).format(formatter)));
        assertTrue(output.contains("Amazon - Backend Developer @ " + now.plusDays(6).format(formatter)));
        assertTrue(output.contains("NUS - Bus Driver @ " + now.plusDays(20).format(formatter)));
    }

    @Test
    public void execute_emptyFilteredList_printsNoUpcomingInterviewsMessage() throws GoldenCompassException {
        interviewList.add(new Interview(new Internship("Software Engineer", "Google"), now.minusMinutes(5)));
        interviewList.add(new Interview(new Internship("Frontend Developer", "Meta"), now.minusDays(3)));
        interviewList.add(new Interview(new Internship("Bus Driver", "NUS"), now.plusDays(2)));

        parser.parse("upcoming 1");
        upcomingCommand.execute();
        String output = outputStream.toString().trim();

        assertEquals("You don't have any interviews in the upcoming 1 day(s).", output);
    }

    @Test
    public void execute_nonIntegerParameter_throwsException() throws GoldenCompassException {
        parser.parse("upcoming abc");
        GoldenCompassException exception1 = assertThrows(GoldenCompassException.class, () -> upcomingCommand.execute());
        assertEquals("The parameter of the upcoming command must be a single integer.", exception1.getMessage());

        parser.parse("upcoming 3 10");
        GoldenCompassException exception2 = assertThrows(GoldenCompassException.class, () -> upcomingCommand.execute());
        assertEquals("The parameter of the upcoming command must be a single integer.", exception2.getMessage());
    }

    @Test
    public void execute_negativeParameter_printsPastInterviews() throws GoldenCompassException {
        interviewList.add(new Interview(new Internship("Software Engineer", "Google"), now.minusMinutes(1)));
        interviewList.add(new Interview(new Internship("Frontend Developer", "Meta"), now.minusDays(3)));

        parser.parse("upcoming -1");
        upcomingCommand.execute();
        String output = outputStream.toString().trim();

        assertTrue(output.contains("You have the following interviews in the upcoming -1 day(s):"));
        assertTrue(output.contains("Google - Software Engineer @ " + now.minusMinutes(1).format(formatter)));
        assertFalse(output.contains("Meta - Frontend Developer @ "));

        parser.parse("upcoming -4");
        upcomingCommand.execute();
        output = outputStream.toString().trim();

        assertTrue(output.contains("You have the following interviews in the upcoming -4 day(s):"));
        assertTrue(output.contains("Google - Software Engineer @ " + now.minusMinutes(1).format(formatter)));
        assertTrue(output.contains("Meta - Frontend Developer @ " + now.minusDays(3).format(formatter)));
    }

    @Test
    public void getterForHelpMessages_help_printsHelpMessages() {
        String commandDescription = upcomingCommand.getCommandDescription();
        String flagDescription = upcomingCommand.getFlagDescription();

        String expected =
                "The command lists upcoming interviews within a specified number of days " +
                        "(inclusive of current date time).";

        assertEquals(expected, commandDescription);

        expected = String.join(System.lineSeparator(),
                        "Command format:",
                        "\tupcoming [days]",
                        "If the optional parameter [days] is not supplied, a default of 5 days will be used.",
                        "Example usage:",
                        "\tupcoming -> shows interviews in the next 5 days",
                        "\tupcoming 3 -> shows interviews in the next 3 days",
                        "If the parameter [days] is a negative integer -N, " +
                        "then interviews in the past N days will be shown."
        );

        assertEquals(expected, flagDescription);
    }

}
