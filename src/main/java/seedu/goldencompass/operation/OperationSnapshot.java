package seedu.goldencompass.operation;


import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;

import java.util.HashMap;
import java.util.Map;

public class OperationSnapshot {
    protected String  command;
    private InternshipList internshipListCopy;
    private InterviewList interviewListCopy;
    private Map<String, String> aliasMapCopy;

    public OperationSnapshot() {
        this.command = "";
        this.internshipListCopy = new InternshipList();
        this.interviewListCopy = new InterviewList();
        this.aliasMapCopy = new HashMap<>();
    }

    public OperationSnapshot(OperationSnapshot other) {
        snapshot(other.internshipListCopy, other.interviewListCopy, other.aliasMapCopy, other.command);
    }

    public void snapshot(InternshipList internshipList, InterviewList interviewList, Map<String, String> aliasMap,
                         String command) {
        this.command = command;
        this.internshipListCopy = new InternshipList(internshipList);
        this.interviewListCopy = new InterviewList(interviewList);
        this.aliasMapCopy = new HashMap<>(aliasMap);
    }

    public InternshipList getInternshipListCopy() {
        return internshipListCopy;
    }

    public InterviewList getInterviewListCopy() {
        return interviewListCopy;
    }

    public Map<String, String> getAliasMapCopy() {
        return aliasMapCopy;
    }

    public String getCommand() {
        return this.command;
    }
}
