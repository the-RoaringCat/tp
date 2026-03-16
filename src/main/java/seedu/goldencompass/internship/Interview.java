package seedu.goldencompass.internship;

import java.time.LocalDate;

public class Interview {

    protected Internship internship;
    protected LocalDate date;
    protected String comments;

    public Interview(Internship internship, LocalDate date) {
        this.internship = internship;
        this.date = date;
    }

    public Interview(Internship internship) {
        this.internship = internship;
        this.date = null;
    }

    /**
     * Sets the deadline date of this interview.
     * @param date a {@code LocalDate} representing the new deadline date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the deadline date of this interview.
     * @return the deadline date as a {@code LocalDate}.
     */
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return internship.toString() + " @ " + (date != null ? date : "No date set");
    }

}
