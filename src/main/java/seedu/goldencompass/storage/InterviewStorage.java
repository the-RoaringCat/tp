package seedu.goldencompass.storage;

import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterviewStorage {
    private static final Logger logger = Logger.getLogger(InterviewStorage.class.getName());
    private final String filePath;

    public InterviewStorage(String filePath) {
        assert filePath != null : "File path cannot be null";
        this.filePath = filePath;
    }

    /**
     * Saves interviews by storing the linked company name and the date.
     */
    public void save(InterviewList interviewList) {
        try {
            File f = new File(filePath);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            FileWriter fw = new FileWriter(filePath);

            for (Interview i : interviewList.getInterviews()) {
                String company = i.getInternship().getCompanyName();
                String role = i.getInternship().getTitle();
                String date = (i.getDate() != null) ? i.getDate().toString() : "null";

                fw.write(company + " | " + role + " | " + date + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving interviews", e);
        }
    }

    /**
     * Loads interviews and reconnects them to existing internships in the list.
     */
    public void load(InterviewList interviewList, InternshipList internshipList) {
        File f = new File(filePath);
        if (!f.exists()) {
            return;
        }

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] parts = line.split(" \\| ");

                // CHANGED: We now expect 3 parts (Company, Role, Date)
                if (parts.length < 3) {
                    continue;
                }

                String companyName = parts[0];
                String role = parts[1]; // ADDED: Extract the role
                String dateStr = parts[2]; // CHANGED: Date is now at index 2

                // CHANGED: Use a new composite search method (see Step 3 below)
                Internship linkedInternship = internshipList.findInternshipByCompanyAndRole(companyName, role);

                if (linkedInternship != null) {
                    Interview interview = new Interview(linkedInternship);
                    if (!dateStr.equals("null")) {
                        try {
                            interview.setDate(LocalDateTime.parse(dateStr));
                        } catch (DateTimeParseException e) {
                            logger.warning("Invalid date format for: " + companyName);
                        }
                    }
                    interviewList.add(interview);
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not load interviews from " + filePath);
        }
    }
}
