package matej.mrozek.shopping.store.cart;

import matej.mrozek.shopping.store.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    final List<CartProduct> cartProductList;

    public Cart() {
        this.cartProductList = new ArrayList<>();
    }

    public int getTotalPrice() {
        int price = 0;
        for (CartProduct cartProduct : cartProductList) {
            price += cartProduct.getAmount() * cartProduct.product.price;
        }

        return price;
    }

    public boolean isEmpty() {
        return cartProductList.isEmpty();
    }

    public void clear() {
        cartProductList.clear();
    }

    public void addProduct(Product product, int amount) {
        boolean isInCart = false;
        int cartProductPosition = 0;
        for (CartProduct cartProduct : cartProductList) {
            if (cartProduct.product == product) {
                isInCart = true;
                break;
            }

            cartProductPosition++;
        }

        if (!isInCart) {
            cartProductList.add(new CartProduct(product, amount));
        } else {
            cartProductList.get(cartProductPosition).addAmount(amount);
        }
    }

    public void removeProduct(Product product, int amount) {
        boolean isInCart = false;
        int cartProductPosition = 0;
        for (CartProduct cartProduct : cartProductList) {
            if (cartProduct.product == product) {
                isInCart = true;
                break;
            }

            cartProductPosition++;
        }

        if (!isInCart) {
            return;
        }

        cartProductList.get(cartProductPosition).removeAmount(amount);
    }
}
