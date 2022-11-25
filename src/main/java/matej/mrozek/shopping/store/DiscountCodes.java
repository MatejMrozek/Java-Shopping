package matej.mrozek.shopping.store;

public enum DiscountCodes {
    ILOVEPROGRAMMING("ILOVEPROGRAMMING", 15, ProductCategory.Electronics),
    FRESH("FRESH", 20, ProductCategory.Fruits);

    public final String code;

    public final int discountPercentage;

    public final ProductCategory productCategory;

    DiscountCodes(String code, int discountPercentage, ProductCategory productCategory) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.productCategory = productCategory;
    }

    public int getDiscount(int categoryPrice) {
        return (categoryPrice * discountPercentage) / 100;
    }

    @Override
    public String toString() {
        return code;
    }
}
