package seedu.goldencompass.storage;

import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the loading and saving of internship data to a text file.
 */
public class InternshipStorage {

    private static final Logger logger = Logger.getLogger(InternshipStorage.class.getName());
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The relative path to the text file (e.g., "data/internships.txt").
     */
    public InternshipStorage(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "Storage file path cannot be null or empty";
        this.filePath = filePath;
    }

    /**
     * Ensures that the directory and the file exist before reading or writing.
     * If they do not exist, it creates them.
     */
    private void ensureFileExists() throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
            logger.log(Level.INFO, "Created missing data directory.");
        }

        if (!file.exists()) {
            file.createNewFile();
            logger.log(Level.INFO, "Created missing data file.");
        }
    }

    /**
     * Saves the current list of internships to the hard drive.
     * Formats the data with a " | " delimiter.
     *
     * @param internshipList The list of internships to save.
     */
    public void save(InternshipList internshipList) {
        assert internshipList != null : "Cannot save a null InternshipList";
        try {
            ensureFileExists();
            FileWriter fw = new FileWriter(filePath);

            for (Internship intern : internshipList.getInternships()) {
                // Determine the status to save
                String status = "PENDING";
                if (intern.hasOffer()) {
                    status = "OFFER";
                } else if (intern.isRejected()) {
                    status = "REJECTED";
                }

                // Format: Title | CompanyName | Status
                String saveLine = intern.getTitle() + " | " + intern.getCompanyName() + " | " + status + "\n";
                fw.write(saveLine);
            }

            fw.close();
            logger.log(Level.INFO, "Successfully saved internships to disk.");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save data: " + e.getMessage());
            System.out.println("Oops! Something went wrong while saving your data.");
        }
    }

    /**
     * Loads the internships from the text file into an ArrayList.
     *
     * @return An ArrayList of Internship objects.
     */
    public ArrayList<Internship> load() {
        ArrayList<Internship> loadedList = new ArrayList<>();

        try {
            ensureFileExists();
            File f = new File(filePath);
            Scanner s = new Scanner(f);

            while (s.hasNext()) {
                String line = s.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(" \\| ");

                if (parts.length == 2 || parts.length == 3) {
                    String title = parts[0].trim();
                    String company = parts[1].trim();

                    if (title.isEmpty() || company.isEmpty()) {
                        logger.log(Level.WARNING, "Skipped line with empty title or company: " + line);
                        continue;
                    }

                    // 1. Create it and hold it in a variable
                    Internship loadedInternship = new Internship(title, company);

                    // 2. Update the status if a 3rd column exists
                    if (parts.length == 3) {
                        String status = parts[2].trim().toUpperCase();

                        switch (status) {
                        case "OFFER":
                            loadedInternship.markAsOffer();
                            break;
                        case "REJECTED":
                            loadedInternship.markAsRejected();
                            break;
                        case "PENDING":
                            break;
                        default:
                            // This handles cases where the text file might be corrupted
                            logger.warning("Unknown status found in file: " + status);
                            break;
                        }
                    }

                    // 3. ONLY ADD ONCE at the very end
                    loadedList.add(loadedInternship);

                } else {
                    logger.log(Level.WARNING, "Skipped corrupted line: " + line);
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Save file not found, starting with an empty list.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error creating file during load: " + e.getMessage());
        }

        assert loadedList != null : "Loaded list should be initialized before returning";
        return loadedList;
    }
}
