package seedu.goldencompass.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.Interview;
import seedu.goldencompass.internship.InterviewList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.goldencompass.preparser.Config.DEFAULT_FLAG;

public class SetInterviewDeadlineCommandTest {

    private InterviewList interviewList;
    private SetInterviewDeadlineCommand command;

    @BeforeEach
    public void setUp() {
        interviewList = new InterviewList();
        interviewList.add(new Interview(new Internship("SWE", "Google"), LocalDate.parse("2025-01-01")));
        command = new SetInterviewDeadlineCommand(interviewList);
    }

    private Map<String, List<String>> buildMap(String index, String date) {
        Map<String, List<String>> map = new HashMap<>();
        map.put(DEFAULT_FLAG, new ArrayList<>(Arrays.asList(index)));
        map.put("/d", new ArrayList<>(Arrays.asList(date)));
        return map;
    }

    @Test
    public void execute_validInput_setsDeadline() throws GoldenCompassException {
        command.execute(buildMap("1", "2026-06-01"));
        assertEquals(LocalDate.parse("2026-06-01"), interviewList.get(0).getDate());
    }

    @Test
    public void execute_indexZero_exceptionThrown() {
        try {
            command.execute(buildMap("0", "2026-06-01"));
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index 0 is out of range. There are 1 interview(s).", e.getMessage());
        }
    }

    @Test
    public void execute_indexOutOfRange_exceptionThrown() {
        try {
            command.execute(buildMap("5", "2026-06-01"));
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index 5 is out of range. There are 1 interview(s).", e.getMessage());
        }
    }

    @Test
    public void execute_invalidIndex_exceptionThrown() {
        try {
            command.execute(buildMap("abc", "2026-06-01"));
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index must be a valid integer, got: abc", e.getMessage());
        }
    }

    @Test
    public void execute_missingIndex_exceptionThrown() {
        try {
            command.execute(buildMap("", "2026-06-01"));
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Index must be a valid integer, got: ", e.getMessage());
        }
    }

    @Test
    public void execute_duplicateFlag_exceptionThrown() {
        Map<String, List<String>> map = new HashMap<>();
        map.put(DEFAULT_FLAG, new ArrayList<>(Arrays.asList("1")));
        map.put("/d", new ArrayList<>(Arrays.asList("2026-06-01", "2026-07-01")));
        try {
            command.execute(map);
            fail();
        } catch (GoldenCompassException e) {
            assertEquals("Error: Duplicate flag: /d", e.getMessage());
        }
    }
}
