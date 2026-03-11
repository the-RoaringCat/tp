package seedu.goldencompass.ui;

import java.util.Scanner;

public class Ui {

    Scanner in = new Scanner(System.in);

    public void greet() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");
    }

    public void print(String s) {
        System.out.println(s);
    }

    public String read() {
        return in.nextLine();
    }

}

