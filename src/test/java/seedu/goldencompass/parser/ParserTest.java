package seedu.goldencompass.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    private Parser parser;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void parse_emptyString_singleMapEntryFromAnEmptyString() throws GoldenCompassException {
        parser.parse("");
        assertEquals("", parser.getCommand());
        assertEquals(1, parser.getFlagToParamMap().size());
        assertEquals("", parser.getParamsOf(parser.getCommand()).get(0));
    }

    @Test
    public void parse_commandWithoutParamsWithoutFlags_parseCorrectly() throws GoldenCompassException {
        parser.parse("command");
        assertEquals("command", parser.getCommand());
        assertEquals(1, parser.getFlagToParamMap().size());
        assertEquals("", parser.getParamsOf(parser.getCommand()).get(0));
    }

    @Test
    public void parse_commandWithParamsWithoutFlags_parseCorrectly() throws GoldenCompassException {
        parser.parse("command1 abc xyz");
        assertEquals("command1", parser.getCommand());
        assertEquals(1, parser.getFlagToParamMap().size());
        assertEquals("abc xyz", parser.getParamsOf(parser.getCommand()).get(0));
    }

    @Test
    public void parse_commandWithParamsWithFlags_parseCorrectly() throws GoldenCompassException {
        parser.parse("command2 xyz /t tt t /a aa a");
        assertEquals("command2", parser.getCommand());
        assertEquals(3, parser.getFlagToParamMap().size());
        assertEquals("xyz", parser.getParamsOf(parser.getCommand()).get(0));
        assertEquals("tt t", parser.getParamsOf("/t").get(0));
        assertEquals("aa a", parser.getParamsOf("/a").get(0));
    }

    @Test
    public void parse_commandWithParamsWithDuplicatedFlags_parseCorrectly() throws GoldenCompassException {
        parser.parse("command3 xyz /t tt t /t aa a");
        assertEquals("command3", parser.getCommand());
        assertEquals(2, parser.getFlagToParamMap().size());
        assertEquals("xyz", parser.getParamsOf(parser.getCommand()).get(0));
        assertEquals(2, parser.getParamsOf("/t").size());
        assertEquals("tt t", parser.getParamsOf("/t").get(0));
        assertEquals("aa a", parser.getParamsOf("/t").get(1));
    }

}
