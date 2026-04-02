package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;

import java.util.List;
import java.util.logging.Logger;

/**
 * Lists all interviews in ascending order of their dates.
 */
public class ListInterviewCommand extends Command {

    private static final Logger logger = Logger.getLogger(ListInterviewCommand.class.getName());
    private static final String COMMAND_DESCRIPTION =
            "The command lists all interviews in ascending order of their dates.";
    private static final String FORMAT_DESCRIPTION = "Format: list-interview" + System.lineSeparator();

    private final InterviewList interviewList;

    /**
     * Constructs a ListInterviewCommand.
     *
     * @param interviewList The interview list to retrieve interviews from.
     */
    public ListInterviewCommand(InterviewList interviewList) {
        this.interviewList = interviewList;
    }

    /**
     * Executes the command by printing all interviews to the UI.
     *
     * @throws GoldenCompassException if command execution fails
     */
    @Override
    public void execute() throws GoldenCompassException {
        logger.info("Executing ListInterviewCommand.");

        assert ui != null : "UI must be initialized before executing command";

        List<Interview> interviews = interviewList.getInterviews();

        assert interviews != null : "Interview list returned cannot be null";

        if (interviews.isEmpty()) {
            logger.info("No interviews found in interview list.");
            ui.print("You don't have any interviews!");
            return;
        }

        logger.info("Listing " + interviews.size() + " interviews.");

        ui.print("Here are the interview invitations:");

        for (int i = 0; i < interviews.size(); i++) {
            ui.print((i + 1) + ". " + interviews.get(i).toString());
        }

        logger.info("ListInterviewCommand execution completed successfully.");
    }

    @Override
    protected String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    protected String getFlagDescription() {
        return FORMAT_DESCRIPTION;
    }

}
