package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.parser.Parser;


public class AddAliasCommand extends Command {

    //default + /c + /a; total 3
    private static final int PARAM_LENGTH = 3;
    private static final String COMMAND_DESCRIPTION = """
            This command can set alias to other commands.
            Format: alias /c COMMAND /a ALIAS
            """;
    private static final String FLAG_DESCRIPTION = """
            Flags:
            /c - specifies the command keyword.
            /a - specifies the alias to that command.
            """;
    private static final String COMMAND_FLAG = "/c";
    private static final String ALIAS_FLAG = "/a";

    private final Executor executor;
    public AddAliasCommand(Parser parser, Executor executor) {
        super(parser);
        this.executor = executor;
    }




    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public String getFlagDescription() {
        return FLAG_DESCRIPTION;
    }

    @Override
    public void execute() throws GoldenCompassException {
        if(parser.getFlagToParamMap().size() != PARAM_LENGTH) {
            throw new GoldenCompassException("Error: This command takes 2 arguments");
        }

        checkFlagPresence(COMMAND_FLAG, ALIAS_FLAG);

        String commandWord = parser.getParamsOf("/c").get(0);
        String alias = parser.getParamsOf("/a").get(0);

        executor.addAlias(commandWord, alias);
        ui.print("Command: \"" + commandWord + "\" now has a new alias: \"" + alias + "\"");
    }
}
