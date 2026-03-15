package seedu.goldencompass.parser;

import seedu.goldencompass.exception.GoldenCompassException;

import java.util.List;
import java.util.Map;

public class Parser {

    public String command = "";
    public String companyName = "";
    public String jobDescription = "";
    public Map<String, List<String>> map;
    public Preparser preparser;


    public Parser() throws GoldenCompassException {

            this.preparser = new Preparser();

    }

    public void parse(String userInput) throws GoldenCompassException {
        preparser.preparse(userInput);
        this.command = preparser.getCommandWord();
        this.map = preparser.getFlagToParameterMap();
//        String[] words = message.split("\\s+");
//
//        command = words[0];
//
//        switch (command) {
//        case "bye", "list" -> {
//
//        }
//        case "find" -> {
//            try {
//                companyName = message.substring(5);
//            } catch (StringIndexOutOfBoundsException e) {
//                throw new GoldenCompassException("Find what?");
//            }
//        }
//        default -> throw new GoldenCompassException("Invalid command");
//        }

    }

}
