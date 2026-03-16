package seedu.goldencompass.ui;

import java.util.Scanner;

public class Ui {

    Scanner in = new Scanner(System.in);

    public void greet() {
        print("Hello, I'm your Golden Compass!");
    }

    public void print(String s) {
        System.out.println(s);
    }

    public String read() {
        return in.nextLine();
    }

}

