package seedu.goldencompass.parser;

import seedu.goldencompass.exception.GoldenCompassException;

public class Parser {

    public String command = "";
    public String companyName = "";
    public String jobDescription = "";

    public void parse(String message) throws GoldenCompassException {

        String[] words = message.split("\\s+");

        command = words[0];

        switch (command) {
        case "bye", "list" -> {

        }
        case "find" -> {
            try {
                companyName = message.substring(5);
            } catch (StringIndexOutOfBoundsException e) {
                throw new GoldenCompassException("Find what?");
            }
        }
        case "add" -> {
            try {
                int cIndex = message.indexOf("c/");
                int dIndex = message.indexOf("d/");

                if (cIndex == -1 || dIndex == -1) {
                    throw new GoldenCompassException("Invalid format! Use: add c/COMPANY d/DESCRIPTION");
                }

                // Extracting the text between 'c/' and 'd/'
                companyName = message.substring(cIndex + 2, dIndex).trim();
                // Extracting the text after 'd/'
                jobDescription = message.substring(dIndex + 2).trim();

            } catch (StringIndexOutOfBoundsException e) {
                throw new GoldenCompassException("Oops! Make sure your company and description aren't empty.");
            }
        }
        default -> throw new GoldenCompassException("Invalid command");
        }

    }

}
