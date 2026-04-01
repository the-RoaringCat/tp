package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.parser.Parser;

public class RemoveAliasCommand extends Command {
    //default
    private static final int PARAM_LENGTH = 1;
    private static final String KEYWORD = "remove-alias";
    private final Executor executor;

    public RemoveAliasCommand(Parser parser, Executor executor) {
        super(parser);
        this.executor = executor;
    }

    @Override
    public void execute() throws GoldenCompassException {
        if(parser.getFlagToParamMap().size() != PARAM_LENGTH) {
            throw new GoldenCompassException("Error: Expecting only one argument.");
        }
        String alias = parser.getParamsOf(KEYWORD).get(0);

        executor.removeAlias(alias);
        ui.print("Alias: \"" + alias + "\" is now removed.");
    }
}
