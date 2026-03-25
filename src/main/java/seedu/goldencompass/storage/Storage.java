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
public class Storage {

    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The relative path to the text file (e.g., "data/internships.txt").
     */
    public Storage(String filePath) {
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

            // Use your teammate's getInternships() method to loop through everything!
            for (Internship intern : internshipList.getInternships()) {
                // Format: Title | CompanyName
                String saveLine = intern.getTitle() + " | " + intern.getCompanyName() + "\n";
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
                    continue; // Skip empty lines
                }

                // Split by the " | " delimiter we used during save
                String[] parts = line.split(" \\| ");

                if (parts.length == 2) {
                    String title = parts[0].trim();
                    String company = parts[1].trim();
                    loadedList.add(new Internship(title, company));
                } else {
                    logger.log(Level.WARNING, "Skipped corrupted line in save file: " + line);
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
