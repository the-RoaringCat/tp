package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.operation.OperationHistory;
import seedu.goldencompass.parser.Parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Executor {
    private static final Set<String> UNDOABLE=Set.of("add", "update-date", "add-interview", "alias",
            "remove-alias", "mark", "delete", "delete-interview", "reject", "clear-rejected");

    private final Map<String, String> aliasMap = new HashMap<>();

    private final Map<String, Command> commands;
    private final Parser parser;
    private final Set<String> undoable;


    public Executor(Parser parser, InternshipList internshipList, InterviewList interviewList,
                    OperationHistory operationHistory) {

        this.parser = parser;

        commands = Map.ofEntries(
                Map.entry("example", new ExampleCommand(parser, internshipList)),
                Map.entry("add", new AddInternshipCommand(parser, internshipList)),
                Map.entry("list", new ListCommand(parser, internshipList)),
                Map.entry("list-interview", new ListInterviewCommand(interviewList)),
                Map.entry("update-date", new SetInterviewDeadlineCommand(parser, interviewList)),
                Map.entry("add-interview", new AddInterviewCommand(parser, internshipList, interviewList)),
                Map.entry("alias", new AddAliasCommand(parser, this)),
                Map.entry("remove-alias", new RemoveAliasCommand(parser, this)),
                Map.entry("mark", new MarkOfferCommand(parser, internshipList)),
                Map.entry("delete", new DeleteInternshipCommand(parser, internshipList, interviewList)),
                Map.entry("delete-interview", new DeleteInterviewCommand(parser, internshipList, interviewList)),
                Map.entry("reject", new RejectOfferCommand(parser, internshipList)),
                Map.entry("clear-rejected", new ClearRejectedCommand(internshipList, interviewList)),
                Map.entry("search", new SearchInternshipCommand(parser, internshipList)),
                Map.entry("search-interview", new SearchInterviewCommand(parser, interviewList)),
                Map.entry("upcoming", new UpcomingCommand(parser, interviewList)),
                Map.entry("undo", new UndoCommand(parser, this, internshipList, interviewList,
                        operationHistory)),
                Map.entry("redo", new RedoCommand(parser, this, internshipList, interviewList,
                        operationHistory))
        );

        //copy the key of commands into alias map
        commands.keySet().forEach(key -> aliasMap.put(key, key));

        //copy the set of undoable command into set of undoable alias
        undoable = new HashSet<>(UNDOABLE);
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

        if (parser.isFlagExist("/help")) {
            cmd.printHelp();
            return;
        }

        cmd.execute();
    }

    public void addAlias(String command, String alias) throws GoldenCompassException {
        command = command.trim();
        alias = alias.trim();

        //alias is empty
        if(alias.isEmpty()) {
            throw new GoldenCompassException("Error: Cannot add blank alias");
        }

        //command is not found
        if(aliasMap.get(command) == null) {
            throw new GoldenCompassException("Error: Cannot add alias to \"" + command + "\" since it does not exist.");
        }

        //alias is already registered
        if(aliasMap.containsKey(alias)) {
            throw new GoldenCompassException("Error: Alias \"" + alias + "\" already exists.");
        }

        //register that alias
        aliasMap.put(alias, command);

        //register the alias as undoable if it is
        if(UNDOABLE.contains(command)) {
            undoable.add(alias);
        }
    }

    public void removeAlias(String alias) throws GoldenCompassException{
        alias = alias.trim();

        //alias is empty
        if(alias.isEmpty()) {
            throw new GoldenCompassException("Error: Cannot remove blank alias");
        }

        //alias does not exist
        if (!aliasMap.containsKey(alias)) {
            throw new GoldenCompassException("Error: Alias: \"" + alias +"\" does not exist.");
        }

        //alias is default command
        if(commands.containsKey(alias)) {
            throw new GoldenCompassException("Error: Cannot remove default command: \"" + alias +"\"");
        }

        //remove the alias from registry
        aliasMap.remove(alias);

        //remove the alias from undoable registry if it is undoable
        undoable.remove(alias);
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
