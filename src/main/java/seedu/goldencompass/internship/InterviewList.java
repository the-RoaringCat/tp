package seedu.goldencompass.internship;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InterviewList {

    private final List<Interview> interviews = new ArrayList<>();

    public List<Interview> getInterviews() {
        return interviews;
    }

    public void add(Interview x) {
        assert x != null : "Interview to add should not be null";
        interviews.add(x);
        interviews.sort(Comparator.comparing(Interview::getDate));
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
