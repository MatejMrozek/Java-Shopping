package matej.mrozek.shopping.store;

public class Product {
    public final String name;

    public final int price;

    public final boolean adultOnly;

    public Product(String name, int price, boolean adultOnly) {
        this.name = name;
        this.price = price;
        this.adultOnly = adultOnly;
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
        this.adultOnly = false;
    }
}
