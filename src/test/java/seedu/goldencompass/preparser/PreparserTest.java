package seedu.goldencompass.preparser;

import org.junit.jupiter.api.Test;
import seedu.goldencompass.exception.GoldenCompassParsingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.goldencompass.preparser.Config.DEFAULT_FLAG;

public class PreparserTest {

    @Test
    public void parse_validInput_correctCommandWord() throws GoldenCompassParsingException {
        Preparser p = new Preparser("example /a hello /b world");
        assertEquals("example", p.getCommandWord());
    }

    @Test
    public void parse_defaultFlag_capturesLeadingArgs() throws GoldenCompassParsingException {
        Preparser p = new Preparser("example hello /a world");
        assertEquals("hello", p.getFlagToParameterMap().get(DEFAULT_FLAG).get(0));
    }

    @Test
    public void parse_unknownFlag_exceptionThrown() {
        try {
            new Preparser("example /z foo");
            fail();
        } catch (GoldenCompassParsingException e) {
            assertEquals("Error: Unknown flag: /z", e.getMessage());
        }
    }

}
