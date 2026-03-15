package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.util.List;
import java.util.Map;

public class Executor {

    private final Map<String, Command> map;
    private Parser parser;

    public Executor(Parser parser, InternshipList internships, InterviewList interviews) {
        this.parser = parser;
        map = Map.of(
                "example", new ExampleCommand(parser, internships)
        );
    }

    public Command findCommand() throws GoldenCompassException {
        Command cmd = map.get(parser.command);
        if(cmd == null) {
            throw new GoldenCompassException("Error: unknown command: " + parser.command);
        }

        return cmd;
    }

}
