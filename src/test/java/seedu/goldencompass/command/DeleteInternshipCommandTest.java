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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DeleteInternshipCommandTest {

    private InternshipList internshipList;
    private InterviewList interviewList;
    private Parser parser;
    private DeleteInternshipCommand deleteCommand;
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

    // Existing tests...

    @Test
    void delete_firstInternship_removesCorrectly() {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));

        assertEquals(2, internshipList.getSize());
        assertEquals("Google", internshipList.get(0).getCompanyName());

        internshipList.delete(0);

        assertEquals(1, internshipList.getSize());
        assertEquals("Meta", internshipList.get(0).getCompanyName());
    }

    @Test
    void delete_lastInternship_removesCorrectly() {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        internshipList.add(new Internship("Backend Developer", "Amazon"));

        assertEquals(3, internshipList.getSize());

        internshipList.delete(2);

        assertEquals(2, internshipList.getSize());
        assertEquals("Google", internshipList.get(0).getCompanyName());
        assertEquals("Meta", internshipList.get(1).getCompanyName());
    }

    @Test
    void delete_middleInternship_removesCorrectly() {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        internshipList.add(new Internship("Backend Developer", "Amazon"));

        internshipList.delete(1);

        assertEquals(2, internshipList.getSize());
        assertEquals("Google", internshipList.get(0).getCompanyName());
        assertEquals("Amazon", internshipList.get(1).getCompanyName());
    }

    @Test
    void delete_indexOutOfBounds_throwsException() {
        internshipList.add(new Internship("Software Engineer", "Google"));

        assertThrows(IndexOutOfBoundsException.class, () -> internshipList.delete(5));
        assertThrows(IndexOutOfBoundsException.class, () -> internshipList.delete(-1));
    }

    @Test
    void delete_emptyList_throwsException() {
        assertEquals(0, internshipList.getSize());
        assertThrows(IndexOutOfBoundsException.class, () -> internshipList.delete(0));
    }

    // NEW TESTS

    @Test
    void delete_singleInternship_removesSuccessfully() {
        internshipList.add(new Internship("Software Engineer", "Google"));
        assertEquals(1, internshipList.getSize());

        internshipList.delete(0);

        assertEquals(0, internshipList.getSize());
    }

    @Test
    void delete_multipleInternshipsSequentially_removesAll() {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        internshipList.add(new Internship("Backend Developer", "Amazon"));

        internshipList.delete(0); // Remove Google
        assertEquals(2, internshipList.getSize());
        assertEquals("Meta", internshipList.get(0).getCompanyName());

        internshipList.delete(0); // Remove Meta
        assertEquals(1, internshipList.getSize());
        assertEquals("Amazon", internshipList.get(0).getCompanyName());

        internshipList.delete(0); // Remove Amazon
        assertEquals(0, internshipList.getSize());
    }

    @Test
    void delete_returnsCorrectDeletedInternship() {
        Internship google = new Internship("Software Engineer", "Google");
        internshipList.add(google);
        internshipList.add(new Internship("Frontend Developer", "Meta"));

        Internship deleted = internshipList.delete(0);

        assertNotNull(deleted);
        assertEquals("Google", deleted.getCompanyName());
        assertEquals("Software Engineer", deleted.getTitle());
    }

    @Test
    void delete_withAssociatedInterview_removesBoth() throws Exception {
        // Add internship
        Internship google = new Internship("Software Engineer", "Google");
        internshipList.add(google);

        // Add interview
        LocalDateTime interviewDate = LocalDateTime.of(2025, 6, 15, 10, 0);
        Interview interview = new Interview(google, interviewDate);
        google.setInterview(interview);
        interviewList.add(interview);

        assertEquals(1, internshipList.getSize());
        assertEquals(1, interviewList.size());
        assertNotNull(google.getInterview());

        // Delete internship
        parser.parse("delete 1");
        deleteCommand = new DeleteInternshipCommand(parser, internshipList, interviewList);
        deleteCommand.execute();

        // Verify both are gone
        assertEquals(0, internshipList.getSize());
        assertEquals(0, interviewList.size());

        String output = outputStream.toString();
        assertTrue(output.contains("✓ Deleted internship: Google - Software Engineer"));
    }

    @Test
    void delete_withoutAssociatedInterview_removesOnlyInternship() throws Exception {
        // Add internship without interview
        Internship google = new Internship("Software Engineer", "Google");
        internshipList.add(google);

        assertEquals(1, internshipList.getSize());
        assertEquals(0, interviewList.size());
        assertNull(google.getInterview());

        // Delete internship
        parser.parse("delete 1");
        deleteCommand = new DeleteInternshipCommand(parser, internshipList, interviewList);
        deleteCommand.execute();

        // Verify internship is gone
        assertEquals(0, internshipList.getSize());
        assertEquals(0, interviewList.size());

        String output = outputStream.toString();
        assertTrue(output.contains("✓ Deleted internship: Google - Software Engineer"));
    }

    @Test
    void delete_invalidIndexZero_throwsException() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));

        parser.parse("delete 0");
        deleteCommand = new DeleteInternshipCommand(parser, internshipList, interviewList);

        assertThrows(GoldenCompassException.class, () -> deleteCommand.execute());
    }

    @Test
    void delete_negativeIndex_throwsException() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));

        parser.parse("delete -5");
        deleteCommand = new DeleteInternshipCommand(parser, internshipList, interviewList);

        assertThrows(GoldenCompassException.class, () -> deleteCommand.execute());
    }

    @Test
    void delete_nonNumericIndex_throwsException() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));

        parser.parse("delete abc");
        deleteCommand = new DeleteInternshipCommand(parser, internshipList, interviewList);

        assertThrows(GoldenCompassException.class, () -> deleteCommand.execute());
    }

    @Test
    void delete_missingIndex_throwsException() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));

        parser.parse("delete");
        deleteCommand = new DeleteInternshipCommand(parser, internshipList, interviewList);

        assertThrows(GoldenCompassException.class, () -> deleteCommand.execute());
    }

    @Test
    void delete_largeIndexOutOfBounds_throwsException() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));

        parser.parse("delete 10");
        deleteCommand = new DeleteInternshipCommand(parser, internshipList, interviewList);

        assertThrows(GoldenCompassException.class, () -> deleteCommand.execute());
    }

    @Test
    void delete_withWhitespaceInIndex_worksCorrectly() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));

        parser.parse("delete   2   ");
        deleteCommand = new DeleteInternshipCommand(parser, internshipList, interviewList);
        deleteCommand.execute();

        assertEquals(1, internshipList.getSize());
        assertEquals("Google", internshipList.get(0).getCompanyName());

        String output = outputStream.toString();
        assertTrue(output.contains("✓ Deleted internship: Meta - Frontend Developer"));
    }
}
