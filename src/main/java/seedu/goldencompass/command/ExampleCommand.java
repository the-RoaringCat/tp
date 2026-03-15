package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.preparser.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class ExampleCommand implements Command {

    private static final ArrayList<String> FLAGS = new ArrayList<>(Arrays.asList("/eg", "/EG"));
    private final Parser parser;
    private final List<Internship> internships;

    public ExampleCommand(Parser parser, List<Internship> internships) {
        this.parser = parser;
        this.internships = internships;
        Config.registerFlag(FLAGS.toArray(new String[0]));
    }

    /**
     * Prints a list of legal flags with their respective parameters.
     * <P><B>Example Use</B></P>
     * @param flagToParamMap
     */
    @Override
    public void execute(Map<String, List<String>> flagToParamMap) throws GoldenCompassException {
        checkFlag(flagToParamMap, FLAGS);
        System.out.println("hi, I am ExampleCommand");
        for(String key : flagToParamMap.keySet()) {
            String[] params = getParamsOf(key, flagToParamMap);
            System.out.println("I have this flag " + key + " with params: " + Arrays.toString(params));
        }
    }
}
