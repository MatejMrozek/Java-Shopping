package matej.mrozek.shopping;

import java.io.IOException;

public class Logger {
    public static final String DIVIDER = "----------------------------------------------";

    public static void print(String text, boolean noNewLine) {
        if (noNewLine) {
            System.out.print(text);
        } else {
            System.out.println(text);
        }
    }

    public static void print(String text) {
        print(text, false);
    }

    public static void print() {
        print("");
    }

    public static void printDivider() {
        print(DIVIDER);
    }

    public static void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
