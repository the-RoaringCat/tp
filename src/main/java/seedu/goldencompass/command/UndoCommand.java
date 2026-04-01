package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.operation.OperationHistory;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.operation.OperationSnapshot;

public class UndoCommand extends Command implements DataHistory {

    //default
    private static final int PARAM_LENGTH = 1;
    private static final String COMMAND_KEYWORD = "undo";
    private final Executor executor;
    private final InternshipList internshipList;
    private final InterviewList interviewList;
    private final OperationHistory operationHistory;


    public UndoCommand(Parser parser, Executor executor, InternshipList internshipList,
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
        OperationSnapshot past = operationHistory.getUndo();

        if(past == null) {
            throw new GoldenCompassException("Error: there is no more undo history.");
        }

        modifyDataWith(past, executor, internshipList, interviewList);

        ui.print("Undo " + operationHistory.getUndoneCommand() + " successfully");
    }
}
