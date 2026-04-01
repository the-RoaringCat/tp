package seedu.goldencompass.command;

import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.operation.OperationSnapshot;

public interface DataHistory {
    default void modifyDataWith(OperationSnapshot operationSnapshot, Executor executor,
                                InternshipList internshipList, InterviewList interviewList) {
        assert operationSnapshot != null : "operationSnapshot cannot be null";
        assert executor != null : "executor cannot be null";
        assert internshipList != null : "internshipList cannot be null";
        assert interviewList != null : "interviewList cannot be null";

        executor.setAliasMap(operationSnapshot.getAliasMapCopy());
        internshipList.setInternships(operationSnapshot.getInternshipListCopy().getInternships());
        interviewList.setInterviews(operationSnapshot.getInterviewListCopy().getInterviews());
    }
}
