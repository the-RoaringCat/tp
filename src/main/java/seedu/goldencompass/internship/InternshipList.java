package seedu.goldencompass.internship;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Manages a list of internship applications.
 * Provides functionality to add internships and display them.
 */
public class InternshipList {

    private static final Logger logger = Logger.getLogger(InternshipList.class.getName());

    /** Internal list to store internship objects */
    private final List<Internship> internships = new ArrayList<>();

    /**
     * Constructs an empty InternshipList.
     */
    public InternshipList() {
        logger.info("InternshipList created");
    }

    /**
     * Constructs an InternshipList with existing data.
     *
     * @param loadedInternships The list of internships loaded from the save file.
     */
    public InternshipList(List<Internship> loadedInternships) {
        if (loadedInternships != null) {
            this.internships.addAll(loadedInternships);
        }
        logger.info("InternshipList created with " + this.internships.size() + " internships");
    }

    public List<Internship> getInternships() {
        assert internships != null : "Internships list should not be null";
        logger.finer("Returning internships list, size: " + internships.size());
        return internships;
    }

    /**
     * Replaces all internships in the list with the given list.
     *
     * @param internships The new list of internships
     */
    public void setInternships(List<Internship> internships) {
        this.internships.clear();
        if (internships != null) {
            this.internships.addAll(internships);
        }
        logger.info("InternshipList replaced with " + this.internships.size() + " internships");
    }

    public void add(Internship x) {
        if (x == null) {
            logger.warning("Attempted to add null internship");
            throw new IllegalArgumentException("Internship cannot be null");
        }

        for (int i = 0; i < internships.size(); i++) {
            if (internships.get(i).equals(x)) {
                throw new IllegalArgumentException("Warning: This internship already exists in your list!");
            }
        }

        int sizeBefore = internships.size();
        internships.add(x);
        int sizeAfter = internships.size();

        logger.info("Added internship: " + x.getCompanyName() + " - " + x.getTitle());

        assert sizeAfter == sizeBefore + 1
                : "List size should increase by 1. Before: " + sizeBefore + ", After: " + sizeAfter;
    }

    public int getSize() {
        int size = internships.size();
        assert size >= 0 : "List size should never be negative";
        logger.finer("getSize() called, returning: " + size);
        return size;
    }

    public Internship get(int index) {
        assert internships != null : "Internships list should not be null";

        if (index < 0 || index >= internships.size()) {
            logger.warning("Invalid index requested: " + index
                    + " (list size: " + internships.size() + ")");
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + internships.size());
        }

        Internship internship = internships.get(index);
        assert internship != null : "Internship at index " + index + " should not be null";
        logger.finer("Retrieved internship at index " + index);
        return internship;
    }

    public Internship delete(int index) {
        assert internships != null : "Internships list should not be null";

        if (index < 0 || index >= internships.size()) {
            logger.warning("Attempted to delete invalid index: " + index);
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + internships.size());
        }

        Internship deletedInternship = internships.remove(index);
        logger.info("Deleted internship: " + deletedInternship.getCompanyName()
                + " - " + deletedInternship.getTitle());
        assert internships.size() >= 0 : "List size should not be negative after deletion";

        return deletedInternship;
    }

    /**
     * Finds an internship by company name.
     *
     * @param companyName The company name to search for
     * @return The internship if found, null otherwise
     */
    public Internship findInternshipByCompany(String companyName) {
        if (companyName == null || companyName.trim().isEmpty()) {
            return null;
        }

        for (Internship internship : internships) {
            if (internship.getCompanyName().equalsIgnoreCase(companyName.trim())) {
                return internship;
            }
        }
        return null;
    }
}
