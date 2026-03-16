package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.ui.Ui;

import java.util.List;
import java.util.Map;

/**
 * Represents a command to add a new internship to the GoldenCompass tracker.
 */
public class AddInternshipCommand implements Command {

    private final Ui ui;
    private final Parser parser;
    private final InternshipList internshipList;
    private Map<String, List<String>> flagToParamMap;

    public AddInternshipCommand(Parser parser, InternshipList internshipList) {
        ui = new Ui();
        this.parser = parser;
        this.internshipList = internshipList;
        this.flagToParamMap = parser.getFlagToParamMap();
    }

    @Override
    public void execute() throws GoldenCompassException {

        String companyName = parser.getParamsOf("add").get(0);
        String title = "";

        if (parser.getParamsOf("/t") != null) {
            title = String.join(" ", parser.getParamsOf("/t")).trim();
        }

        if (companyName.isEmpty() || title.isEmpty()) {
            throw new GoldenCompassException("Company name and title cannot be empty!");
        }

        Internship newInternship = new Internship(title, companyName);
        internshipList.add(newInternship);

        ui.print("Got it! I've added this internship to your compass:\n  " + newInternship);

    }

}
