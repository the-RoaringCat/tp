package seedu.goldencompass;

import seedu.goldencompass.command.Executor;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.ui.Ui;

import java.io.IOException;

public class GoldenCompass {
    /**
     * Main entry-point for the GoldenCompass application.
     */
    private final Ui ui;
    private final Parser parser;
    private final InternshipList internships;
    private final InterviewList interviews;
    private final Executor executor;

    public GoldenCompass() throws GoldenCompassException {
        ui = new Ui();
        parser = new Parser();
        internships = new InternshipList();
        interviews = new InterviewList();
        executor = new Executor(parser, internships, interviews);
    }

    public static void main(String[] args) throws IOException, GoldenCompassException {
        new GoldenCompass().run();

    }

    public void run() {

        ui.greet();

        while (true) {
            try {
                parser.parse(ui.read());
                if (parser.getCommand().equals("bye")) {
                    break;
                }
                executor.execute();
            } catch (GoldenCompassException e) {
                ui.print(e.getMessage());
            }
        }

    }

}
