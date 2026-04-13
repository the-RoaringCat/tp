package seedu.goldencompass.internship;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Interview {

    protected Internship internship;
    protected LocalDateTime dateTime;



    public Interview(Internship internship, LocalDateTime date) {
        assert internship != null : "Internship should not be null";
        assert date != null : "Date should not be null when provided";
        this.internship = internship;
        this.dateTime = date;
        internship.setInterview(this);
    }

    public Interview(Internship internship) {
        assert internship != null : "Internship should not be null";
        this.internship = internship;
        this.dateTime = null;
        internship.setInterview(this);
    }

    /**
     * Copy constructor that does not copy internship field
     * @param other other
     */
    public Interview(Interview other) {

        this.dateTime = other.dateTime;
    }

    /**
     * Get a copy of the input {@code Interview}
     * @param other an Interview
     * @return an Interview object or null if other is null.
     */
    public static Interview copyOf(Interview other) {
        if (other == null) {
            return null;
        }
        return new Interview(other);
    }

    /**
     * Sets the deadline date and time of this interview.
     * @param date a {@code LocalDateTime} representing the new deadline.
     */
    public void setDate(LocalDateTime date) {
        assert date != null : "Date should not be null";
        this.dateTime = date;
    }

    /**
     * Returns the deadline date and time of this interview.
     * @return the deadline as a {@code LocalDateTime}.
     */
    public LocalDateTime getDate() {
        return dateTime;
    }

    @Override
    public String toString() {
        if (dateTime == null) {
            return internship.toString() + " @ No date set";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return internship.toString() + " @ " + dateTime.format(formatter);
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
        if (date != null && (this.dateTime == null || !this.dateTime.toLocalDate().equals(date))) {
            return false;
        }
        return true;
    }

    public void setInternship(Internship internship) {
        this.internship = internship;
    }
}
