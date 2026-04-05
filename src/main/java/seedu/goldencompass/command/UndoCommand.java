package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.operation.OperationHistory;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.operation.OperationSnapshot;

import java.util.logging.Logger;

public class UndoCommand extends Command implements DataHistory {
    private static final Logger logger = Logger.getLogger(UndoCommand.class.getName());

    //default
    private static final int PARAM_LENGTH = 1;
    private static final String COMMAND_DESCRIPTION = "This command can undo a command that modifies the data.";
    private static final String FLAG_DESCRIPTION = "Format: undo";
    private final Executor executor;
    private final InternshipList internshipList;
    private final InterviewList interviewList;
    private final OperationHistory operationHistory;

    /**
     * Constructs an UndoCommand
     * @param parser parser
     * @param executor executor
     * @param internshipList internshipList
     * @param interviewList interviewList
     * @param operationHistory operationHistory
     */
    public UndoCommand(Parser parser, Executor executor, InternshipList internshipList,
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
     * Executes the command by undoing an action
     * @throws GoldenCompassException if execution fails
     */
    @Override
    public void execute() throws GoldenCompassException {
        if(parser.getFlagToParamMap().size() != PARAM_LENGTH || !parser.getDefaultParam().isBlank()) {
            logger.info("There are unexpected parameters");
            throw new GoldenCompassException("Error: expecting no parameters");
        }
        OperationSnapshot past = operationHistory.getUndo();

        if(past == null) {
            logger.info("No undo history");
            throw new GoldenCompassException("Error: there is no more undo history.");
        }

        modifyDataWith(past, executor, internshipList, interviewList);

        ui.print("Undo " + operationHistory.getUndoneCommand() + " successfully");
        logger.info("UndoCommand execution completed successfully");
    }
}
