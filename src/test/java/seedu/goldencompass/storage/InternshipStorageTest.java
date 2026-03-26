package seedu.goldencompass.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InternshipStorageTest {

    private static final String TEST_FILE_PATH = "test_internships.txt";
    private InternshipStorage internshipStorage;
    private InternshipList internshipList;

    @BeforeEach
    public void setUp() {
        // Run this before EVERY test to set up a fresh environment
        internshipStorage = new InternshipStorage(TEST_FILE_PATH);
        internshipList = new InternshipList();
    }

    @AfterEach
    public void tearDown() {
        // Run this after EVERY test to clean up the dummy text file
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void saveAndLoad_validInternships_success() {
        // 1. Add fake data
        internshipList.add(new Internship("Software Engineer", "Grab"));
        internshipList.add(new Internship("Data Analyst", "Shopee"));

        // 2. Save the data to the dummy file
        internshipStorage.save(internshipList);

        // 3. Load the data back out
        ArrayList<Internship> loadedList = internshipStorage.load();

        // 4. Assert that everything matches perfectly!
        assertEquals(2, loadedList.size());
        assertEquals("Software Engineer", loadedList.get(0).getTitle());
        assertEquals("Grab", loadedList.get(0).getCompanyName());
        assertEquals("Data Analyst", loadedList.get(1).getTitle());
        assertEquals("Shopee", loadedList.get(1).getCompanyName());
    }

    @Test
    public void load_emptyFile_returnsEmptyList() {
        // Save a completely empty list
        internshipStorage.save(internshipList);

        // Load it back out
        ArrayList<Internship> loadedList = internshipStorage.load();

        // Assert it didn't magically create fake internships
        assertTrue(loadedList.isEmpty());
    }

    @Test
    public void load_corruptedFile_skipsCorruptedLines() throws IOException {
        // Manually write a corrupted text file to see if our Storage class catches it
        File testFile = new File(TEST_FILE_PATH);
        testFile.createNewFile();
        FileWriter fw = new FileWriter(testFile);
        fw.write("Valid Title | Valid Company\n");
        fw.write("This line is corrupted and has no delimiter\n");
        fw.write("Another Valid Title | Another Company\n");
        fw.close();

        // Load it out
        ArrayList<Internship> loadedList = internshipStorage.load();

        // Assert that it safely skipped the bad line and only loaded the 2 good ones
        assertEquals(2, loadedList.size());
        assertEquals("Valid Title", loadedList.get(0).getTitle());
        assertEquals("Another Valid Title", loadedList.get(1).getTitle());
    }
}
