package matej.mrozek.shopping.store.cart;

import matej.mrozek.shopping.store.Product;

public class CartProduct {
    public final Product product;

    private int amount;

    public CartProduct(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void removeAmount(int amount) {
        this.amount -= amount;
    }
}
