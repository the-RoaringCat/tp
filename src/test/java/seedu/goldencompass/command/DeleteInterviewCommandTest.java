package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DeleteInterviewCommandTest {

    private InternshipList internshipList;
    private InterviewList interviewList;
    private Parser parser;
    private DeleteInterviewCommand deleteInterviewCommand;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() throws Exception {
        internshipList = new InternshipList();
        interviewList = new InterviewList();
        parser = new Parser();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void execute_validIndex_deletesInterviewSuccessfully() throws Exception {
        // Add an internship
        Internship internship = new Internship("Software Engineer", "Google");
        internshipList.add(internship);

        // Add an interview to the internship
        LocalDateTime interviewDate = LocalDateTime.of(2025, 6, 15, 10, 0);
        Interview interview = new Interview(internship, interviewDate);
        internship.setInterview(interview);
        interviewList.add(interview);

        assertEquals(1, interviewList.size());

        // Parse the command
        parser.parse("delete-interview 1");

        // Execute delete-interview command
        deleteInterviewCommand = new DeleteInterviewCommand(parser, internshipList, interviewList);
        deleteInterviewCommand.execute();

        // Verify interview was deleted
        assertEquals(0, interviewList.size());
        assertNull(internship.getInterview());

        // Verify output message
        String output = outputStream.toString();
        assertTrue(output.contains("✓ Deleted interview for Google"));
    }

    @Test
    void execute_invalidIndexOutOfBounds_throwsException() throws Exception {
        // Add an internship but no interview
        Internship internship = new Internship("Software Engineer", "Google");
        internshipList.add(internship);

        parser.parse("delete-interview 5"); // Index out of bounds

        deleteInterviewCommand = new DeleteInterviewCommand(parser, internshipList, interviewList);

        assertThrows(GoldenCompassException.class, () -> {
            deleteInterviewCommand.execute();
        });
    }

    @Test
    void execute_negativeIndex_throwsException() throws Exception {
        Internship internship = new Internship("Software Engineer", "Google");
        internshipList.add(internship);

        parser.parse("delete-interview -1");

        deleteInterviewCommand = new DeleteInterviewCommand(parser, internshipList, interviewList);

        assertThrows(GoldenCompassException.class, () -> {
            deleteInterviewCommand.execute();
        });
    }

    @Test
    void execute_nonIntegerIndex_throwsException() throws Exception {
        Internship internship = new Internship("Software Engineer", "Google");
        internshipList.add(internship);

        parser.parse("delete-interview abc");

        deleteInterviewCommand = new DeleteInterviewCommand(parser, internshipList, interviewList);

        assertThrows(GoldenCompassException.class, () -> {
            deleteInterviewCommand.execute();
        });
    }

    @Test
    void execute_missingIndex_throwsException() throws Exception {
        parser.parse("delete-interview");

        deleteInterviewCommand = new DeleteInterviewCommand(parser, internshipList, interviewList);

        assertThrows(GoldenCompassException.class, () -> {
            deleteInterviewCommand.execute();
        });
    }

    @Test
    void execute_noInterviewForInternship_throwsException() throws Exception {
        // Add internship without interview
        Internship internship = new Internship("Software Engineer", "Google");
        internshipList.add(internship);

        parser.parse("delete-interview 1");

        deleteInterviewCommand = new DeleteInterviewCommand(parser, internshipList, interviewList);

        GoldenCompassException exception = assertThrows(GoldenCompassException.class, () -> {
            deleteInterviewCommand.execute();
        });

        assertTrue(exception.getMessage().contains("does not have an interview scheduled"));
    }

    @Test
    void execute_emptyInternshipList_throwsException() throws Exception {
        parser.parse("delete-interview 1");

        deleteInterviewCommand = new DeleteInterviewCommand(parser, internshipList, interviewList);

        assertThrows(GoldenCompassException.class, () -> {
            deleteInterviewCommand.execute();
        });
    }

    @Test
    void execute_multipleInternships_deletesCorrectInterview() throws Exception {
        // Add first internship with interview
        Internship google = new Internship("Software Engineer", "Google");
        internshipList.add(google);
        LocalDateTime date1 = LocalDateTime.of(2025, 6, 15, 10, 0);
        Interview interview1 = new Interview(google, date1);
        google.setInterview(interview1);
        interviewList.add(interview1);

        // Add second internship with interview
        Internship meta = new Internship("Frontend Developer", "Meta");
        internshipList.add(meta);
        LocalDateTime date2 = LocalDateTime.of(2025, 7, 1, 14, 30);
        Interview interview2 = new Interview(meta, date2);
        meta.setInterview(interview2);
        interviewList.add(interview2);

        assertEquals(2, interviewList.size());

        // Delete interview for first internship
        parser.parse("delete-interview 1");
        deleteInterviewCommand = new DeleteInterviewCommand(parser, internshipList, interviewList);
        deleteInterviewCommand.execute();

        // Verify only first interview was deleted
        assertEquals(1, interviewList.size());
        assertNull(google.getInterview());
        assertNotNull(meta.getInterview());

        String output = outputStream.toString();
        assertTrue(output.contains("✓ Deleted interview for Google"));
        assertFalse(output.contains("Meta"));
    }
}
