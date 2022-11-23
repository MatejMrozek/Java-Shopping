package matej.mrozek.shopping.store;

import matej.mrozek.shopping.Logger;
import matej.mrozek.shopping.Main;
import matej.mrozek.shopping.atm.Account;
import matej.mrozek.shopping.store.cart.Cart;
import matej.mrozek.shopping.store.cart.ProductAmount;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Store {
    final List<Product> products;

    final Cart cart;

    public Store() {
        this.products = new ArrayList<>();
        this.cart = new Cart();
    }

    public void init() {
        if (products.isEmpty()) {
            loadProducts();
        }

        Logger.clear();

        boolean leaving = false;
        do {
            Logger.printDivider();
            Logger.print("Store");
            Logger.print();

            int optionCount = 1;
            Logger.print(optionCount + ") Select products");

            optionCount++;
            Logger.print(optionCount + ") Cart" + (cart.isEmpty() ? " (empty)" : ""));

            if (!cart.isEmpty()) {
                optionCount++;
                Logger.print(optionCount + ") Payment");
            }

            optionCount++;
            Logger.print(optionCount + ") Exit");

            Logger.printDivider();
            Logger.print();

            int option;
            String optionInput = new Scanner(System.in).nextLine();
            Logger.print();
            try {
                option = Integer.parseInt(optionInput);
            } catch (Exception exception) {
                option = -1;
            }

            boolean exit = false;
            boolean invalid = false;
            switch (option) {
                case 1 -> {
                    while (true) {
                        Logger.clear();
                        Logger.printDivider();
                        Logger.print("Store - Categories");
                        Logger.print();

                        int optionAmount = 0;
                        for (ProductCategory productCategory : ProductCategory.values()) {
                            optionAmount++;
                            Logger.print(optionAmount + ") " + productCategory.toString());
                        }

                        optionAmount++;
                        Logger.print(optionAmount + ") Go back");
                        Logger.printDivider();
                        Logger.print();

                        int categoryOption;
                        String categoryOptionInput = new Scanner(System.in).nextLine();
                        Logger.print();
                        try {
                            categoryOption = Integer.parseInt(categoryOptionInput);
                        } catch (Exception exception) {
                            categoryOption = -1;
                        }

                        if (categoryOption > 0) {
                            if (categoryOption >= optionAmount) {
                                Logger.clear();
                                break;
                            } else {
                                ProductCategory selectedCategory = null;

                                int productCategoryI = 0;
                                for (ProductCategory productCategory : ProductCategory.values()) {
                                    productCategoryI++;

                                    if (productCategoryI == categoryOption) {
                                        selectedCategory = productCategory;
                                        break;
                                    }
                                }

                                while (true) {
                                    Logger.clear();
                                    Logger.printDivider();
                                    Logger.print("Store - " + selectedCategory.toString());
                                    Logger.print();

                                    int optionAmount2 = 0;
                                    for (Product product : products) {
                                        if (product.productCategory != selectedCategory) {
                                            continue;
                                        }

                                        optionAmount2++;
                                        Logger.print(optionAmount2 + ") " + product.name + " - " + product.price + "CZK" + (product.adultOnly ? " (18+)" : ""));
                                    }

                                    optionAmount2++;
                                    Logger.print(optionAmount2 + ") Go back");
                                    Logger.printDivider();
                                    Logger.print();

                                    int productOption;
                                    String productOptionInput = new Scanner(System.in).nextLine();
                                    Logger.print();
                                    try {
                                        productOption = Integer.parseInt(productOptionInput);
                                    } catch (Exception exception) {
                                        productOption = -1;
                                    }

                                    if (productOption > 0) {
                                        if (productOption >= optionAmount2) {
                                            Logger.clear();
                                            break;
                                        } else {
                                            int productI = 0;
                                            for (Product product : products) {
                                                if (product.productCategory != selectedCategory) {
                                                    continue;
                                                }

                                                productI++;
                                                if (productI == productOption) {
                                                    while (true) {
                                                        Logger.print("How many " + product.name + "s do you want (0 to go back)? ", true);
                                                        int productAmount;
                                                        String productAmountInput = new Scanner(System.in).nextLine();
                                                        Logger.print();
                                                        try {
                                                            productAmount = Integer.parseInt(productAmountInput);
                                                        } catch (Exception exception) {
                                                            productAmount = -1;
                                                        }

                                                        if (productAmount < 0) {
                                                            Logger.print("Invalid input! Try again.");
                                                            Logger.print();
                                                            continue;
                                                        } else if (productAmount > 0) {
                                                            cart.addProduct(product, productAmount);

                                                            Logger.print("Successfully added " + productAmount + " " + product.name + "s to your shopping cart.");
                                                            Main.sleep(1500);
                                                        }

                                                        Logger.clear();

                                                        break;
                                                    }

                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        Logger.print("Invalid input! Try again.");

                                        Main.sleep(2000);

                                        Logger.clear();
                                    }
                                }
                            }
                        } else {
                            Logger.print("Invalid input! Try again.");

                            Main.sleep(2000);

                            Logger.clear();
                        }
                    }
                }
                case 2 -> {
                    Logger.clear();
                    Logger.printDivider();
                    Logger.print("Store - Cart");
                    Logger.print();

                    for (ProductCategory productCategory : ProductCategory.values()) {
                        boolean printCategory = true;

                        int i = 0;
                        for (ProductAmount productAmount : cart.getProducts()) {
                            if (productAmount.product.productCategory != productCategory) {
                                continue;
                            }

                            i++;

                            if (printCategory) {
                                Logger.print("--- " + productCategory.toString() + " ---");
                                Logger.print();
                                printCategory = false;
                            }

                            Product product = productAmount.product;
                            Logger.print(i + " - " + product.name + " - " + product.price + "CZK (for each)" + (product.adultOnly ? " (18+)" : ""));
                            if (productAmount.getAmount() > 1) {
                                Logger.print(" ".repeat(String.valueOf(i).length()) + "   Total " + productAmount.getAmount() + " for " + productAmount.getPrice() + "CZK");
                            }
                        }
                    }

                    if (cart.isEmpty()) {
                        Logger.print("Empty");
                    }

                    Logger.print();
                    Logger.print("Enter anything to go back.");
                    Logger.printDivider();
                    Logger.print();

                    String discard = new Scanner(System.in).nextLine();
                    if (discard.equals("no")) {
                        Logger.print();
                        Logger.print("Yes. =)");
                    }
                }
                case 3 -> {
                    if (optionCount == 3) {
                        exit = true;
                    } else {
                        while (true) {
                            Logger.clear();
                            Logger.printDivider();
                            Logger.print("Store - Payment");
                            Logger.print();

                            for (ProductCategory productCategory : ProductCategory.values()) {
                                boolean printCategory = true;

                                int i = 0;
                                for (ProductAmount productAmount : cart.getProducts()) {
                                    if (productAmount.product.productCategory != productCategory) {
                                        continue;
                                    }

                                    i++;

                                    if (printCategory) {
                                        Logger.print("--- " + productCategory.toString() + " ---");
                                        printCategory = false;
                                    }

                                    Product product = productAmount.product;
                                    Logger.print(i + " - " + product.name + " - " + product.price + "CZK (for each)" + (product.adultOnly ? " (18+)" : ""));
                                    if (productAmount.getAmount() > 1) {
                                        Logger.print(" ".repeat(String.valueOf(i).length()) + "   Total " + productAmount.getAmount() + " for " + productAmount.getPrice() + "CZK");
                                    }
                                }
                            }

                            Logger.print();
                            Logger.print("Total for " + cart.getTotalPrice() + "CZK");
                            Logger.print();
                            Logger.print("1) Pay in cash");
                            Logger.print("2) Pay by card");
                            Logger.print("3) Go back");
                            Logger.printDivider();
                            Logger.print();

                            int paymentOption;
                            String paymentOptionInput = new Scanner(System.in).nextLine();
                            Logger.print();
                            try {
                                paymentOption = Integer.parseInt(paymentOptionInput);
                            } catch (Exception exception) {
                                paymentOption = -1;
                            }

                            boolean goBack = true;
                            switch (paymentOption) {
                                case 1 ->  {
                                    if (Main.getPurse() - cart.getTotalPrice() < 0) {
                                        Logger.print("You don't have enough money to pay " + cart.getTotalPrice() + "CZK.");

                                        Main.sleep(2000);

                                        goBack = false;
                                    } else {
                                        Main.removePurse(cart.getTotalPrice());
                                        for (ProductAmount productAmount : cart.getProducts()) {
                                            Main.addOwnedProducts(productAmount);
                                        }

                                        cart.clear();
                                        
                                        Logger.print("You have paid " + cart.getTotalPrice() + "CZK.");
                                        Logger.print("Thanks for you purchase.");

                                        printReceipt();

                                        Main.sleep(1500);
                                    }
                                }
                                case 2 -> {
                                    Logger.clear();

                                    Logger.printDivider();
                                    Logger.print("Store - Card payment");
                                    Logger.printDivider();
                                    Logger.print();

                                    boolean goBack2 = false;
                                    Account selectedAccount = null;
                                    do {
                                        Logger.print("Enter your username (leave blank to go back): ", true);
                                        String usernameInput = new Scanner(System.in).nextLine();
                                        Logger.print();
                                        if (usernameInput.equals("")) {
                                            goBack2 = true;
                                            break;
                                        } else if (usernameInput.length() < 3) {
                                            Logger.print("Usernames can't be shorter than 3 characters, try again.");
                                        } else if (usernameInput.length() > 16) {
                                            Logger.print("Usernames can't be longer than 16 characters, try again.");
                                        } else if (usernameInput.matches("[0-9]")) {
                                            Logger.print("Usernames can't contain numbers, try again");
                                        } else if (usernameInput.matches("[~!@#$%^&*()_+{}\\[\\]:;,.<>/?\n]")) {
                                            Logger.print("Usernames can't contain special characters, try again.");
                                        } else {
                                            boolean invalidAccount = true;
                                            for (Account account : Main.getATM().getAccounts()) {
                                                if (account.username.equalsIgnoreCase(usernameInput)) {
                                                    selectedAccount = account;
                                                    invalidAccount = false;
                                                    break;
                                                }
                                            }

                                            if (invalidAccount) {
                                                Logger.print("Account with this username doesn't exist, try again.");
                                            }
                                        }
                                    } while (selectedAccount == null);

                                    boolean passwordIncorrect = true;
                                    do {
                                        Logger.print("Enter your password (leave blank to go back): ", true);
                                        String passwordInput = new Scanner(System.in).nextLine();
                                        if (passwordInput.equals("")) {
                                            goBack2 = true;
                                        } else if (passwordInput.length() < 8) {
                                            Logger.print("Passwords can't be shorter than 8 characters, try again.");
                                        } else if (passwordInput.length() > 16) {
                                            Logger.print("Usernames can't be longer than 16 characters, try again.");
                                        } else if (selectedAccount.checkPasswordCorrect(passwordInput)) {
                                            passwordIncorrect = false;
                                        } else {
                                            Logger.print("Incorrect password, try again.");
                                        }

                                        Logger.print();
                                    } while (passwordIncorrect && !goBack2);

                                    if (goBack2) {
                                        break;
                                    }

                                    Logger.print("Logging in...");
                                    Logger.print("Successfully logged in as " + selectedAccount.username + ".");
                                    Logger.print();

                                    for (Account account : Main.getATM().getAccounts()) {
                                        if (account == selectedAccount) {
                                            if (account.getBalance() - cart.getTotalPrice() < 0) {
                                                Logger.print("There is not enough money on the account " + account.username + ".");
                                            } else {
                                                account.removeMoney(cart.getTotalPrice());
                                                for (ProductAmount productAmount : cart.getProducts()) {
                                                    Main.addOwnedProducts(productAmount);
                                                }

                                                cart.clear();

                                                Logger.print("You have paid " + cart.getTotalPrice() + "CZK usign the account " + account.username + ".");
                                            }

                                            break;
                                        }
                                    }

                                    Main.sleep(2000);

                                    Logger.clear();
                                }
                            }

                            if (goBack) {
                                Logger.clear();
                                break;
                            }
                        }
                    }
                }
                case 4 -> {
                    if (optionCount == 4) {
                        exit = true;
                    } else {
                        invalid = true;
                    }
                }
                default -> invalid = true;
            }

            if (exit) {
                Logger.print("Leaving the store.");

                cart.clear();

                Main.sleep(2000);

                leaving = true;
            }

            if (invalid) {
                Logger.print("Invalid input! Try again.");

                Main.sleep(2000);

                Logger.clear();
            }
        } while (!leaving);

        Logger.clear();
    }

    void printReceipt() {
        //TODO: Receipt printing
    }

    void loadProducts() {
        addProduct(new Product("Apple", 8, ProductCategory.Fruits));
        addProduct(new Product("Vodka", 110, ProductCategory.Alcohol, true));
        addProduct(new Product("iPhone", 25000, ProductCategory.Electronics));
    }

    void addProduct(Product product) {
        products.add(product);
    }
}
