package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;

import java.util.Comparator;
import java.util.List;

public class ListInterviewCommand extends Command {

    private final InterviewList interviewList;

    public ListInterviewCommand(InterviewList interviewList) {
        this.interviewList = interviewList;
    }

    @Override
    public void execute() throws GoldenCompassException {

        List<Interview> interviews = interviewList.getInterviews();

        if (interviews.isEmpty()) {
            ui.print("You don't have any interviews!");
            return;
        }

        ui.print("Here are the interview invitations:");

        List<Interview> sorted_interviews = interviews.stream()
                                                      .sorted(Comparator.comparing(Interview::getDate))
                                                      .toList();

        for (int i = 0; i < sorted_interviews.size(); i++) {
            ui.print((i + 1) + ". " + sorted_interviews.get(i).toString());
        }

    }

}
