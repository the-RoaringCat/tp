package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.operation.OperationHistory;
import seedu.goldencompass.operation.OperationSnapshot;
import seedu.goldencompass.parser.Parser;

public class RedoCommand extends Command implements DataHistory{
    //default
    private static final int PARAM_LENGTH = 1;
    private static final String COMMAND_KEYWORD = "redo";
    private final Executor executor;
    private final InternshipList internshipList;
    private final InterviewList interviewList;
    private final OperationHistory operationHistory;

    public RedoCommand(Parser parser, Executor executor, InternshipList internshipList,
                       InterviewList interviewList, OperationHistory operationHistory) {
        super(parser);
        this.executor = executor;
        this.internshipList = internshipList;
        this.interviewList = interviewList;
        this.operationHistory = operationHistory;
    }

    @Override
    public void execute() throws GoldenCompassException {
        if(parser.getFlagToParamMap().size() != PARAM_LENGTH) {
            throw new GoldenCompassException("Error: expecting no variable");
        }
        OperationSnapshot future = operationHistory.getRedo();

        if(future == null) {
            throw new GoldenCompassException("Error: there is no more redo history.");
        }

        modifyDataWith(future, executor, internshipList, interviewList);

        ui.print("Redo " + future.getCommand() + " successfully");
    }
}
