package seedu.goldencompass.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;

import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterviewStorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void saveAndLoad_validInterviews_success() {
        // 1. Setup paths and storage
        String testPath = tempDir.resolve("testInterviews.txt").toString();
        InterviewStorage storage = new InterviewStorage(testPath);

        // 2. Setup mock internships
        Internship google = new Internship("Software Engineer", "Google");
        InternshipList internshipList = new InternshipList();
        internshipList.add(google);

        // 3. Create and save an interview
        InterviewList interviewList = new InterviewList();
        Interview interview = new Interview(google);
        interview.setDate(LocalDateTime.parse("2026-06-01T10:00"));
        interviewList.add(interview);

        // Save to temp file
        storage.save(interviewList);

        // ✨ FIX: Reset the internship state so the loader doesn't think it's a duplicate
        google.setInterview(null);

        // 4. Load back into a fresh list
        InterviewList loadedList = new InterviewList();
        storage.load(loadedList, internshipList);

        // 5. Assertions
        assertEquals(1, loadedList.size(), "Should have loaded 1 interview");
        assertEquals("Google", loadedList.get(0).getInternship().getCompanyName());
        assertEquals("Software Engineer", loadedList.get(0).getInternship().getTitle());
        assertEquals(LocalDateTime.parse("2026-06-01T10:00"), loadedList.get(0).getDate());
    }

    @Test
    public void saveAndLoad_multipleRolesSameCompany_mapsCorrectly() {
        String testPath = tempDir.resolve("testMultipleRoles.txt").toString();
        InterviewStorage storage = new InterviewStorage(testPath);

        // Setup TWO internships at the SAME company
        Internship swe = new Internship("Software Engineer", "Grab");
        Internship data = new Internship("Data Analyst", "Grab");
        InternshipList internshipList = new InternshipList();
        internshipList.add(swe);
        internshipList.add(data);

        // Create interviews for both
        InterviewList originalList = new InterviewList();
        Interview sweInterview = new Interview(swe);
        sweInterview.setDate(LocalDateTime.parse("2026-12-01T10:00"));

        Interview dataInterview = new Interview(data);
        dataInterview.setDate(LocalDateTime.parse("2026-12-05T14:00"));

        originalList.add(sweInterview);
        originalList.add(dataInterview);

        // Save them
        storage.save(originalList);

        // ✨ FIX: Reset state for both roles so they can be re-linked during load
        swe.setInterview(null);
        data.setInterview(null);

        // Load them back into a fresh list
        InterviewList loadedList = new InterviewList();
        storage.load(loadedList, internshipList);

        // Assertions
        assertEquals(2, loadedList.size(), "Should have loaded 2 interviews");

        assertEquals("Grab", loadedList.get(0).getInternship().getCompanyName());
        assertEquals("Software Engineer", loadedList.get(0).getInternship().getTitle());
        assertEquals(LocalDateTime.parse("2026-12-01T10:00"), loadedList.get(0).getDate());

        assertEquals("Grab", loadedList.get(1).getInternship().getCompanyName());
        assertEquals("Data Analyst", loadedList.get(1).getInternship().getTitle());
        assertEquals(LocalDateTime.parse("2026-12-05T14:00"), loadedList.get(1).getDate());
    }

    @Test
    public void load_nonExistentFile_returnsEmptyList() {
        InterviewStorage storage = new InterviewStorage("non_existent_file.txt");
        InterviewList list = new InterviewList();
        InternshipList internships = new InternshipList();

        storage.load(list, internships);
        assertEquals(0, list.size(), "Loading a non-existent file should return empty list");
    }
}
