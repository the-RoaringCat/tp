package seedu.goldencompass.command;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.operation.OperationHistory;
import seedu.goldencompass.parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddAliasCommandTest {
    private Parser parser;
    private Executor executor;
    private InternshipList internshipList;
    private InterviewList interviewList;
    private AddAliasCommand addAliasCommand;
    private OperationHistory operationHistory;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() throws GoldenCompassException {
        parser = new Parser();
        internshipList = new InternshipList();
        interviewList = new InterviewList();
        operationHistory = new OperationHistory();
        executor = new Executor(parser, internshipList, interviewList, operationHistory);
        addAliasCommand = new AddAliasCommand(parser, executor);

        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }


    @Test
    public void execute_validAlias_addsAliasSuccessfully() throws GoldenCompassException {
        parser.parse("alias /c example /a eg");
        addAliasCommand.execute();

        String output = outputStream.toString().trim();

        assertEquals("Command: \"example\" now has a new alias: \"eg\"", output);

    }
}
