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
            Logger.clear();
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

                        Logger.print();

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

                                    boolean printed = false;
                                    for (DiscountCodes discountCode : DiscountCodes.values()) {
                                        if (selectedCategory == discountCode.productCategory) {
                                            Logger.print("Enter the code \"" + discountCode.code + "\" on checkout to get " + discountCode.discountPercentage + "% off!");
                                            printed = true;
                                        }
                                    }

                                    if (printed) {
                                        Logger.print();
                                    }

                                    int optionAmount2 = 0;
                                    for (Product product : products) {
                                        if (product.productCategory != selectedCategory) {
                                            continue;
                                        }

                                        optionAmount2++;
                                        Logger.print(optionAmount2 + ") " + product.name + " - " + product.price + "CZK" + (product.adultOnly ? " (18+)" : ""));
                                    }

                                    Logger.print();

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
                                        if (productOption == optionAmount2) {
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

                                                            Logger.print("Successfully added " + productAmount + " " + product.name + (productAmount > 1 ? "s" : "") + " to your shopping cart.");

                                                            Main.sleep(1500);
                                                        }

                                                        break;
                                                    }

                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        Logger.print("Invalid input! Try again.");

                                        Main.sleep(2000);
                                    }
                                }
                            }
                        } else {
                            Logger.print("Invalid input! Try again.");

                            Main.sleep(2000);
                        }
                    }
                }
                case 2 -> {
                    while (true){
                        Logger.clear();
                        Logger.printDivider();
                        Logger.print("Store - Cart");
                        Logger.print();

                        int productI = 0;
                        for (ProductCategory productCategory : ProductCategory.values()) {
                            boolean printCategory = true;

                            for (ProductAmount productAmount : cart.getProducts()) {
                                if (productAmount.product.productCategory != productCategory) {
                                    continue;
                                }

                                productI++;

                                if (printCategory) {
                                    Logger.print("--- " + productCategory.toString() + " ---");
                                    printCategory = false;
                                }

                                Product product = productAmount.product;
                                Logger.print(productI + ") " + product.name + " - " + product.price + "CZK (for each)" + (product.adultOnly ? " (18+)" : ""));
                                Logger.print(" ".repeat(String.valueOf(productI).length()) + "  Total " + productAmount.getAmount() + " for " + productAmount.getPrice() + "CZK");
                            }

                            if (!printCategory) {
                                Logger.print();
                            }
                        }

                        if (cart.isEmpty()) {
                            Logger.print("Empty");
                            Logger.print();
                        }

                        productI++;
                        Logger.print(productI + ") Go back.");
                        Logger.printDivider();
                        Logger.print();

                        int removeOption;
                        String removeOptionInput = new Scanner(System.in).nextLine();
                        Logger.print();
                        try {
                            removeOption = Integer.parseInt(removeOptionInput);
                        } catch (Exception exception) {
                            removeOption = -1;
                        }

                        boolean goBack = false;
                        if (removeOption > 0) {
                            if (removeOption == productI) {
                                goBack = true;
                            } else {
                                int productI2 = 0;
                                for (ProductCategory productCategory : ProductCategory.values()) {
                                    boolean end = false;
                                    for (ProductAmount productAmount : cart.getProducts()) {
                                        if (productAmount.product.productCategory != productCategory) {
                                            continue;
                                        }

                                        productI2++;
                                        if (productI2 == removeOption) {
                                            int removeAmount = 1;
                                            if (productAmount.getAmount() > 1) {
                                                Logger.print("How many " + productAmount.product.name + "s do you want to remove (" + productAmount.getAmount() + " in cart, 0 to go back)? ", true);

                                                String removeAmountInput = new Scanner(System.in).nextLine();
                                                Logger.print();
                                                try {
                                                    removeAmount = Integer.parseInt(removeAmountInput);
                                                } catch (Exception exception) {
                                                    removeAmount = -1;
                                                }
                                            }

                                            if (removeAmount == 0) {
                                                break;
                                            } else if (removeAmount > 0 && productAmount.getAmount() - removeAmount >= 0) {
                                                int productAmountI = -1;
                                                for (ProductAmount productAmount2 : cart.getProducts()) {
                                                    productAmountI++;
                                                    if (productAmount2 == productAmount) {
                                                        if (productAmount2.getAmount() - removeAmount == 0) {
                                                            cart.getProducts().remove(productAmountI);
                                                        } else {
                                                            productAmount2.removeAmount(removeAmount);
                                                        }

                                                        break;
                                                    }
                                                }

                                                Logger.print("Successfully removed " + removeAmount + " " + productAmount.product.name + (productAmount.getAmount() > 1 ? "s" : "") + " from your cart.");

                                                Main.sleep(1500);
                                            } else {
                                                Logger.print("Invalid input! Try again.");

                                                Main.sleep(2000);
                                            }

                                            end = true;
                                            break;
                                        }
                                    }

                                    if (end) {
                                        break;
                                    }
                                }
                            }
                        } else {
                            Logger.print("Invalid input! Try again.");

                            Main.sleep(2000);
                        }

                        if (goBack) {
                            break;
                        }
                    }
                }
                case 3 -> {
                    if (optionCount == 3) {
                        exit = true;
                    } else {
                        while (true) {
                            int totalPrice = cart.getTotalPrice();
                            Logger.clear();

                            Logger.printDivider();
                            Logger.print("Store - Payment");
                            Logger.print();

                            for (ProductCategory productCategory : ProductCategory.values()) {
                                boolean printCategory = true;

                                int productI = 0;
                                for (ProductAmount productAmount : cart.getProducts()) {
                                    if (productAmount.product.productCategory != productCategory) {
                                        continue;
                                    }

                                    if (printCategory) {
                                        Logger.print("--- " + productCategory.toString() + " ---");
                                        printCategory = false;
                                    }

                                    productI++;
                                    Product product = productAmount.product;
                                    Logger.print(productI + " - " + product.name + " - " + product.price + "CZK (for each)" + (product.adultOnly ? " (18+)" : ""));
                                    Logger.print(" ".repeat(String.valueOf(productI).length()) + "   Total " + productAmount.getAmount() + " for " + productAmount.getPrice() + "CZK");
                                }

                                if (!printCategory) {
                                    Logger.print();
                                }
                            }

                            Logger.print();
                            Logger.print("Total for " + totalPrice + "CZK");
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

                            int savedMoney = 0;
                            DiscountCodes discountCode = null;
                            if (paymentOption > 0 && paymentOption < 3) {
                                Logger.print("Enter a discount code (leave blank if you don't have any)? ");
                                Logger.print();

                                String discountCodeInput = new Scanner(System.in).nextLine();
                                Logger.print();

                                for (DiscountCodes discountCode2 : DiscountCodes.values()) {
                                    if (discountCodeInput.equals(discountCode2.code)) {
                                        discountCode = discountCode2;

                                        int categoryPrice = 0;
                                        for (ProductAmount productAmount : cart.getProducts()) {
                                            if (productAmount.product.productCategory == discountCode2.productCategory) {
                                                categoryPrice += productAmount.getPrice();
                                            }
                                        }

                                        savedMoney = discountCode.getDiscount(categoryPrice);
                                        totalPrice -= savedMoney;

                                        break;
                                    }
                                }
                            }

                            boolean goBack = true;
                            switch (paymentOption) {
                                case 1 ->  {
                                    if (Main.getPurse() - totalPrice < 0) {
                                        Logger.print("You don't have enough money to pay " + totalPrice + "CZK.");

                                        Main.sleep(2000);

                                        goBack = false;
                                    } else {
                                        Main.removePurse(totalPrice);
                                        for (ProductAmount productAmount : cart.getProducts()) {
                                            Main.addOwnedProducts(productAmount);
                                        }

                                        Logger.print("You have paid " + totalPrice + "CZK.");
                                        if (discountCode != null && savedMoney > 0) {
                                            Logger.print("You saved " + savedMoney + "CZK using the code " + discountCode.code + " to get " + discountCode.discountPercentage + "% off in the " + discountCode.productCategory + " category.");
                                        }

                                        Logger.print("Thanks for you purchase.");

                                        cart.clear();

                                        printReceipt();

                                        Main.sleep(5000);
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
                                            if (account.getBalance() - totalPrice < 0) {
                                                Logger.print("There is not enough money on the account " + account.username + ".");

                                                Main.sleep(2000);
                                            } else {
                                                account.removeMoney(totalPrice);
                                                for (ProductAmount productAmount : cart.getProducts()) {
                                                    Main.addOwnedProducts(productAmount);
                                                }

                                                Logger.print("You have paid " + totalPrice + "CZK using the account " + account.username + ".");
                                                if (discountCode != null && savedMoney > 0) {
                                                    Logger.print("You saved " + savedMoney + "CZK using the code " + discountCode.code + " to get " + discountCode.discountPercentage + "% off in the " + discountCode.productCategory + " category.");
                                                }

                                                Logger.print("Thanks for you purchase.");

                                                cart.clear();

                                                Main.sleep(5000);
                                            }

                                            break;
                                        }
                                    }
                                }
                            }

                            if (goBack) {
                                break;
                            }
                        }
                    }
                }
                case 5 -> exit = true;
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
            }
        } while (!leaving);
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
