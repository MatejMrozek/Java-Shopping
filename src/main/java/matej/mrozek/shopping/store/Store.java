package matej.mrozek.shopping.store;

import matej.mrozek.shopping.Logger;
import matej.mrozek.shopping.Main;
import matej.mrozek.shopping.store.cart.Cart;

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

        boolean leaving = false;
        do {
            Logger.printDivider();
            Logger.print("Welcome to our store, what do you want to do?");
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
                    //TODO: View and add products

                    /*int productI = 0;
                    for (Product product : products) {
                        productI++;

                        Logger.print(productI + ") " + product.name + " - " + product.price + "CZK" + (product.adultOnly ? " (18+)" : ""));
                    }*/
                }
                case 2 -> {
                    //TODO: View cart
                }
                case 3 -> {
                    if (optionCount == 3) {
                        exit = true;
                    } else {
                        //TODO: Payment
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

                break;
            }

            if (invalid) {
                Logger.print("Invalid input! Try again.");

                Main.sleep(2000);

                Logger.flush();
                continue;
            }
        } while (!leaving);

        Logger.flush();
    }

    void loadProducts() {
        addProduct(new Product("Apple", 8));
        addProduct(new Product("Vodka", 110, true));
        addProduct(new Product("iPhone", 25000));
    }

    void addProduct(Product product) {
        products.add(product);
    }
}
