package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.preparser.Config;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Represents a command to add a new internship to the GoldenCompass tracker.
 */
public class AddInternshipCommand implements Executable {

    //Use Config.DEFAULT_FLAG
    private static final ArrayList<String> FLAGS = new ArrayList<>(Arrays.asList(Config.DEFAULT_FLAG, "/t"));

    private final InternshipList internshipList;

    public AddInternshipCommand(InternshipList internshipList) {
        this.internshipList = internshipList;
        Config.registerFlag(FLAGS.toArray(new String[0]));
    }

    @Override
    public void execute(Map<String, List<String>> flagToParamMap) throws GoldenCompassException {

        try {
            checkFlag(flagToParamMap, FLAGS);
        } catch (GoldenCompassException e) {
            // Catch the generic error and throw error message
            throw new GoldenCompassException("Format invalid. Please key in: add COMPANY /t TITLE");
        }

        // Extract using Config.DEFAULT_FLAG
        String companyName = String.join(" ", getParamsOf(Config.DEFAULT_FLAG, flagToParamMap)).trim();
        String title = String.join(" ", getParamsOf("/t", flagToParamMap)).trim();

        if (companyName.isEmpty() || title.isEmpty()) {
            // Throw error if users leave the fields completely blank
            throw new GoldenCompassException("Inputs cannot be empty. Please key in: add COMPANY /t TITLE");
        }

        // Create the object and add it to the list
        Internship newInternship = new Internship(title, companyName);
        internshipList.add(newInternship);

        // Show success message
        System.out.println("Got it! I've added this internship to your compass:");
        System.out.println("  " + newInternship);
    }
}
