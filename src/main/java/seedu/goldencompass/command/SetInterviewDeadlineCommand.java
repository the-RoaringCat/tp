package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.preparser.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static seedu.goldencompass.preparser.Config.DEFAULT_FLAG;

/**
 * Sets the deadline date of an interview identified by its 1-based index in the interview list.
 * <p>
 * Command format: {@code set-deadline INDEX /d DATE}
 * </p>
 */
public class SetInterviewDeadlineCommand implements Executable {

    public static final String COMMAND_WORD = "set-deadline";

    private static final String FLAG_DATE = "/d";
    private static final ArrayList<String> FLAGS = new ArrayList<>(Arrays.asList(FLAG_DATE));

    private final InterviewList interviewList;

    /**
     * Constructs a {@code SetInterviewDeadlineCommand} with the given {@code InterviewList}.
     * <p>
     *     Registers the command's flags with the global flag set and the command word
     *     with the global command set on construction.
     * </p>
     * @param interviewList the list of interviews to operate on.
     */
    public SetInterviewDeadlineCommand(InterviewList interviewList) {
        this.interviewList = interviewList;
        Config.registerFlag(FLAGS.toArray(new String[0]));
        Config.registerCommand(COMMAND_WORD);
    }

    /**
     * Sets the deadline of the interview at the specified index.
     * <p>
     *     Expects {@code /i} with a 1-based integer index and {@code /d} with a date string.
     * </p>
     * @param flagToParamMap a map of flags to their parameter lists.
     * @throws GoldenCompassException if flags are invalid, the index is not a valid integer,
     *     or the index is out of range.
     */
    @Override
    public void execute(Map<String, List<String>> flagToParamMap) throws GoldenCompassException {
        checkFlag(flagToParamMap, FLAGS);

        String indexParam = getParamsOf(DEFAULT_FLAG, flagToParamMap)[0].trim();
        String date = getParamsOf(FLAG_DATE, flagToParamMap)[0].trim();

        int index;
        try {
            index = Integer.parseInt(indexParam);
        } catch (NumberFormatException e) {
            throw new GoldenCompassException("Error: Index must be a valid integer, got: " + indexParam);
        }

        if (index < 1 || index > interviewList.size()) {
            throw new GoldenCompassException(
                    "Error: Index " + index + " is out of range. There are " + interviewList.size() + " interview(s).");
        }

        Interview interview = interviewList.get(index - 1);
        interview.setDate(date);

        System.out.println("Deadline set for interview " + index + ": " + date);
    }
}
