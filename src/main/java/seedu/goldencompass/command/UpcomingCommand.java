package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command that lists upcoming interviews within a specified number of days.
 * <p>
 * The command retrieves the number of days from the parser parameters associated
 * with the "upcoming" flag. If no parameter is provided, a default value of 5 days
 * is used. The command then filters interviews whose dates fall within the range
 * from the current system date to the specified future date and prints them to the UI.
 * </p>
 *
 * <p>
 * Example usage:
 * <ul>
 *     <li>upcoming → shows interviews in the next 5 days</li>
 *     <li>upcoming 3 → shows interviews in the next 3 days</li>
 * </ul>
 * </p>
 */
public class UpcomingCommand extends Command {

    private static final Logger logger = Logger.getLogger(UpcomingCommand.class.getName());
    private static final int DEFAULT_DAYS = 5;
    private static final String COMMAND_DESCRIPTION =
            "The command lists upcoming interviews within a specified number of days.";
    private static final String FORMAT_DESCRIPTION = "Command format: upcoming [days]" + System.lineSeparator()
            + "If the optional parameter [days] is not supplied, a default of 5 days will be used."
            + System.lineSeparator() + "Example usage:" + System.lineSeparator()
            + "\tupcoming → shows interviews in the next 5 days" + System.lineSeparator()
            + "\tupcoming 3 → shows interviews in the next 3 days";

    private final InterviewList interviewList;

    public UpcomingCommand(Parser parser, InterviewList interviewList) {

        assert parser != null : "Parser must not be null";
        assert interviewList != null : "InterviewList must not be null";

        this.parser = parser;
        this.interviewList = interviewList;

    }

    @Override
    public void execute() throws GoldenCompassException {
        logger.log(Level.INFO, "Executing UpcomingCommand");

        assert !parser.getCommand().isBlank(): "Command should not be empty string";
        assert parser.getParamsOf(parser.getCommand()) != null :
                "Even if no parameter is supplied to the command word, getParamsOf() should not return null";

        int days = DEFAULT_DAYS;

        String param = parser.getParamsOf(parser.getCommand()).get(0);

        assert param != null : "The values of the returned map should not be null";

        if (!param.isBlank()) {
            try {
                days = Integer.parseInt(param);
                logger.log(Level.INFO, "UpcomingCommand parameter days set to: {0}", days);
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Invalid upcoming parameter: {0}", param);
                throw new GoldenCompassException("The parameter of the upcoming command must be an integer");
            }
        } else {
            logger.log(Level.INFO, "No parameter provided. Using default days: {0}", DEFAULT_DAYS);
        }

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime limit = now.plusDays(days);

        logger.log(Level.INFO, "Filtering interviews from {0} to {1}", new Object[]{now, limit});

        List<Interview> interviews = interviewList.getInterviews();

        assert interviews != null : "The List of interviews should not be null";

        List<Interview> filteredInterviews = interviews.stream()
                                                       .filter(i -> {
                                                           assert i != null : "Interview should not be null";
                                                           assert i.getDate() != null :
                                                                   "Interview date should not be null";
                                                           return !i.getDate().isBefore(now);
                                                       })
                                                       .filter(i -> !i.getDate().isAfter(limit))
                                                       .toList();

        assert filteredInterviews.size() <= interviews.size() :
                "Filtered list cannot be larger than original list";

        if (filteredInterviews.isEmpty()) {
            ui.print("You don't have any upcoming interviews.");
        } else {
            filteredInterviews.forEach(i -> ui.print(i.toString()));
        }

        logger.log(Level.INFO, "UpcomingCommand execution completed");
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
