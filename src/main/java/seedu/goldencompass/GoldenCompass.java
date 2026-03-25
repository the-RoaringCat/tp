package seedu.goldencompass;

import seedu.goldencompass.command.Executor;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.storage.AliasStorage;
import seedu.goldencompass.ui.Ui;
import seedu.goldencompass.storage.InternshipStorage;
import seedu.goldencompass.storage.InterviewStorage;

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
    private final InternshipStorage internshipStorage;
    private final InterviewStorage interviewStorage;
    private final AliasStorage aliasStorage;

    public GoldenCompass() throws GoldenCompassException {
        ui = new Ui();
        parser = new Parser();
        internshipStorage = new InternshipStorage("data/internships.txt");
        internships = new InternshipList(internshipStorage.load());
        internships.setUi(ui);
        this.interviewStorage = new InterviewStorage("data/interviews.txt");
        this.interviews = new InterviewList();
        this.interviewStorage.load(interviews, internships);
        this.aliasStorage = new AliasStorage("data/aliases.txt");
        this.aliasStorage.load(Executor.getAliasMap());
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
                internshipStorage.save(internships);
                interviewStorage.save(interviews);
                aliasStorage.save(Executor.getAliasMap());
            } catch (GoldenCompassException e) {
                ui.print(e.getMessage());
            }
        }

    }

}
