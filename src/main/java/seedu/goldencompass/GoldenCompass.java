package seedu.goldencompass;

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

    public GoldenCompass() {
        ui = new Ui();
        parser = new Parser();
        internships = new InternshipList();
        interviews = new InterviewList();
    }

    public static void main(String[] args) throws IOException {
        new GoldenCompass().run();
    }

    public void run() {
        ui.greet();
        ui.print("Hello " + ui.read());
    }
}
