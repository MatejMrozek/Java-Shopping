package matej.mrozek.shopping.store.cart;

import matej.mrozek.shopping.store.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    final List<ProductAmount> productAmountList;

    public Cart() {
        this.productAmountList = new ArrayList<>();
    }

    public int getTotalPrice() {
        int price = 0;
        for (ProductAmount productAmount : productAmountList) {
            price += productAmount.getAmount() * productAmount.product.price;
        }

        return price;
    }

    public boolean isEmpty() {
        return productAmountList.isEmpty();
    }

    public void clear() {
        productAmountList.clear();
    }

    public List<ProductAmount> getProducts() {
        return productAmountList;
    }

    public void addProduct(Product product, int amount) {
        boolean isInCart = false;
        int cartProductPosition = 0;
        for (ProductAmount productAmount : productAmountList) {
            if (productAmount.product == product) {
                isInCart = true;
                break;
            }

            cartProductPosition++;
        }

        if (!isInCart) {
            productAmountList.add(new ProductAmount(product, amount));
        } else {
            productAmountList.get(cartProductPosition).addAmount(amount);
        }
    }

    public void removeProduct(Product product, int amount) {
        boolean isInCart = false;
        int cartProductPosition = 0;
        for (ProductAmount productAmount : productAmountList) {
            if (productAmount.product == product) {
                isInCart = true;
                break;
            }

            cartProductPosition++;
        }

        if (!isInCart) {
            return;
        }

        productAmountList.get(cartProductPosition).removeAmount(amount);
    }
}
