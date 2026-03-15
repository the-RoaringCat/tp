package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.parser.Config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class ExampleCommand implements Command {


    private final Parser parser;
    private final InternshipList internships;
    private static final String COMMAND_KEY_WORD = "example";
    private static final Set<String> COMMAND_FLAGS = new HashSet<>(Arrays.asList("/eg", "/EG"));

    public ExampleCommand(Parser parser, InternshipList internships) {
        this.parser = parser;
        this.internships = internships;
        Config.registerCommandFlag(COMMAND_KEY_WORD, COMMAND_FLAGS);
        CommandRegistry.registerCommand(COMMAND_KEY_WORD, this); //self register
    }

    /**
     * Prints a list of legal flags with their respective parameters.
     * <P><B>Example Use</B></P>
     * @param flagToParamMap a Map
     */
    @Override
    public void execute(Map<String, List<String>> flagToParamMap) {
        System.out.println("hi, I am ExampleCommand");
        for(String key : flagToParamMap.keySet()) {
            String[] params = getParamsOf(key, flagToParamMap);
            System.out.println("I have this flag " + key + " with params: " + Arrays.toString(params));
        }
    }
}
