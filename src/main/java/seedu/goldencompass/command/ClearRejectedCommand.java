package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;

import java.util.List;

/**
 * Removes all internships marked as rejected from the list.
 * <p>
 * Command format: {@code clear-rejected}
 * </p>
 */
public class ClearRejectedCommand extends Command {

    public static final String COMMAND_WORD = "clear-rejected";

    private final InternshipList internshipList;

    public ClearRejectedCommand(InternshipList internshipList) {
        this.internshipList = internshipList;
    }

    @Override
    public void execute() throws GoldenCompassException {
        List<Internship> internships = internshipList.getInternships();

        List<Internship> rejected = internships.stream()
                .filter(Internship::isRejected)
                .toList();

        if (rejected.isEmpty()) {
            ui.print("No rejected internships to clear.");
            return;
        }

        internships.removeAll(rejected);

        ui.print("Cleared " + rejected.size() + " rejected internship(s):");
        for (Internship internship : rejected) {
            ui.print("  - " + internship);
        }
    }

    @Override
    protected String getCommandDescription() {
        return "";
    }

    @Override
    protected String getFlagDescription() {
        return "";
    }
}
