package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.util.HashMap;
import java.util.Map;

public class Executor {

    private final static Map<String, String> aliasToCommandWordMap = new HashMap<>();

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
                "add-interview", new AddInterviewCommand(parser, internshipList, interviewList),
                "alias", new AddAliasCommand(parser)
        );

        //copy the key of commands into alias map

        commands.keySet().forEach(key -> aliasToCommandWordMap.put(key, key));
    }

    public void execute() throws GoldenCompassException {

        String inputAlias = parser.getCommand();
        String commandWord = aliasToCommandWordMap.get(inputAlias);
        Command cmd = commands.get(commandWord);

        if (cmd == null) {
            throw new GoldenCompassException("Error: unknown command: " + parser.getCommand());
        }

        cmd.execute();

    }

    public static void addAlias(String command, String alias) throws GoldenCompassException {
        if(aliasToCommandWordMap.get(command) == null) {
            throw new GoldenCompassException("Error: Cannot add alias to \"" + command + " since it does not exist.");
        }
        aliasToCommandWordMap.put(alias, command);
    }
}
