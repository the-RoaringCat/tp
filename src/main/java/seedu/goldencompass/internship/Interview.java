package seedu.goldencompass.internship;

import java.time.LocalDate;

public class Interview {

    protected Internship internship;
    protected LocalDate date;

    public Interview(Internship internship, LocalDate date) {
        assert internship != null : "Internship should not be null";
        assert date != null : "Date should not be null when provided";
        this.internship = internship;
        this.date = date;
    }

    public Interview(Internship internship) {
        assert internship != null : "Internship should not be null";
        this.internship = internship;
        this.date = null;
    }

    /**
     * Sets the deadline date of this interview.
     * @param date a {@code LocalDate} representing the new deadline date.
     */
    public void setDate(LocalDate date) {
        assert date != null : "Date should not be null";
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

    public Internship getInternship() {
        return this.internship;
    }

    /**
     * Returns {@code true} if this interview matches all non-null criteria.
     * Text matching is case-insensitive substring matching.
     *
     * @param company the company name to match, or {@code null} to skip.
     * @param title   the role/title to match, or {@code null} to skip.
     * @param date    the date to match exactly, or {@code null} to skip.
     * @return {@code true} if all non-null criteria match.
     */
    public boolean matches(String company, String title, LocalDate date) {
        if (company != null
                && !internship.companyName.toLowerCase().contains(company.toLowerCase())) {
            return false;
        }
        if (title != null
                && !internship.title.toLowerCase().contains(title.toLowerCase())) {
            return false;
        }
        if (date != null && (this.date == null || !this.date.equals(date))) {
            return false;
        }
        return true;
    }

}
