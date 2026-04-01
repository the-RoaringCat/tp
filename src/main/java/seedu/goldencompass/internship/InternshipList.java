package seedu.goldencompass.internship;

import seedu.goldencompass.ui.Ui;
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

    /** UI object for displaying messages */
    private Ui ui;

    /**
     * Constructs an empty InternshipList.
     */
    public InternshipList() {
        logger.info("InternshipList created");
    }

    public InternshipList(InternshipList other) {
        this.internships.clear();
        this.internships.addAll(other.internships);
        this.ui = new Ui();
    }

    /**
     * Constructs an InternshipList loaded with existing data from storage.
     *
     * @param loadedInternships The list of internships loaded from the save file.
     */
    public InternshipList(List<Internship> loadedInternships) {
        if (loadedInternships != null) {
            this.internships.addAll(loadedInternships);
        }
        logger.info("InternshipList loaded with " + this.internships.size() + " existing records.");
    }

    /**
     * Sets the UI instance for displaying messages.
     *
     * @param ui The UI instance to use
     * @throws IllegalArgumentException if ui is null
     */
    public void setUi(Ui ui) {
        if (ui == null) {
            logger.warning("Attempted to set null UI");
            throw new IllegalArgumentException("UI cannot be null");
        }
        this.ui = ui;
        logger.fine("UI instance set successfully");
    }

    public List<Internship> getInternships() {
        assert internships != null : "Internships list should not be null";
        logger.finer("Returning internships list, size: " + internships.size());
        return internships;
    }

    public void setInternships(List<Internship> internships) {
        this.internships.clear();
        this.internships.addAll(internships);
    }

    /**
     * Adds a new internship to the list.
     *
     * @param x The internship object to be added
     */
    public void add(Internship x) {
        // Defensive check: internship cannot be null
        if (x == null) {
            logger.warning("Attempted to add null internship");
            throw new IllegalArgumentException("Internship cannot be null");
        }

        int sizeBefore = internships.size();
        internships.add(x);
        int sizeAfter = internships.size();

        logger.info("Added internship: " + x.getCompanyName()
                + " - " + x.getTitle());

        // Assertion: size should increase by exactly 1
        assert sizeAfter == sizeBefore + 1
                : "List size should increase by 1. Before: " + sizeBefore + ", After: " + sizeAfter;
    }

    /**
     * Deletes an internship at the specified index.
     *
     * @param index The index of the internship to delete (0-based)
     * @return The deleted internship
     * @throws IndexOutOfBoundsException if index is invalid
     */
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
     * Returns the number of internships in the list.
     *
     * @return The size of the internship list
     */
    public int getSize() {
        int size = internships.size();
        assert size >= 0 : "List size should never be negative";
        logger.finer("getSize() called, returning: " + size);
        return size;
    }

    /**
     * Gets an internship at a specific index.
     *
     * @param index The index of the internship (0-based)
     * @return The internship at the specified index
     * @throws IndexOutOfBoundsException if index is out of range
     */
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

    /**
     * Displays all internships in the list.
     *
     * @throws IllegalStateException if UI is not set
     */
    public void list() {
        logger.info("Listing internships, current size: " + internships.size());

        // Defensive check: UI must be set before displaying
        if (ui == null) {
            logger.severe("Cannot list internships: UI not set");
            throw new IllegalStateException("UI not initialized. Call setUi() first.");
        }

        if (internships.isEmpty()) {
            ui.print("No internships in the list.");
            logger.fine("List is empty, displayed message");
            return;
        }

        ui.print("Here are the internships you have added:");
        for (int i = 0; i < internships.size(); i++) {
            Internship intern = internships.get(i);
            assert intern != null : "Internship at index " + i + " should not be null";
            ui.print((i + 1) + ". " + intern.getCompanyName() + " - " + intern.getTitle());
        }

        logger.info("Displayed " + internships.size() + " internships");
    }

    public Internship findInternshipByCompany(String companyName) {
        for (Internship i : internships) {
            if (i.getCompanyName().equalsIgnoreCase(companyName)) {
                return i;
            }
        }
        return null;
    }
}
