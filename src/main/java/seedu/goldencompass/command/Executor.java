package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.util.Map;

public class Executor {

    private final Map<String, Command> commands;
    private Parser parser;

    public Executor(Parser parser, InternshipList internships, InterviewList interviews) {
        this.parser = parser;
        commands = Map.of(
                "example", new ExampleCommand(parser, internships)
        );
    }

    public Command findCommand() throws GoldenCompassException {
        Command cmd = commands.get(parser.command);
        if(cmd == null) {
            throw new GoldenCompassException("Error: unknown command: " + parser.command);
        }

        return cmd;
    }
}
