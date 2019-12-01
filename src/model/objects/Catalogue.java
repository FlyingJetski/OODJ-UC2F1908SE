package model.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.IOWriterReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Catalogue {
    protected int catalogueId;
    protected String name;
    protected List<Integer> productsId;
    protected List<Double> productsDiscount;
    protected Date dateStart;
    protected Date dateEnd;
    protected String description;
    public static ObservableList<Catalogue> catalogues = FXCollections.observableArrayList();

    public Catalogue(int catalogueId, String name, List<Integer> productsId, List<Double> productsDiscount,
                     Date dateStart, Date dateEnd, String description) {
        this.catalogueId = catalogueId;
        this.name = name;
        this.productsId = productsId;
        this.productsDiscount = productsDiscount;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
    }

    public Catalogue(String name, ArrayList<Integer> productsId, ArrayList<Double> productsDiscount, Date dateStart,
                     Date dateEnd, String description) {
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
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
                catalogueId, name, String.join("<>", productsId.toString()),
                String.join("<>", productsDiscount.toString()), dateStart, dateEnd, description
        );
    }
}
