package model.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.IOWriterReader;

import java.util.function.Predicate;

public class Product {
    protected int productId;
    protected String productName;
    protected int categoryId;
    protected int supplierId;
    protected int quantity;
    protected double purchasingPrice;
    protected double sellingPrice;
    protected String profitMargin;
    protected String categoryName;
    protected String supplierName;

    public static ObservableList<Product> products = FXCollections.observableArrayList();

    public Product(int productId, String productName, int categoryId, int supplierId, int quantity,
                   double purchasingPrice, double sellingPrice) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.productName = productName;
        this.quantity = quantity;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
        this.profitMargin = (sellingPrice - purchasingPrice) / sellingPrice * 100 + "%";
    }

    public Product(String productName, int categoryId, int supplierId, int quantity,
                   double purchasingPrice, double sellingPrice) {
            this.productId = IOWriterReader.getProductId();
            this.categoryId = categoryId;
            this.supplierId = supplierId;
            this.productName = productName;
            this.quantity = quantity;
            this.purchasingPrice = purchasingPrice;
            this.sellingPrice = sellingPrice;
            this.profitMargin = (sellingPrice - purchasingPrice) / sellingPrice * 100 + "%";
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        Predicate<Category> categoryPredicate = category -> category.getCategoryId() == this.getCategoryId();
        return Category.categories.filtered(categoryPredicate).get(0).categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSupplierName() {
        Predicate<Supplier> supplierPredicate = supplier -> supplier.getSupplierId() == this.getSupplierId();
        return Supplier.suppliers.filtered(supplierPredicate).get(0).name;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(double purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(String profitMargin) {
        this.profitMargin = profitMargin;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s|%s|%s|%s",
                productId, productName, categoryId, supplierId,
                quantity, purchasingPrice, sellingPrice,
                profitMargin
        );
    }
}
