package model.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.IOWriterReader;

import java.io.IOException;

public class Category {
    protected int categoryId;
    protected String categoryName;
    public static ObservableList<Category> categories = FXCollections.observableArrayList();

    public Category(int categoryId, String categoryName) throws IOException {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category(String categoryName) throws IOException {
        this.categoryId = IOWriterReader.getCategoryId();
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return String.format("%s|%s",
                categoryId, categoryName
        );
    }
}
