package seedu.goldencompass.parser;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static seedu.goldencompass.parser.Config.ALL_COMMANDS;
import static seedu.goldencompass.parser.Config.ALL_FLAGS;
import static seedu.goldencompass.parser.Config.COMMAND_WORD_INDEX;
import static seedu.goldencompass.parser.Config.DEFAULT_FLAG;
import static seedu.goldencompass.parser.Config.FLAG_INDICATOR;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.exception.GoldenCompassParsingException;

/**
 * Provides methods to parse user input.
 */
public class Preparser {
    private String commandWord;
    private Map<String, List<String>> flagToParameterMap;

    /**
     * Constructs a {@code Preparser} instance with a {@code userInput}.
     * <p></p>
     * User can get the information after parsing with getter methods.
     * @param userInput a string
     * @throws GoldenCompassParsingException if a command word or a flag is not recognizable.
     * @see Preparser#getCommandWord()
     * @see Preparser#getFlagToParameterMap()
     */
    public Preparser(String userInput) throws GoldenCompassException {
        parse(userInput);
        FlagValidator.checkFlag(flagToParameterMap.keySet(), Config.getCommandFlags(commandWord));
    }

    /**
     * Parses the userInput
     * @param userInput a string to parse
     * @throws GoldenCompassParsingException if a command word or a flag is not recognizable
     */
    private void parse(String userInput) throws GoldenCompassParsingException{
        String[] userInputs = userInput.split("\\s+");
        String commandWord = userInputs[COMMAND_WORD_INDEX];
        String[] arguments = Arrays.copyOfRange(userInputs, 1, userInputs.length);

        this.commandWord = commandWord;
        this.flagToParameterMap = findFlags(arguments);
    }

    /**
     * Returns {@code true} if {@code text} is a flag, and {@code false} if it is a parameter.
     * <p></p>
     * Any {@code text} that starts with {@code -} is expected
     * to be a flag.
     * @param text a string to be checked against.
     * @return {@code true} if {@code text} is a flag, and {@code false} if it is a parameter.
     * @throws GoldenCompassParsingException if {@code text} starts with {@code -}
     *     but not found in the Set of all flags.
     */
    private static boolean isFlag(String text) throws GoldenCompassParsingException{

        //text not starting with "-" is not a flag
        if(!text.startsWith(FLAG_INDICATOR)) {
            return false;
        }

        if(ALL_FLAGS.contains(text)) {
            return true;
        }

        //text with "-" is considered as a flag, but not found in the flag set.
        throw new GoldenCompassParsingException("Error: Unknown flag: " + text);
    }

    /**
     * Returns the {@code commandWord}
     * @param commandWord a string
     * @return the {@code commandWord}
     * @throws GoldenCompassParsingException if no command word is found.
     */
    private static String checkCommandWord(String commandWord) throws GoldenCompassParsingException{
        if(!ALL_COMMANDS.contains(commandWord)) {
            throw new GoldenCompassParsingException("Error: Command " + commandWord + " does not exist");
        }
        return commandWord;
    }

    /**
     * Returns a Map from a flag to a list of params pointed by this flag
     * @param arguments a string array
     * @return a Map from a flag to a list of params pointed by this flag
     * @throws GoldenCompassParsingException if a non-recognizable flag is detected or a parameter
     *     is not identified by any flag.
     * @see Preparser#isFlag(String)
     */
    private static Map<String, List<String>> findFlags(String[] arguments) throws GoldenCompassParsingException {
        String flag = DEFAULT_FLAG;
        ArrayList<String> params = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();

        for(String argument : arguments) {
            if(isFlag(argument)) {
                putToMap(map, flag, String.join(" ", params));
                flag = argument;
                params = new ArrayList<>();
            } else {
                //a param
                params.add(argument);
            }
        }

        putToMap(map, flag, String.join(" ", params));

        return map;
    }

    /**
     * Puts a {@code value} into a list associating with the {@code key}.
     * @param map a map from string to list of string
     * @param key a flag that is a string.
     * @param value a param that is a string.
     */
    private static void putToMap(Map<String, List<String>> map, String key, String value) {
        if(!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        List<String> params = map.get(key);
        params.add(value);
    }

    /**
     * Returns the command word identified by the preparser instance.
     * <p>
     *     <b>Warning:</b>
     *     It can only be called after a {@code Preparser} is instantiated without Exception.
     * </p>
     * @return the command word identified by the preparser instance.
     * @see Preparser#Preparser(String) the constructor
     */
    public String getCommandWord() {
        return this.commandWord;
    }

    /**
     * Returns a Map of flag to list of parameters identified by the preparser instance.
     * <p>
     *     There is a possibility of having duplicated occurrences of a flag. So parameters associated with each
     *     occurrence is stored as a {@code List<String>}, following the order in which they appear.
     * </p>
     * <p>
     *     <b>Warning:</b>
     *     It can only be called after a {@code Preparser} is instantiated without Exception.
     * </p>
     * @return a Map of flag to list of parameters identified by the preparser instance.
     * @see Preparser#Preparser(String) the constructor
     */
    public Map<String, List<String>> getFlagToParameterMap() {
        return this.flagToParameterMap;
    }
}
