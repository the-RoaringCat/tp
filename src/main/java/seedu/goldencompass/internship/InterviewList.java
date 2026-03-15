package seedu.goldencompass.internship;

import java.util.ArrayList;

public class InterviewList {

    private final ArrayList<Interview> interviews = new ArrayList<>();

    public void add(Interview x) {
        interviews.add(x);
    }

    /**
     * Returns the {@code Interview} at the given zero-based index.
     * @param index zero-based index of the interview to retrieve.
     * @return the {@code Interview} at the given index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Interview get(int index) {
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
