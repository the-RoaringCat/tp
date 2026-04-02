package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListInterviewCommandTest {

    private InterviewList interviewList;
    private ListInterviewCommand listInterviewCommand;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        interviewList = new InterviewList();
        listInterviewCommand = new ListInterviewCommand(interviewList);
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void list_emptyList_printsNoInterviewsMessage() throws GoldenCompassException {
        listInterviewCommand.execute();
        String output = outputStream.toString().trim();
        assertEquals("You don't have any interviews!", output);
    }

    @Test
    public void list_singleInterview_printsCorrectly() throws GoldenCompassException {

        Internship google = new Internship("Software Engineer", "Google");
        interviewList.add(new Interview(google, LocalDateTime.parse("2026-03-25T09:00")));

        listInterviewCommand.execute();

        String output = outputStream.toString().trim();
        System.out.println(output);
        assertTrue(output.contains("Here are the interview invitations:"));
        assertTrue(output.contains("Google - Software Engineer @ 2026-03-25 09:00"));

    }

    @Test
    public void list_multipleInterviews_printsAllCorrectly() throws GoldenCompassException {

        Internship google = new Internship("Software Engineer", "Google");
        Internship meta = new Internship("Frontend Developer", "Meta");
        Internship amazon = new Internship("Backend Developer", "Amazon");
        Internship nus = new Internship("Bus Driver", "NUS");
        interviewList.add(new Interview(google, LocalDateTime.parse("2026-03-31T10:00")));
        interviewList.add(new Interview(meta, LocalDateTime.parse("2026-03-25T14:00")));
        interviewList.add(new Interview(amazon, LocalDateTime.parse("2026-04-01T09:00")));
        interviewList.add(new Interview(nus, LocalDateTime.parse("2026-02-26T11:00")));

        listInterviewCommand.execute();

        String output = outputStream.toString().trim();

        int nusIndex = output.indexOf("NUS - Bus Driver @ 2026-02-26");
        int metaIndex = output.indexOf("Meta - Frontend Developer @ 2026-03-25");
        int googleIndex = output.indexOf("Google - Software Engineer @ 2026-03-31");
        int amazonIndex = output.indexOf("Amazon - Backend Developer @ 2026-04-01");

        assertTrue(nusIndex < metaIndex);
        assertTrue(metaIndex < googleIndex);
        assertTrue(googleIndex < amazonIndex);

        assertEquals(4, interviewList.size());

    }

}
