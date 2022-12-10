package matej.mrozek.shopping;

import matej.mrozek.shopping.atm.ATM;
import matej.mrozek.shopping.store.Store;
import matej.mrozek.shopping.store.cart.ProductAmount;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<ProductAmount> ownedProducts = new ArrayList<>();

    private static final ATM ATM = new ATM();
    private static final Store STORE = new Store();

    private static int purse;

    public static void main(String[] args) {
        ATM.addShowcaseAccounts();

        while (true) {
            Logger.clear();
            Logger.printDivider();
            Logger.print("Square");
            Logger.print();
            Logger.print("Purse: " + purse);
            Logger.print();
            Logger.print("1) ATM");
            Logger.print("2) Store");
            Logger.print("3) Home (Exit the program)");
            Logger.printDivider();
            Logger.print();

            int option;
            String optionInput = new Scanner(System.in).nextLine();
            Logger.print();
            try {
                option = Integer.parseInt(optionInput);
            } catch (Exception exception) {
                Logger.print("Invalid input! Try again.");

                sleep(2000);

                continue;
            }

            switch (option) {
                case 1 -> {
                    Logger.print("Going to the ATM...");

                    sleep(1500);

                    ATM.init();
                }
                case 2 -> {
                    Logger.print("Going to the store...");

                    sleep(1500);

                    STORE.init();
                }
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

            Logger.clear();
        }
    }

    public static ATM getATM() {
        return ATM;
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
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

    public static void addOwnedProducts(ProductAmount productAmount) {
        boolean addAsNewOwnedProduct = true;
        for (ProductAmount ownedProduct : ownedProducts) {
            if (ownedProduct.product.name.equals(productAmount.product.name)) {
                addAsNewOwnedProduct = false;

                ownedProduct.addAmount(productAmount.getAmount());

                break;
            }
        }

        if (addAsNewOwnedProduct) {
            ownedProducts.add(productAmount);
        }
    }
}
