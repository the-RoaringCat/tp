package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;

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
    private ListCommand listCommand;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() throws Exception{
        internshipList = new InternshipList();
        Parser parser = new Parser();
        parser.parse("list");
        listCommand = new ListCommand(parser, internshipList);
        //internshipList.setUi(new Ui());
        // Redirect System.out to capture output for testing
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void list_emptyList_printsNoInternshipsMessage() throws GoldenCompassException {
        listCommand.execute();
        String output = outputStream.toString().trim();
        assertEquals("No internships in the list.", output);
    }

    @Test
    public void list_singleInternship_printsCorrectly() throws GoldenCompassException {
        Internship internship = new Internship("Software Engineer", "Google");
        internshipList.add(internship);

        listCommand.execute();
        String output = outputStream.toString().trim();

        assertTrue(output.contains("Here are the internships you have added:"));
        assertTrue(output.contains("1. Google - Software Engineer"));
    }

    @Test
    public void list_multipleInternships_printsAllCorrectly() throws GoldenCompassException {
        // Add multiple internships
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        internshipList.add(new Internship("Backend Developer", "Amazon"));

        listCommand.execute();
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
    public void list_largeNumberOfInternships_printsAll() throws GoldenCompassException {
        int numberOfInternships = 10;
        for (int i = 0; i < numberOfInternships; i++) {
            internshipList.add(new Internship("Position " + i, "Company " + i));
        }

        listCommand.execute();
        String output = outputStream.toString();

        for (int i = 0; i < numberOfInternships; i++) {
            assertTrue(output.contains((i + 1) + ". Company " + i + " - Position " + i));
        }
    }
}
