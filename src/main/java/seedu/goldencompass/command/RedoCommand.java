package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.operation.OperationHistory;
import seedu.goldencompass.operation.OperationSnapshot;
import seedu.goldencompass.parser.Parser;

import java.util.logging.Logger;

public class RedoCommand extends Command implements DataHistory{
    private static final Logger logger = Logger.getLogger(RedoCommand.class.getName());

    //default
    private static final int PARAM_LENGTH = 1;
    private static final String COMMAND_DESCRIPTION = "This command can redo a command that has been undone";
    private static final String FLAG_DESCRIPTION = "Format: redo";
    private final Executor executor;
    private final InternshipList internshipList;
    private final InterviewList interviewList;
    private final OperationHistory operationHistory;

    /**
     * Constructs a RedoCommand
     * @param parser parser
     * @param executor executor
     * @param internshipList internshipList
     * @param interviewList interviewList
     * @param operationHistory operationHistory
     */
    public RedoCommand(Parser parser, Executor executor, InternshipList internshipList,
                       InterviewList interviewList, OperationHistory operationHistory) {
        super(parser);
        this.executor = executor;
        this.internshipList = internshipList;
        this.interviewList = interviewList;
        this.operationHistory = operationHistory;
    }

    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public String getFlagDescription() {
        return FLAG_DESCRIPTION;
    }

    /**
     * Executes the command by redoing an action undone
     * @throws GoldenCompassException if execution fails
     */
    @Override
    public void execute() throws GoldenCompassException {
        logger.info("Executing RedoCommand");
        if(parser.getFlagToParamMap().size() != PARAM_LENGTH || !parser.getDefaultParam().isBlank()) {
            logger.info("There are unexpected parameters");
            throw new GoldenCompassException("Error: expecting no parameters");
        }
        OperationSnapshot future = operationHistory.getRedo();

        if(future == null) {
            logger.info("No redo history");
            throw new GoldenCompassException("Error: there is no more redo history.");
        }

        modifyDataWith(future, executor, internshipList, interviewList);

        ui.print("Redo " + future.getCommand() + " successfully");
        logger.info("RedoCommand execution completed successfully");
    }
}
