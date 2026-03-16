package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static seedu.goldencompass.parser.Config.DEFAULT_FLAG;

/**
 * All command should implement this interface in order to be executed.
 */
public interface Command {

    void execute() throws GoldenCompassException;
    /**
     * Checks for missing flag and flag not used by the command
     * @param flagToParamMap a Map
     * @param flags  a String array
     */

    default void checkFlag(Map<String, List<String>> flagToParamMap, ArrayList<String> flags)
            throws GoldenCompassException {
        //each input flag must map to a flag recognized by the command
        String[] extraFlags = flagToParamMap.keySet().stream()
                .filter(key -> !key.equals(DEFAULT_FLAG) && !flags.contains(key))
                .toArray(String[]::new);

        //each input flag must find the same flag in the input
        String[] missingFlags = flags.stream()
                .filter(key -> !flagToParamMap.containsKey(key))
                .toArray(String[]::new);

        //exception related
        StringBuilder errorMessage = new StringBuilder();

        if(extraFlags.length > 0) {
            errorMessage.append("Error: the command does not take these flag(s): ")
                    .append(String.join(", ", extraFlags))
                    .append(System.lineSeparator());
        }
        if(missingFlags. length > 0) {
            errorMessage.append("Error: there are missing flags for the command: ")
                    .append(String.join(", ", missingFlags));
        }
        if(errorMessage.length() > 0) {
            throw new GoldenCompassException(errorMessage.toString());
        }
    }

}
