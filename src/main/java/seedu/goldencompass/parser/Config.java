package seedu.goldencompass.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class Config {
    public static final Set<String> ALL_FLAGS = new HashSet<>(Arrays.asList("/a", "/b", "/c", "/d"));
    public static final Set<String> ALL_COMMANDS = new HashSet<>(Arrays.asList("example"));
    public static final int COMMAND_WORD_INDEX = 0;
    public static final String FLAG_INDICATOR = "/";
    public static final String DEFAULT_FLAG = "/default";

    public static final Map<String, Set<String>> COMMAND_TO_FLAGS_MAP = new HashMap<>();

    /**
     * Registers an array of flags to a set that contains all flags of the app.
     * <P>
     *     A flag should start with {@code /}, this method does not check
     *     if the entry is valid.
     * </P>
     * <P>
     *     Duplicated flag entry is ignored.
     * </P>
     * @param flags a string array
     */
    public static void registerFlag(String... flags) {
        ALL_FLAGS.addAll(Arrays.asList(flags));
    }

    /**
     * Registers the {@code flags} to the system set.
     * @param flags
     * @see Config#registerCommandFlag(String, Set) 
     */
    private static void registerSystemFlag(Set<String> flags) {
        ALL_FLAGS.addAll(flags);
    }

    /**
     * Register the {@code commandFlags} that belong to the {@code commandKeyword} into a map.
     * <P></P>
     * <B>Side effect:</B> it also registers the {@code commandFlags} to a system level set of all flags.
     * @param commandKeyword a String
     * @param commandFlags a Set
     */
    public static void registerCommandFlag(String commandKeyword, Set<String> commandFlags) {
        commandFlags.add(DEFAULT_FLAG); //because every command implicitly has a default flag
        registerSystemFlag(commandFlags);
        COMMAND_TO_FLAGS_MAP.put(commandKeyword, commandFlags);
    }

    public static Set<String> getCommandFlags(String commandKeyword) {
        return COMMAND_TO_FLAGS_MAP.get(commandKeyword);
    }

    /**
     * Registers an array of command words to the set that contains all command words of the app.
     * <P>
     *     Duplicated command word entries are ignored.
     * </P>
     * @param commands a string array of command words to register.
     */
    public static void registerCommand(String... commands) {
        for (String command : commands) {
            ALL_COMMANDS.add(command);
        }
    }
}
