package seedu.goldencompass.internship;

import seedu.goldencompass.ui.Ui;
import java.util.ArrayList;

/**
 * Manages a list of internship applications.
 * Provides functionality to add internships and display them.
 */
public class InternshipList {

    /** Internal list to store internship objects */
    private final ArrayList<Internship> internships = new ArrayList<>();

    /** UI object for printing messages */
    private Ui ui;

    /**
     * Constructs an InternshipList with the specified UI.
     *
     * @param ui The UI object to use for printing
     */
    public InternshipList(Ui ui) {
        this.ui = ui;
    }

    /**
     * Adds a new internship to the list.
     *
     * @param x The internship object to be added
     */
    public void add(Internship x) {

        internships.add(x);
    }

    /**
     * Returns the number of internships in the list.
     *
     * @return The size of the internship list
     */
    public int getSize() {
        return internships.size();
    }

    /**
     * Displays all internships in the list with their index numbers.
     * Shows company name and job title for each internship.
     * If the list is empty, displays a message indicating no internships.
     */
    public void list() {
        if (internships.isEmpty()) {
            System.out.println("No internships in the list.");
            return;
        }

        ui.print("Here are the internships you have added:");
        for (int i = 0; i < internships.size(); i++) {
            Internship intern = internships.get(i);
            ui.print((i + 1) + ". " + intern.companyName + " - " + intern.title);
        }
    }
}
