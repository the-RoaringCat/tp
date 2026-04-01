package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.operation.OperationHistory;
import seedu.goldencompass.parser.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Executor {

    private final Map<String, String> aliasMap = new HashMap<>();

    private final Map<String, Executable> commands;
    private final Parser parser;
    private final Set<String> undoable=Set.of("add", "update-date", "add-interview", "alias", "remove-alias", "mark",
            "delete", "reject");


    public Executor(Parser parser, InternshipList internshipList, InterviewList interviewList,
                    OperationHistory operationHistory) {

        this.parser = parser;

        commands = Map.ofEntries(
                Map.entry("example", new ExampleCommand(parser, internshipList)),
                Map.entry("add", new AddInternshipCommand(parser, internshipList)),
                Map.entry("list", new ListCommand(internshipList)),
                Map.entry("list-interview", new ListInterviewCommand(interviewList)),
                Map.entry("update-date", new SetInterviewDeadlineCommand(parser, interviewList)),
                Map.entry("add-interview", new AddInterviewCommand(parser, internshipList, interviewList)),
                Map.entry("alias", new AddAliasCommand(parser, this)),
                Map.entry("remove-alias", new RemoveAliasCommand(parser, this)),
                Map.entry("mark", new MarkOfferCommand(parser, internshipList)),
                Map.entry("delete", new DeleteInternshipCommand(parser, internshipList)),
                Map.entry("reject", new RejectOfferCommand(parser, internshipList)),
                Map.entry("search-interview", new SearchInterviewCommand(parser, interviewList)),
                Map.entry("undo", new UndoCommand(parser, this, internshipList, interviewList,
                        operationHistory)),
                Map.entry("redo", new RedoCommand(parser, this, internshipList, interviewList,
                        operationHistory))
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
        Executable cmd = commands.get(commandWord);

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

    public void setAliasMap(Map<String, String> newAliasMap) {
        aliasMap.clear();
        aliasMap.putAll(newAliasMap);
    }

    public boolean isUndoable(String command) {
        return undoable.contains(command);
    }
}
