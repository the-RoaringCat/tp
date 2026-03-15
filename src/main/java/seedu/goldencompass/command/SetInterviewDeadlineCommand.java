package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.preparser.Config;
import seedu.goldencompass.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    private final Ui ui = new Ui();

    /**
     * Constructs a {@code SetInterviewDeadlineCommand} with the given {@code InterviewList}.
     * <p>
     *     Registers the command word with the global command set on construction.
     * </p>
     * @param interviewList the list of interviews to operate on.
     */
    public SetInterviewDeadlineCommand(InterviewList interviewList) {
        this.interviewList = interviewList;
        Config.registerCommand(COMMAND_WORD);
    }

    /**
     * Sets the deadline of the interview at the specified index.
     * <p>
     *     Expects a 1-based integer index as the default argument and {@code /d} with a date in
     *     ISO format (yyyy-MM-dd).
     * </p>
     * @param flagToParamMap a map of flags to their parameter lists.
     * @throws GoldenCompassException if flags are invalid, the index is not a valid integer,
     *     the index is out of range, or the date format is invalid.
     */
    @Override
    public void execute(Map<String, List<String>> flagToParamMap) throws GoldenCompassException {
        checkFlag(flagToParamMap, FLAGS);

        for (Entry<String, List<String>> entry : flagToParamMap.entrySet()) {
            if (!entry.getKey().equals(DEFAULT_FLAG) && entry.getValue().size() > 1) {
                throw new GoldenCompassException("Error: Duplicate flag: " + entry.getKey());
            }
        }

        String indexParam = getParamsOf(DEFAULT_FLAG, flagToParamMap)[0].trim();
        String dateParam = getParamsOf(FLAG_DATE, flagToParamMap)[0].trim();

        int index;
        try {
            index = Integer.parseInt(indexParam);
        } catch (NumberFormatException e) {
            throw new GoldenCompassException("Error: Index must be a valid integer, got: " + indexParam);
        }

        if (!interviewList.isValidIndex(index)) {
            throw new GoldenCompassException(
                    "Error: Index " + index + " is out of range. There are " + interviewList.size() + " interview(s).");
        }

        LocalDate date;
        try {
            date = LocalDate.parse(dateParam);
        } catch (DateTimeParseException e) {
            throw new GoldenCompassException("Error: Invalid date format, expected yyyy-MM-dd, got: " + dateParam);
        }

        Interview interview = interviewList.get(index - 1);
        interview.setDate(date);

        ui.print("Deadline set for interview " + index + ": " + date);
    }
}
