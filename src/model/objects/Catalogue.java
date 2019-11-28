package model.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.IOWriterReader;
import java.util.ArrayList;
import java.util.Date;

public class Catalogue {
    protected int catalogueId;
    protected String name;
    protected ArrayList<Integer> productsId = new ArrayList<>();
    protected ArrayList<Double> productsDiscount = new ArrayList<>();
    protected Date dateStart;
    protected Date dateEnd;
    protected String description;
    public static ObservableList<Catalogue> catalogues = FXCollections.observableArrayList();

    public Catalogue(int catalogueId, String name, ArrayList<Integer> productsId, ArrayList<Double> productsDiscount,
                     Date dateStart, Date dateEnd, String description) {
        this.catalogueId = catalogueId;
        this.name = name;
        this.productsId = productsId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
    }

    public Catalogue(String name, ArrayList<Integer> productsId, ArrayList<Double> productsDiscount, Date dateStart,
                     Date dateEnd, String description) {
        this.catalogueId = IOWriterReader.getCatalogueId();
        this.name = name;
        this.productsId = productsId;
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

    public ArrayList<Integer> getProductsId() {
        return productsId;
    }

    public void setProductsId(ArrayList<Integer> productsId) {
        this.productsId = productsId;
    }

    public ArrayList<Double> getProductsDiscount() {
        return productsDiscount;
    }

    public void setProductsDiscount(ArrayList<Double> productsId) {
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
        return String.format("%s|%s|%s|%s|%s|%s",
                catalogueId, name, String.join("<>", productsId.toString()),
                dateStart, dateEnd, description
        );
    }
}
