package matej.mrozek.shopping.store;

public enum ProductCategory {
    Fruits("Fruits"),
    Vegetables("Vegetables"),
    Pastry("Pastry"),
    Alcohol("Alcohol"),
    Electronics("Electronics");

    private final String title;

    ProductCategory(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
