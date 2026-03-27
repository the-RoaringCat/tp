package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DeleteInternshipCommandTest {

    private InternshipList internshipList;

    @BeforeEach
    void setUp() {
        internshipList = new InternshipList();
    }

    @Test
    void delete_firstInternship_removesCorrectly() {
        // Add internships
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));

        assertEquals(2, internshipList.getSize());
        assertEquals("Google", internshipList.get(0).getCompanyName());

        // Delete first internship
        internshipList.delete(0);

        assertEquals(1, internshipList.getSize());
        assertEquals("Meta", internshipList.get(0).getCompanyName());
    }

    @Test
    void delete_lastInternship_removesCorrectly() {
        // Add internships
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        internshipList.add(new Internship("Backend Developer", "Amazon"));

        assertEquals(3, internshipList.getSize());

        // Delete last internship (index 2)
        internshipList.delete(2);

        assertEquals(2, internshipList.getSize());
        assertEquals("Google", internshipList.get(0).getCompanyName());
        assertEquals("Meta", internshipList.get(1).getCompanyName());
    }

    @Test
    void delete_middleInternship_removesCorrectly() {
        // Add internships
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        internshipList.add(new Internship("Backend Developer", "Amazon"));

        // Delete middle internship (index 1)
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
}