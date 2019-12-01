package model.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import model.IOWriterReader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Catalogue {
    protected int catalogueId;
    protected String name;
    protected List<Integer> productsId;
    protected List<Double> productsDiscount;
    protected LocalDate dateStart;
    protected LocalDate dateEnd;
    protected String description;
    public static ObservableList<Catalogue> catalogues = FXCollections.observableArrayList();

    public Catalogue(int catalogueId, String name, List<Integer> productsId, List<Double> productsDiscount,
                     LocalDate dateStart, LocalDate dateEnd, String description) {
        this.catalogueId = catalogueId;
        this.name = name;
        this.productsId = productsId;
        this.productsDiscount = productsDiscount;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
    }

    public Catalogue(String name, List<Integer> productsId, List<Double> productsDiscount, LocalDate dateStart,
                     LocalDate dateEnd, String description) {
        this.catalogueId = IOWriterReader.getCatalogueId();
        this.name = name;
        this.productsId = productsId;
        this.productsDiscount = productsDiscount;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
    }

    public int getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(int catalogueId) {
        this.catalogueId = catalogueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProductsName() {
        Predicate<Product> productPredicate = product -> this.getProductsId().contains(product.getProductId());
        FilteredList<Product> products = Product.products.filtered(productPredicate);
        List<String> productsName = new ArrayList<>();
        for (Product product: products) {
            productsName.add(product.getName());
        }
        return productsName;
    }

    public List<Integer> getProductsId() {
        return productsId;
    }

    public void setProductsId(List<Integer> productsId) {
        this.productsId = productsId;
    }

    public List<Double> getProductsDiscount() {
        return productsDiscount;
    }

    public void setProductsDiscount(List<Double> productsDiscount) {
        this.productsDiscount = productsDiscount;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s|%s|%s",
                catalogueId, name, productsId.stream().map(Object::toString).collect(Collectors.joining("<>")),
                productsDiscount.stream().map(Object::toString).collect(Collectors.joining("<>")),
                dateStart, dateEnd, description
        );
    }
}
