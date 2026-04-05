package seedu.goldencompass.parser;

import seedu.goldencompass.exception.GoldenCompassException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a parser that parses user inputs into a flag-to-param map.
 *
 * <p>
 * The parser splits a user input string into tokens separated by whitespace.
 * The first token is treated as the command, while recognized flags (defined in
 * {@code FLAGSET}) are mapped to their associated parameters.
 * </p>
 *
 * <p>
 * Example:
 * <pre>
 * Input:  "add Google SWE /d 2026-04-03"
 * Output:
 * command = "add"
 * flagToParamMap = {
 *     "add" -> ["Google SWE"],
 *     "/d"  -> ["2026-04-03"]
 * }
 * </pre>
 * </p>
 */
public class Parser {

    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    /** Set of valid flags recognized by the parser. */
    private static final Set<String> FLAGSET =
            Set.of("/t", "/d", "/c", "/a", "/help");

    private String command;
    private Map<String, List<String>> flagToParamMap;

    /**
     * Parses a raw user input into a mapping of flags to their parameters.
     *
     * <p>
     * The first word is treated as the command and stored internally.
     * Subsequent recognized flags are mapped to their parameters. In case of
     * an empty input "", the command is parsed as "" with value [""].
     * </p>
     *
     * <p>
     * This method guarantees that the returned map contains an entry for each flag
     * and the command itself, even when no parameters are provided, in which case
     * the value in the map is [""], i.e., a list of a single empty string.
     * </p>
     *
     * <p>
     * That the value in the map is a list of strings is to address the case of
     * duplicated flags, such that associated with each flag there are multiple
     * parameter strings.
     * </p>
     *
     * @param userInput the raw command string entered by the user
     * @throws GoldenCompassException if the input is empty or cannot be parsed
     */
    public void parse(String userInput) throws GoldenCompassException {

        assert userInput != null : "The user input must not be null, at least an empty string.";
        logger.log(Level.FINE, "Parsing user input: {0}", userInput);

        String[] words = userInput.trim().split("\\s+");
        assert words.length > 0 : "Split words should not be empty, at least one empty string.";

        command = words[0];
        logger.log(Level.FINE, "Detected command: {0}", command);

        int[] indicesOfFlags = IntStream.concat(
                IntStream.of(0),
                IntStream.range(1, words.length).filter(i -> FLAGSET.contains(words[i]))
        ).toArray();

        flagToParamMap = IntStream.range(0, indicesOfFlags.length)
                .mapToObj(i -> Map.entry(
                        words[indicesOfFlags[i]],
                        String.join(
                                " ", Arrays.copyOfRange(
                                    words,
                                    indicesOfFlags[i] + 1,
                                    (i + 1 < indicesOfFlags.length) ? indicesOfFlags[i + 1] : words.length
                                )
                        )
                ))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        assert command != null : "Command should be initialized after parsing";
        assert flagToParamMap != null : "Flag to parameter map should be initialized";

        logger.log(Level.FINE, "Parsing complete. Command & Flags detected: {0}",
                flagToParamMap.keySet());
    }

    /**
     * Returns the parsed command.
     *
     * @return the command string
     */
    public String getCommand() {
        assert command != null : "parse() must be called before getCommand()";
        return command;
    }

    /**
     * Returns the mapping of flags to parameters.
     *
     * @return map of flags to parameter lists
     */
    public Map<String, List<String>> getFlagToParamMap() {
        assert flagToParamMap != null : "parse() must be called before accessing flag map";
        return flagToParamMap;
    }

    /**
     * Returns the list of parameters associated with a specific flag.
     *
     * @param flag the flag or command string to retrieve parameters for
     * @return null or list of parameters associated with the flag
     */
    public List<String> getParamsOf(String flag) {
        assert flag != null : "Flag must not be null";
        assert flagToParamMap != null : "parse() must be called before getParamsOf()";
        return flagToParamMap.get(flag);
    }

    /**
     * Checks whether a flag exists in the parsed input.
     *
     * @param flag the flag to check
     * @return true if the flag exists, false otherwise
     */
    public boolean isFlagExist(String flag) {
        assert flag != null : "Flag must not be null";
        return flagToParamMap.containsKey(flag);
    }

    public String getDefaultParam() {
        return getParamsOf(command).get(0);
    }
}
