package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.util.HashMap;
import java.util.Map;

public class Executor {

    private final Map<String, String> aliasMap = new HashMap<>();

    private final Map<String, Command> commands;
    private final Parser parser;



    public Executor(Parser parser, InternshipList internshipList, InterviewList interviewList) {

        this.parser = parser;

        commands = Map.of(
                "example", new ExampleCommand(parser, internshipList),
                "add", new AddInternshipCommand(parser, internshipList),
                "list", new ListCommand(internshipList),
                "list-interview", new ListInterviewCommand(interviewList),
                "update-date", new SetInterviewDeadlineCommand(parser, interviewList),
                "add-interview", new AddInterviewCommand(parser, internshipList, interviewList),
                "alias", new AddAliasCommand(parser, this),
                "remove-alias", new RemoveAliasCommand(parser, this),
                "mark", new MarkOfferCommand(parser, internshipList),
                "delete", new DeleteInternshipCommand(parser, internshipList)
                //"delete-interview", new DeleteInterviewCommand(parser, internshipList, interviewList)
        );

        //copy the key of commands into alias map

        commands.keySet().forEach(key -> aliasMap.put(key, key));
    }

    public void execute() throws GoldenCompassException {

        String inputAlias = parser.getCommand();
        // Check if the alias exists at all
        if (!aliasMap.containsKey(inputAlias)) {
            throw new GoldenCompassException("Error: unknown command: " + inputAlias);
        }
        String commandWord = aliasMap.get(inputAlias);
        Command cmd = commands.get(commandWord);

        if (cmd == null) {
            throw new GoldenCompassException("Error: unknown command: " + parser.getCommand());
        }

        cmd.execute();

    }

    public void addAlias(String command, String alias) throws GoldenCompassException {
        if(aliasMap.get(command) == null) {
            throw new GoldenCompassException("Error: Cannot add alias to \"" + command + " since it does not exist.");
        }
        if(aliasMap.containsKey(alias)) {
            throw new GoldenCompassException("Error: Alias \"" + alias + "\" already exists.");
        }
        aliasMap.put(alias, command);
    }

    public void removeAlias(String alias) throws GoldenCompassException{
        //alias does not exist
        if(!aliasMap.containsKey(alias)) {
            throw new GoldenCompassException("Error: Alias: \"" + alias +"\" does not exist.");
        }

        //cannot remove default command
        if(commands.containsKey(alias)) {
            throw new GoldenCompassException("Error: Cannot remove default command: \"" + alias +"\"");
        }

        aliasMap.remove(alias);
    }

    public Map<String, String> getAliasMap() {
        return aliasMap;
    }
}
