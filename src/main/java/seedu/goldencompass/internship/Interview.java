package seedu.goldencompass.internship;

public class Interview {

    protected Internship internship;
    protected String date;
    protected String comments;

    public Interview(Internship internship, String date) {
        this.internship = internship;
        this.date = date;
    }

}
