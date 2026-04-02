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

        // 2. Setup mock internships (Interviews need these to exist first!)
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

        // 4. Load back into a fresh list
        InterviewList loadedList = new InterviewList();
        storage.load(loadedList, internshipList);

        // 5. Assertions
        assertEquals(1, loadedList.size(), "Should have loaded 1 interview");
        assertEquals("Google", loadedList.get(0).getInternship().getCompanyName());
        assertEquals(LocalDateTime.parse("2026-06-01T10:00"), loadedList.get(0).getDate());
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
