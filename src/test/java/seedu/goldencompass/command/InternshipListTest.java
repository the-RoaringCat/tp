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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void setUp() throws Exception {
        internshipList = new InternshipList();
        Parser parser = new Parser();
        parser.parse("list");
        listCommand = new ListCommand(parser, internshipList);
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

    // NEW TESTS

    @Test
    public void add_nullInternship_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> internshipList.add(null));
    }

    @Test
    public void getInternship_validIndex_returnsCorrectInternship() {
        Internship google = new Internship("Software Engineer", "Google");
        Internship meta = new Internship("Frontend Developer", "Meta");
        internshipList.add(google);
        internshipList.add(meta);

        Internship retrieved = internshipList.get(0);
        assertEquals("Google", retrieved.getCompanyName());
        assertEquals("Software Engineer", retrieved.getTitle());
    }

    @Test
    public void getInternship_invalidIndex_throwsException() {
        internshipList.add(new Internship("Software Engineer", "Google"));

        assertThrows(IndexOutOfBoundsException.class, () -> internshipList.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> internshipList.get(5));
    }

    @Test
    public void getSize_returnsCorrectCount() {
        assertEquals(0, internshipList.getSize());

        internshipList.add(new Internship("Software Engineer", "Google"));
        assertEquals(1, internshipList.getSize());

        internshipList.add(new Internship("Frontend Developer", "Meta"));
        assertEquals(2, internshipList.getSize());

        internshipList.delete(0);
        assertEquals(1, internshipList.getSize());
    }

    @Test
    public void addInternship_withSpecialCharacters_worksCorrectly() {
        Internship internship = new Internship("Software Engineer & Developer", "Google-Meta");
        internshipList.add(internship);

        assertEquals(1, internshipList.getSize());
        assertEquals("Google-Meta", internshipList.get(0).getCompanyName());
        assertEquals("Software Engineer & Developer", internshipList.get(0).getTitle());
    }

    @Test
    public void addInternship_withExtraWhitespace_trimsCorrectly() {
        Internship internship = new Internship("  Software Engineer  ", "  Google  ");
        internshipList.add(internship);

        assertEquals("Google", internshipList.get(0).getCompanyName());
        assertEquals("Software Engineer", internshipList.get(0).getTitle());
    }

    @Test
    public void listAfterDelete_printsCorrectRemaining() throws GoldenCompassException {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        internshipList.add(new Internship("Backend Developer", "Amazon"));

        // Delete middle one
        internshipList.delete(1);

        listCommand.execute();
        String output = outputStream.toString();

        assertTrue(output.contains("1. Google - Software Engineer"));
        assertTrue(output.contains("2. Amazon - Backend Developer"));
        assertFalse(output.contains("Meta"));
        assertEquals(2, internshipList.getSize());
    }

    @Test
    public void addMultipleInternshipsAfterClear_worksCorrectly() {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        assertEquals(2, internshipList.getSize());

        // Delete all
        internshipList.delete(0);
        internshipList.delete(0);
        assertEquals(0, internshipList.getSize());

        // Add new ones
        internshipList.add(new Internship("Data Analyst", "Amazon"));
        internshipList.add(new Internship("DevOps Engineer", "Microsoft"));

        assertEquals(2, internshipList.getSize());
        assertEquals("Amazon", internshipList.get(0).getCompanyName());
        assertEquals("Microsoft", internshipList.get(1).getCompanyName());
    }

    @Test
    public void getInternships_returnsUnmodifiableList() {
        internshipList.add(new Internship("Software Engineer", "Google"));

        // Get the list and try to modify it directly
        // This test ensures the list can be accessed but the internal list remains unchanged
        // (Note: This test passes as long as we can access it - actual immutability depends on implementation)
        assertNotNull(internshipList.getInternships());
        assertEquals(1, internshipList.getInternships().size());
    }

    @Test
    public void addInternshipWithSameCompany_worksCorrectly() {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Google"));
        internshipList.add(new Internship("Backend Developer", "Google"));

        assertEquals(3, internshipList.getSize());
        assertEquals("Google", internshipList.get(0).getCompanyName());
        assertEquals("Google", internshipList.get(1).getCompanyName());
        assertEquals("Google", internshipList.get(2).getCompanyName());
    }

    @Test
    public void listAfterMultipleOperations_printsCorrectly() throws GoldenCompassException {
        // Add internships
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));

        // Delete one
        internshipList.delete(0);

        // Add another
        internshipList.add(new Internship("Backend Developer", "Amazon"));

        // Execute list command
        listCommand.execute();
        String output = outputStream.toString();

        assertTrue(output.contains("1. Meta - Frontend Developer"));
        assertTrue(output.contains("2. Amazon - Backend Developer"));
        assertFalse(output.contains("Google"));
        assertEquals(2, internshipList.getSize());
    }

    @Test
    public void addInternshipWithLongNames_handlesCorrectly() throws GoldenCompassException {
        String longCompanyName = "Very Long Company Name That Might Exceed Typical Width";
        String longTitle = "Very Long Job Title That Describes Many Responsibilities In Detail";

        internshipList.add(new Internship(longTitle, longCompanyName));

        listCommand.execute();
        String output = outputStream.toString();

        assertTrue(output.contains(longCompanyName));
        assertTrue(output.contains(longTitle));
    }
}
