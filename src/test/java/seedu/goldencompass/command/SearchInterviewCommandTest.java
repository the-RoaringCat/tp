package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SearchInterviewCommandTest {

    private Parser parser;
    private InterviewList interviewList;
    private SearchInterviewCommand searchCommand;

    @BeforeEach
    public void setUp() throws GoldenCompassException {
        parser = new Parser();
        interviewList = new InterviewList();
        searchCommand = new SearchInterviewCommand(parser, interviewList);

        Internship google = new Internship("Software Engineer", "Google");
        Interview googleInterview = new Interview(google, LocalDateTime.parse("2028-06-15T10:00"));
        interviewList.add(googleInterview);

        Internship meta = new Internship("Data Scientist", "Meta");
        Interview metaInterview = new Interview(meta, LocalDateTime.parse("2028-07-20T14:00"));
        interviewList.add(metaInterview);

        Internship googleTwo = new Internship("Product Manager", "Google");
        Interview googleTwoInterview = new Interview(googleTwo, LocalDateTime.parse("2028-07-20T14:00"));
        interviewList.add(googleTwoInterview);
    }

    @Test
    public void execute_searchByCompany_findsMatches() throws GoldenCompassException {
        parser.parse("search-interview /c Google");
        searchCommand.execute();
        // Should find 2 Google interviews - no exception thrown means success
    }

    @Test
    public void execute_searchByCompanyCaseInsensitive_findsMatches() throws GoldenCompassException {
        parser.parse("search-interview /c google");
        searchCommand.execute();
    }

    @Test
    public void execute_searchByTitle_findsMatches() throws GoldenCompassException {
        parser.parse("search-interview /t Engineer");
        searchCommand.execute();
    }

    @Test
    public void execute_searchByDate_findsMatches() throws GoldenCompassException {
        parser.parse("search-interview /d 2028-07-20");
        searchCommand.execute();
    }

    @Test
    public void execute_searchByCompanyAndTitle_findsMatches() throws GoldenCompassException {
        parser.parse("search-interview /c Google /t Product");
        searchCommand.execute();
    }

    @Test
    public void execute_searchByCompanyAndDate_findsMatches() throws GoldenCompassException {
        parser.parse("search-interview /c Google /d 2028-06-15");
        searchCommand.execute();
    }

    @Test
    public void execute_noMatchingResults_printsNoResults() throws GoldenCompassException {
        parser.parse("search-interview /c Amazon");
        searchCommand.execute();
    }

    @Test
    public void execute_noFlagsProvided_exceptionThrown() throws GoldenCompassException {
        parser.parse("search-interview");
        try {
            searchCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Please provide at least one search flag. "
                    + "Usage: search-interview [/c COMPANY] [/t ROLE] [/d DATE]", e.getMessage());
        }
    }

    @Test
    public void execute_invalidDateFormat_exceptionThrown() throws GoldenCompassException {
        parser.parse("search-interview /d not-a-date");
        try {
            searchCommand.execute();
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Invalid date format, expected yyyy-MM-dd, got: not-a-date",
                    e.getMessage());
        }
    }

    @Test
    public void execute_emptyList_printsNoResults() throws GoldenCompassException {
        InterviewList emptyList = new InterviewList();
        SearchInterviewCommand emptySearch = new SearchInterviewCommand(parser, emptyList);
        parser.parse("search-interview /c Google");
        emptySearch.execute();
    }
}
