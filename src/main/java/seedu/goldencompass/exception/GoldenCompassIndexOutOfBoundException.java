package seedu.goldencompass.exception;

public class GoldenCompassIndexOutOfBoundException extends GoldenCompassException {
    public GoldenCompassIndexOutOfBoundException(int lowerBound, int upperBound) {
        super("Error: Please provide an index from " + lowerBound + " to " + upperBound + ".");
    }
}
