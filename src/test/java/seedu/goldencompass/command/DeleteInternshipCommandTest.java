package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteInternshipCommandTest {

    private InternshipList internshipList;

    @BeforeEach
    void setUp() {
        internshipList = new InternshipList();
    }

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
}
