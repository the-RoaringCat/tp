package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.parser.Parser;

import java.util.logging.Logger;


public class AddAliasCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddAliasCommand.class.getName());
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
            """.stripTrailing();
    private static final String COMMAND_FLAG = "/c";
    private static final String ALIAS_FLAG = "/a";

    private final Executor executor;

    /**
     * Constructs an AddAliasCommand
     * @param parser parser
     * @param executor executor
     */
    public AddAliasCommand(Parser parser, Executor executor) {
        super(parser);
        this.executor = executor;
    }




    @Override
    protected String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    protected String getFlagDescription() {
        return FLAG_DESCRIPTION;
    }

    /**
     * Executes the command by adding an alias to an existing command
     * @throws GoldenCompassException if execution fails
     */
    @Override
    public void execute() throws GoldenCompassException {
        logger.info("Executing AddAliasCommand");

        if(parser.getFlagToParamMap().size() != PARAM_LENGTH) {
            logger.info("There are unexpected parameters.");
            throw new GoldenCompassException("Error: This command takes 2 arguments");
        }

        //validate no default param
        String defaultParam = parser.getDefaultParam();
        if(!defaultParam.isBlank()) {
            logger.info("There are unexpected parameters.");
            throw new GoldenCompassException("Error: Need to provide flag to the parameter: " + defaultParam);
        }

        //validate the correct type of params
        checkFlagPresence(COMMAND_FLAG, ALIAS_FLAG);

        String commandWord = parser.getParamsOf("/c").get(0);
        String alias = parser.getParamsOf("/a").get(0);

        executor.addAlias(commandWord, alias);
        ui.print("Command: \"" + commandWord + "\" now has a new alias: \"" + alias + "\"");

        logger.info("AddAliasCommand execution completed successfully");
    }
}
