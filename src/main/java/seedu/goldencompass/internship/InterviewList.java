package seedu.goldencompass.internship;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class InterviewList {

    private static final Logger logger = Logger.getLogger(InternshipList.class.getName());

    private final List<Interview> interviews = new ArrayList<>();

    public InterviewList() {

    }

    public InterviewList(InterviewList other) {
        this.interviews.clear();
        this.interviews.addAll(other.interviews);
    }


    public List<Interview> getInterviews() {
        return interviews;
    }

    public void add(Interview x) {
        assert x != null : "Interview to add should not be null";
        interviews.add(x);
        interviews.sort(Comparator.comparing(Interview::getDate));
    }

    /**
     * Deletes an interview by internship reference.
     *
     * @param internship The internship whose interview to delete
     */
    public void deleteByInternship(Internship internship) {
        if (internship == null) {
            logger.warning("Attempted to delete interview for null internship");
            return;
        }

        boolean removed = interviews.removeIf(interview ->
                interview.getInternship() != null && interview.getInternship().equals(internship));

        if (removed) {
            logger.info("Removed interview from InterviewList for: " + internship.getCompanyName());
        }
    }

    /**
     * Returns the {@code Interview} at the given zero-based index.
     * @param index zero-based index of the interview to retrieve.
     * @return the {@code Interview} at the given index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Interview get(int index) {
        assert index >= 0 && index < interviews.size() : "Index should be within valid range";
        return interviews.get(index);
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews.clear();
        this.interviews.addAll(interviews);
    }

    /**
     * Returns the number of interviews in the list.
     * @return the number of interviews.
     */
    public int size() {
        return interviews.size();
    }

    /**
     * Returns {@code true} if the given 1-based index is within the valid range.
     * @param index 1-based index to check.
     * @return {@code true} if index is valid, {@code false} otherwise.
     */
    public boolean isValidIndex(int index) {
        return index >= 1 && index <= interviews.size();
    }

}
