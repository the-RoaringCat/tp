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
import static org.junit.jupiter.api.Assertions.assertFalse;

public class InternshipStorageTest {

    private static final String TEST_FILE_PATH = "test_internships.txt";
    private InternshipStorage internshipStorage;
    private InternshipList internshipList;

    @BeforeEach
    public void setUp() {
        internshipStorage = new InternshipStorage(TEST_FILE_PATH);
        internshipList = new InternshipList();
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void saveAndLoad_withStatuses_success() {
        // 1. Create three different types of internships
        Internship offerIntern = new Internship("Software Engineer", "Grab");
        offerIntern.markAsOffer();

        Internship rejectedIntern = new Internship("Data Analyst", "Shopee");
        rejectedIntern.markAsRejected();

        Internship pendingIntern = new Internship("Frontend Dev", "TikTok");

        internshipList.add(offerIntern);
        internshipList.add(rejectedIntern);
        internshipList.add(pendingIntern);

        // 2. Save and Reload
        internshipStorage.save(internshipList);
        ArrayList<Internship> loadedList = internshipStorage.load();

        // 3. Assertions
        assertEquals(3, loadedList.size());

        // Check the Offer
        assertTrue(loadedList.get(0).hasOffer());
        assertFalse(loadedList.get(0).isRejected());

        // Check the Rejection
        assertTrue(loadedList.get(1).isRejected());
        assertFalse(loadedList.get(1).hasOffer());

        // Check the Pending one
        assertFalse(loadedList.get(2).hasOffer());
        assertFalse(loadedList.get(2).isRejected());
    }

    @Test
    public void load_legacyTwoColumnFormat_loadsAsPending() throws IOException {
        // Manually create an OLD style file (only 2 columns)
        File testFile = new File(TEST_FILE_PATH);
        FileWriter fw = new FileWriter(testFile);
        fw.write("Old Role | Old Company\n");
        fw.close();

        ArrayList<Internship> loadedList = internshipStorage.load();

        // Should still load fine, just with no offer/rejection status
        assertEquals(1, loadedList.size());
        assertEquals("Old Role", loadedList.get(0).getTitle());
        assertFalse(loadedList.get(0).hasOffer());
        assertFalse(loadedList.get(0).isRejected());
    }

    @Test
    public void load_emptyFile_returnsEmptyList() {
        internshipStorage.save(internshipList);
        ArrayList<Internship> loadedList = internshipStorage.load();
        assertTrue(loadedList.isEmpty());
    }

    @Test
    public void load_corruptedFile_skipsCorruptedLines() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        testFile.createNewFile();
        FileWriter fw = new FileWriter(testFile);
        fw.write("Valid Title | Valid Company | OFFER\n");
        fw.write("Corrupted line with no pipes\n"); // Should be skipped
        fw.write("Another Title | Another Company | REJECTED\n");
        fw.close();

        ArrayList<Internship> loadedList = internshipStorage.load();

        assertEquals(2, loadedList.size());
        assertTrue(loadedList.get(0).hasOffer());
        assertTrue(loadedList.get(1).isRejected());
    }
}
