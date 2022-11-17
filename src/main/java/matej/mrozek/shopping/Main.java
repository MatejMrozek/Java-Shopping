package matej.mrozek.shopping;

import matej.mrozek.shopping.atm.ATM;
import matej.mrozek.shopping.store.Store;

import java.util.Scanner;

public class Main {
    private static final ATM ATM = new ATM();
    private static final Store STORE = new Store();

    private static int purse;

    public static void main(String[] args) {
        while (true) {
            Logger.printDivider();
            Logger.print("Purse: " + purse);
            Logger.printDivider();
            Logger.print("Where do you want to go?");
            Logger.print();
            Logger.print("1) ATM");
            Logger.print("2) Store");
            Logger.print("3) Home (Exit the program)");
            Logger.printDivider();
            Logger.print();

            int option;
            String optionInput = new Scanner(System.in).nextLine();
            try {
                option = Integer.parseInt(optionInput);
            } catch (Exception exception) {
                Logger.print("Invalid input! Try again.");

                sleep(2000);

                Logger.flush();
                continue;
            }

            switch (option) {
                case 1 -> ATM.init();
                case 2 -> STORE.init();
                case 3 -> {
                    Logger.print("Exiting...");

                    sleep(1000);

                    return;
                }
                default -> {
                    Logger.print("Invalid input! Try again.");

                    sleep(2000);
                }
            }

            Logger.flush();
        }
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static ATM getATM() {
        return ATM;
    }

    public static Store getSTORE() {
        return STORE;
    }

    public static int getPurse() {
        return purse;
    }

    public static void addPurse(int amount) {
        purse += amount;
    }

    public static void removePurse(int amount) {
        purse -= amount;
    }
}