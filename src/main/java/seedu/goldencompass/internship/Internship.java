package seedu.goldencompass.internship;

import java.util.logging.Logger;

/**
 * Represents an internship application.
 * Contains information about the internship such as job title, company name,
 * application status, and interview details.
 */
public class Internship {

    private static final Logger logger = Logger.getLogger(Internship.class.getName());

    // 1. Group all PROTECTED variables together
    protected String title;
    protected String companyName;
    protected String comments;
    protected String link;
    protected boolean hasApplied;
    protected Interview interview;

    // 2. Group all PRIVATE variables together
    private ApplicationStatus status;


    /**
     * Constructs a new Internship with the specified title and company.
     * Sets hasApplied to true by default.
     *
     * @param title The job title
     * @param companyName The company name
     * @throws IllegalArgumentException if title or companyName is null or empty
     */
    public Internship(String title, String companyName) {
        // Defensive checks for title
        if (title == null) {
            logger.severe("Attempted to create internship with null title");
            throw new IllegalArgumentException("Title cannot be null");
        }
        if (title.trim().isEmpty()) {
            logger.warning("Attempted to create internship with empty title");
            throw new IllegalArgumentException("Title cannot be empty");
        }

        // Defensive checks for company name
        if (companyName == null) {
            logger.severe("Attempted to create internship with null company name");
            throw new IllegalArgumentException("Company name cannot be null");
        }
        if (companyName.trim().isEmpty()) {
            logger.warning("Attempted to create internship with empty company name");
            throw new IllegalArgumentException("Company name cannot be empty");
        }

        this.title = title.trim();
        this.companyName = companyName.trim();
        this.hasApplied = true;
        this.status = ApplicationStatus.PENDING;

        // Assertions to verify invariants
        assert this.title != null && !this.title.isEmpty()
                : "Title should be set after construction";
        assert this.companyName != null && !this.companyName.isEmpty()
                : "Company name should be set after construction";
        assert this.hasApplied : "hasApplied should be true after creation";

        logger.info("Created internship: " + this.companyName
                + " - " + this.title);
    }

    /**
     * Gets the job title.
     *
     * @return The job title
     */
    public String getTitle() {
        assert title != null : "Title should not be null";
        return title;
    }

    /**
     * Gets the company name.
     *
     * @return The company name
     */
    public String getCompanyName() {
        assert companyName != null : "Company name should not be null";
        return companyName;
    }

    /**
     * Gets the interview associated with this internship.
     *
     * @return The interview, or null if no interview scheduled
     */
    public Interview getInterview() {
        return interview;
    }

    /**
     * Deletes the interview associated with this internship.
     */
    public void deleteInterview() {
        if (interview != null) {
            logger.info("Deleting interview for: " + companyName + " - " + title);
            this.interview = null;
        } else {
            logger.fine("No interview to delete for: " + companyName);
        }
    }

    public void markAsOffer() {
        this.status = ApplicationStatus.OFFER;
    }

    public void markAsRejected() {
        this.status = ApplicationStatus.REJECTED;
    }

    public boolean hasOffer() {
        return this.status == ApplicationStatus.OFFER;
    }

    public boolean isRejected() {
        return this.status == ApplicationStatus.REJECTED;
    }

    @Override
    public String toString() {
        String tag = "";
        // Your teammate's suggested switch statement!
        switch (status) {
        case OFFER:
            tag = " [OFFER RECEIVED] 🏆";
            break;
        case REJECTED:
            tag = " [REJECTED] ❌";
            break;
        case PENDING:
            tag = "";
            break;
        default:
            tag = " ";
            break;
        }
        return companyName + " - " + title + tag;
    }
}
