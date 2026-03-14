package seedu.goldencompass;

import seedu.goldencompass.command.ExampleCommand;
import seedu.goldencompass.command.CommandRegistry;
import seedu.goldencompass.command.Executable;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.preparser.Preparser;
import seedu.goldencompass.ui.Ui;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GoldenCompass {
    /**
     * Main entry-point for the GoldenCompass application.
     */
    private final Ui ui;
    private final Parser parser;
    private final InternshipList internships;
    private final InterviewList interviews;

    public GoldenCompass() {
        ui = new Ui();
        parser = new Parser();
        internships = new InternshipList();
        interviews = new InterviewList();
    }

    public static void main(String[] args) throws IOException {
        new GoldenCompass().run();
        //new GoldenCompass().testCommand();

    }

    public void run() {
        ui.greet();
        ui.print("Hello " + ui.read());
    }

    public void testCommand() {

        //register an example command for testing
        CommandRegistry.registerCommand("example", new ExampleCommand());

        //loop for input
        while(true) {
            try {
                //type: example default params /a param a /b param b0 /b param b1 /c
                Preparser pp = new Preparser(ui.read());

                //pp attributes
                String commandWord = pp.getCommandWord();
                Map<String, List<String>> map = pp.getFlagToParameterMap();

                //find the matching executable and execute it
                Executable executable = CommandRegistry.getCommand(commandWord);
                executable.execute(map);
            } catch (GoldenCompassException e) {
                ui.print(e.getMessage());
            }


        }
    }
}
