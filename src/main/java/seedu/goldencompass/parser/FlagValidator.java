package seedu.goldencompass.parser;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.set_operation.SetOperation;

import java.util.Set;

public class FlagValidator {


    /**
     * Check if {@code inputFlagSet} has the same element as {@code commandFlagSet}
     * @param inputFlagSet a Set
     * @param commandFlagSet a Set
     * @throws GoldenCompassException if they are different
     */
    public static void checkFlag(Set<String> inputFlagSet, Set<String> commandFlagSet)
            throws GoldenCompassException {

        //expecting one to one between the two input sets.
        Set<String> extraFlags = SetOperation.difference(inputFlagSet, commandFlagSet);
        Set<String> missingFlags = SetOperation.difference(commandFlagSet, inputFlagSet);

        //exception related
        //the string is empty if no error.
        String errorMessage = generateErrorMessage(extraFlags, missingFlags);
        if(!errorMessage.isEmpty()) {
            throw new GoldenCompassException(errorMessage);
        }
    }

    /**
     * Generates an error message string based on the sets of extra and missing flags.
     *
     * @param extraFlags    the set of flags that are not recognized by the command
     * @param missingFlags  the set of flags that are required but not provided
     * @return              the constructed error message string, or an empty string if no errors
     */
    private static String generateErrorMessage(Set<String> extraFlags, Set<String> missingFlags) {
        StringBuilder errorMessage = new StringBuilder();

        if(!extraFlags.isEmpty()) {
            errorMessage.append("Error: the command does not take these flag(s): ")
                    .append(String.join(", ", extraFlags))
                    .append(System.lineSeparator());
        }
        if(!missingFlags.isEmpty()) {
            errorMessage.append("Error: there are missing flags for the command: ")
                    .append(String.join(", ", missingFlags));
        }

        return errorMessage.toString();
    }
}
