package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.parser.Parser;

import java.util.logging.Logger;

public class RemoveAliasCommand extends Command {
    private static final Logger logger = Logger.getLogger(RemoveAliasCommand.class.getName());

    //default
    private static final int PARAM_LENGTH = 1;
    private static final String COMMAND_DESCRIPTION = """
            This command can remove an alias.
            Format: remove-alias ALIAS
            """;
    private static final String FLAG_DESCRIPTION = """
            Flags:
            This command only takes one parameter, no flag required.
            """.stripTrailing();

    private final Executor executor;

    /**
     * Constructs a RemoveAliasCommand
     * @param parser parser
     * @param executor executor
     */
    public RemoveAliasCommand(Parser parser, Executor executor) {
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

    /**
     * Executes the command by removing an existing alias
     * @throws GoldenCompassException if execution fails
     */
    @Override
    public void execute() throws GoldenCompassException {
        logger.info("Executing RemoveAliasCommand");

        if(parser.getFlagToParamMap().size() != PARAM_LENGTH) {
            logger.info("There are missing or unexpected parameters.");
            throw new GoldenCompassException("Error: Expecting only one argument.");
        }

        String alias = parser.getDefaultParam();

        executor.removeAlias(alias);
        ui.print("Alias: \"" + alias + "\" is now removed.");

        logger.info("Executing RemoveAliasCommand successfully.");
    }
}
