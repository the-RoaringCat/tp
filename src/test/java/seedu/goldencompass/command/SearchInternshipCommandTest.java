package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SearchInternshipCommandTest {

    private InternshipList internshipList;
    private Parser parser;
    private SearchInternshipCommand searchCommand;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() throws Exception {
        internshipList = new InternshipList();
        parser = new Parser();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void execute_searchByCompany_findsMatches() throws Exception {
        // Add test internships
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        internshipList.add(new Internship("Backend Developer", "Amazon"));

        parser.parse("search /c Google");
        searchCommand = new SearchInternshipCommand(parser, internshipList);
        searchCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Found 1 matching internship(s)"));
        assertTrue(output.contains("Google - Software Engineer"));
        assertFalse(output.contains("Meta"));
        assertFalse(output.contains("Amazon"));
    }

    @Test
    void execute_searchByTitle_findsMatches() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Meta"));
        internshipList.add(new Internship("Backend Engineer", "Amazon"));

        parser.parse("search /t Engineer");
        searchCommand = new SearchInternshipCommand(parser, internshipList);
        searchCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Found 2 matching internship(s)"));
        assertTrue(output.contains("Google - Software Engineer"));
        assertTrue(output.contains("Amazon - Backend Engineer"));
        assertFalse(output.contains("Meta"));
    }

    @Test
    void execute_searchByCompanyAndTitle_findsMatches() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Software Engineer", "Meta"));
        internshipList.add(new Internship("Frontend Developer", "Google"));

        parser.parse("search /c Google /t Software");
        searchCommand = new SearchInternshipCommand(parser, internshipList);
        searchCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Found 1 matching internship(s)"));
        assertTrue(output.contains("Google - Software Engineer"));
        assertFalse(output.contains("Meta"));
        assertFalse(output.contains("Frontend Developer"));
    }

    @Test
    void execute_searchCaseInsensitive_findsMatches() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Software Engineer", "MICROSOFT"));

        parser.parse("search /c microsoft");
        searchCommand = new SearchInternshipCommand(parser, internshipList);
        searchCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Found 1 matching internship(s)"));
        assertTrue(output.contains("MICROSOFT - Software Engineer"));
    }

    @Test
    void execute_searchPartialMatch_findsMatches() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Software Developer", "Google"));

        parser.parse("search /t Software");
        searchCommand = new SearchInternshipCommand(parser, internshipList);
        searchCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Found 2 matching internship(s)"));
        assertTrue(output.contains("Software Engineer"));
        assertTrue(output.contains("Software Developer"));
    }

    @Test
    void execute_noMatches_printsNoResults() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));

        parser.parse("search /c Amazon");
        searchCommand = new SearchInternshipCommand(parser, internshipList);
        searchCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("No internships found matching your search criteria"));
    }

    @Test
    void execute_emptyList_printsNoResults() throws Exception {
        parser.parse("search /c Google");
        searchCommand = new SearchInternshipCommand(parser, internshipList);
        searchCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("No internships found matching your search criteria"));
    }

    @Test
    void execute_noCriteria_throwsException() throws Exception {
        parser.parse("search");
        searchCommand = new SearchInternshipCommand(parser, internshipList);

        assertThrows(GoldenCompassException.class, () -> {
            searchCommand.execute();
        });
    }

    @Test
    void execute_emptyCompanyCriteria_throwsException() throws Exception {
        parser.parse("search /c");
        searchCommand = new SearchInternshipCommand(parser, internshipList);

        // This should treat empty criteria as no criteria and throw exception
        assertThrows(GoldenCompassException.class, () -> {
            searchCommand.execute();
        });
    }

    @Test
    void execute_emptyTitleCriteria_throwsException() throws Exception {
        parser.parse("search /t");
        searchCommand = new SearchInternshipCommand(parser, internshipList);

        assertThrows(GoldenCompassException.class, () -> {
            searchCommand.execute();
        });
    }

    @Test
    void execute_searchWithExtraWhitespace_worksCorrectly() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));

        parser.parse("search /c   Google   ");
        searchCommand = new SearchInternshipCommand(parser, internshipList);
        searchCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Found 1 matching internship(s)"));
        assertTrue(output.contains("Google - Software Engineer"));
    }

    @Test
    void execute_multipleInternshipsSameCompany_showsAll() throws Exception {
        internshipList.add(new Internship("Software Engineer", "Google"));
        internshipList.add(new Internship("Frontend Developer", "Google"));
        internshipList.add(new Internship("Data Analyst", "Google"));

        parser.parse("search /c Google");
        searchCommand = new SearchInternshipCommand(parser, internshipList);
        searchCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Found 3 matching internship(s)"));
        assertTrue(output.contains("Software Engineer"));
        assertTrue(output.contains("Frontend Developer"));
        assertTrue(output.contains("Data Analyst"));
    }
}
