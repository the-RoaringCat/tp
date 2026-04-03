package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearRejectedCommandTest {

    private InternshipList internshipList;
    private InterviewList interviewList;
    private ClearRejectedCommand clearRejectedCommand;

    @BeforeEach
    public void setUp() {
        internshipList = new InternshipList();
        interviewList = new InterviewList();
        clearRejectedCommand = new ClearRejectedCommand(internshipList, interviewList);
    }

    @Test
    public void execute_noRejected_printsNoRejected()
            throws GoldenCompassException {
        internshipList.add(new Internship("SWE", "Google"));
        internshipList.add(new Internship("DS", "Meta"));

        clearRejectedCommand.execute();
        assertEquals(2, internshipList.getSize());
    }

    @Test
    public void execute_allRejected_clearsAll()
            throws GoldenCompassException {
        Internship google = new Internship("SWE", "Google");
        Internship meta = new Internship("DS", "Meta");
        google.markAsRejected();
        meta.markAsRejected();
        internshipList.add(google);
        internshipList.add(meta);

        clearRejectedCommand.execute();
        assertEquals(0, internshipList.getSize());
    }

    @Test
    public void execute_someRejected_clearsOnlyRejected()
            throws GoldenCompassException {
        Internship google = new Internship("SWE", "Google");
        Internship meta = new Internship("DS", "Meta");
        Internship amazon = new Internship("BE", "Amazon");
        google.markAsRejected();
        internshipList.add(google);
        internshipList.add(meta);
        internshipList.add(amazon);

        clearRejectedCommand.execute();
        assertEquals(2, internshipList.getSize());
        assertEquals("Meta", internshipList.get(0).getCompanyName());
        assertEquals("Amazon", internshipList.get(1).getCompanyName());
    }

    @Test
    public void execute_emptyList_printsNoRejected()
            throws GoldenCompassException {
        clearRejectedCommand.execute();
        assertEquals(0, internshipList.getSize());
    }
}
