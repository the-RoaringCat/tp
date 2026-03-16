package seedu.goldencompass.command;

import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.ui.Ui;

import java.util.List;
import java.util.Map;

public class ExampleCommand implements Command {

    private final Ui ui;
    private final Parser parser;
    private final InternshipList internships;

    public ExampleCommand(Parser parser, InternshipList internships) {
        ui = new Ui();
        this.parser = parser;
        this.internships = internships;
    }

    @Override
    public void execute() throws GoldenCompassException {

        Map<String, List<String>> flagToParamMap = parser.getFlagToParamMap();

        ui.print("hi, I am ExampleCommand");

        for(String key : flagToParamMap.keySet()) {
            List<String> params = parser.getParamsOf(key);
            ui.print("I have this flag " + key + " with params: " + params);
        }

    }
}
