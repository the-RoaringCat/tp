package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.util.HashMap;
import java.util.Map;

public class Executor {

    private static final Map<String, String> ALIAS_MAP = new HashMap<>();

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
                "alias", new AddAliasCommand(parser),
                "mark", new MarkOfferCommand(parser, internshipList),
                "delete", new DeleteInternshipCommand(parser, internshipList)
                //"delete-interview", new DeleteInterviewCommand(parser, internshipList, interviewList)
        );

        //copy the key of commands into alias map

        commands.keySet().forEach(key -> ALIAS_MAP.put(key, key));
    }

    public void execute() throws GoldenCompassException {

        String inputAlias = parser.getCommand();
        // Check if the alias exists at all
        if (!ALIAS_MAP.containsKey(inputAlias)) {
            throw new GoldenCompassException("Error: unknown command: " + inputAlias);
        }
        String commandWord = ALIAS_MAP.get(inputAlias);
        Command cmd = commands.get(commandWord);

        if (cmd == null) {
            throw new GoldenCompassException("Error: unknown command: " + parser.getCommand());
        }

        cmd.execute();

    }

    public static void addAlias(String command, String alias) throws GoldenCompassException {
        if(ALIAS_MAP.get(command) == null) {
            throw new GoldenCompassException("Error: Cannot add alias to \"" + command + " since it does not exist.");
        }
        ALIAS_MAP.put(alias, command);
    }

    public static Map<String, String> getAliasMap() {
        return ALIAS_MAP;
    }
}
