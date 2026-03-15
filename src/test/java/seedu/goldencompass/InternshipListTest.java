package seedu.goldencompass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for InternshipList.
 * Contains unit tests for listing internships functionality.
 */
public class InternshipListTest {

    private InternshipList internshipList;
    private Ui ui;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
        internshipList = new InternshipList();
        //internshipList.setUi(new Ui());
        // Redirect System.out to capture output for testing
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void list_emptyList_printsNoInternshipsMessage() {
        internshipList.list();
        String output = outputStream.toString().trim();
        assertEquals("No internships in the list.", output);
    }

    @Test
    public void list_singleInternship_printsCorrectly() {
        Internship internship = new Internship("Software Engineer", "Google");
        internshipList.add(internship);

        internshipList.list();
        String output = outputStream.toString().trim();

        assertTrue(output.contains("Here are the internships you have added:"));
        assertTrue(output.contains("1. Google - Software Engineer"));
    }

    @Test
    public void list_multipleInternships_printsAllCorrectly() {
        // Add multiple internships
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        internshipList.add(new Internship("Backend Developer", "Amazon"));

        internshipList.list();
        String output = outputStream.toString();

        assertTrue(output.contains("1. Google - Software Engineer"));
        assertTrue(output.contains("2. Meta - Frontend Developer"));
        assertTrue(output.contains("3. Amazon - Backend Developer"));
        assertEquals(3, internshipList.getSize());
    }

    @Test
    public void add_internship_increasesListSize() {
        assertEquals(0, internshipList.getSize());

        internshipList.add(new Internship("Software Engineer", "Google"));
        assertEquals(1, internshipList.getSize());

        internshipList.add(new Internship("Frontend Developer", "Meta"));
        assertEquals(2, internshipList.getSize());
    }

    // Optional: Test for large number of entries
    @Test
    public void list_largeNumberOfInternships_printsAll() {
        int numberOfInternships = 10;
        for (int i = 0; i < numberOfInternships; i++) {
            internshipList.add(new Internship("Position " + i, "Company " + i));
        }

        internshipList.list();
        String output = outputStream.toString();

        for (int i = 0; i < numberOfInternships; i++) {
            assertTrue(output.contains((i + 1) + ". Company " + i + " - Position " + i));
        }
    }
}
