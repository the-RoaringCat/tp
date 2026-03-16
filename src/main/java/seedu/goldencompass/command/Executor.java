package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.util.Map;

public class Executor {

    private final Map<String, Command> commands;
    private final Parser parser;

    public Executor(Parser parser, InternshipList internshipList, InterviewList interviewList) {

        this.parser = parser;

        commands = Map.of(
                "example", new ExampleCommand(parser, internshipList),
                "add", new AddInternshipCommand(parser, internshipList),
                "list", new ListCommand(internshipList),
                "list-interview", new ListInterviewCommand(interviewList),
                "set-deadline", new SetInterviewDeadlineCommand(parser, interviewList),
                "add-interview", new AddInterviewCommand(parser, internshipList, interviewList)
        );

    }

    public void execute() throws GoldenCompassException {

        Command cmd = commands.get(parser.getCommand());

        if (cmd == null) {
            throw new GoldenCompassException("Error: unknown command: " + parser.getCommand());
        }

        cmd.execute();

    }

}
