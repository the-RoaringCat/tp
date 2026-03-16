package seedu.goldencompass.parser;

import seedu.goldencompass.exception.GoldenCompassException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parser {

    private String command;
    private Map<String, List<String>> flagToParamMap;
    private final Preparser preparser;

    public Parser() throws GoldenCompassException {
        Set<String> flagSet = Set.of("/by", "/from", "/to", "/t", "/d");
        preparser = new Preparser(flagSet);
    }

    public void parse(String userInput) throws GoldenCompassException {
        flagToParamMap = preparser.preparse(userInput);
        command = preparser.getCommand();
    }

    public String getCommand() {
        return command;
    }

    public Map<String, List<String>> getFlagToParamMap() {
        return flagToParamMap;
    }

    public List<String> getParamsOf(String flag) throws GoldenCompassException {
        try {
            return flagToParamMap.get(flag);
        }  catch (NullPointerException e) {
            throw new GoldenCompassException("The flag is not found in the user input.");
        }
    }

}
