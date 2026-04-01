package seedu.goldencompass.parser;

import seedu.goldencompass.exception.GoldenCompassException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Preparser {

    private String command;
    private final Set<String> flagSet;

    public Preparser(Set<String> flagSet) throws GoldenCompassException {
        this.flagSet = flagSet;
    }

    /**
     * Pre-parses a raw command message into a mapping of flags to their parameters.
     * <p>
     * The input message is split by whitespace into individual words. The first word
     * is treated as the command and stored internally in the {@code command} field.
     * Subsequent words that match entries in {@code flagSet} are treated as flags.
     * Each flag is mapped to the sequence of words that follow it until the next flag
     * or the end of the message.
     * </p>
     *
     * <p>
     * The command itself is treated as a flag at index 0, ensuring that every command
     * has at least one associated parameter entry. If no parameters follow a flag or
     * the command, an empty string is stored as its parameter.
     * </p>
     *
     * <p>
     * This method guarantees that the returned map contains an entry for the command
     * itself, even when no parameters are provided, in which case the parameter list
     * contains a single empty string.
     * </p>
     *
     * <p>
     * Example:
     * <pre>
     * Input:  "add Google SWE /date 2026-04-03"
     * Output: {
     *     "add"   -> ["Google SWE"],
     *     "/date" -> ["2026-04-03"]
     * }
     *
     * Input:  "command"
     * Output: { "command" -> [""] }
     * </pre>
     * </p>
     *
     * @param message the raw command string entered by the user
     * @return a map where each key is a command or flag and the value is a list of
     *         parameters associated with that flag
     */
    public Map<String, List<String>> preparse(String message) {

        assert message != null : "The message must not be null";

        String[] words = message.split("\\s+");

        command = words[0];

        int[] indicesOfFlags = IntStream.concat(
                IntStream.of(0),
                IntStream.range(1, words.length).filter(i -> flagSet.contains(words[i]))
        ).toArray();

        return IntStream.range(0, indicesOfFlags.length)
                .mapToObj(i -> Map.entry(
                        words[indicesOfFlags[i]],
                        String.join(" ", Arrays.copyOfRange(words, indicesOfFlags[i] + 1,
                                (i + 1 < indicesOfFlags.length) ? indicesOfFlags[i + 1] : words.length))
                ))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

    }

    public String getCommand() {
        return command;
    }

}
