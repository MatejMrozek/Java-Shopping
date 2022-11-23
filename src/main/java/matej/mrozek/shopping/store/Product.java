package matej.mrozek.shopping.store;

public class Product {
    public final String name;

    public final ProductCategory productCategory;

    public final int price;

    public final boolean adultOnly;

    public Product(String name, int price, ProductCategory productCategory, boolean adultOnly) {
        this.name = name;
        this.price = price;
        this.productCategory = productCategory;
        this.adultOnly = adultOnly;
    }

    public Product(String name, int price, ProductCategory productCategory) {
        this.name = name;
        this.price = price;
        this.productCategory = productCategory;
        this.adultOnly = false;
    }
}
