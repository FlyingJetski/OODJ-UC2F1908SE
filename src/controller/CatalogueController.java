package controller;

import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.DuplicateException;
import model.objects.Log;
import model.objects.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class CatalogueController implements Initializable {
    @FXML AnchorPane addCataloguePane;
    @FXML AnchorPane editCataloguePane;
    @FXML ComboBox searchComboBox;
    @FXML TextField searchText;

    @FXML TableView<Catalogue> catalogueTableView;
    @FXML TableColumn catalogueIdTableColumn;
    @FXML TableColumn nameTableColumn;
    @FXML TableColumn userUsernameTableColumn;
    @FXML TableColumn productsTableColumn;
    @FXML TableColumn discountsTableColumn;
    @FXML TableColumn startingDateTableColumn;
    @FXML TableColumn endingDateTableColumn;
    @FXML TableColumn descriptionTableColumn;

    @FXML TextField addCatalogueName;
    @FXML ComboBox<Product> addCatalogueProducts;
    @FXML TextField addCatalogueDiscount;
    @FXML TableView<Product> addCatalogueSelectedProducts;
    @FXML TableColumn addCatalogueSelectedProductsNameTableColumn;
    @FXML TableColumn addCatalogueSelectedProductsDiscountTableColumn;
    @FXML Button addCatalogueSelectedProductsAddButton;
    @FXML Button addCatalogueSelectedProductsRemoveButton;
    @FXML DatePicker addCatalogueStartingDate;
    @FXML DatePicker addCatalogueEndingDate;
    @FXML TextArea addCatalogueDescription;

    @FXML TextField editCatalogueName;
    @FXML ComboBox<Product> editCatalogueProducts;
    @FXML TextField editCatalogueDiscount;
    @FXML TableView<Product> editCatalogueSelectedProducts;
    @FXML TableColumn editCatalogueSelectedProductsNameTableColumn;
    @FXML TableColumn editCatalogueSelectedProductsDiscountTableColumn;
    @FXML Button editCatalogueSelectedProductsAddButton;
    @FXML Button editCatalogueSelectedProductsRemoveButton;
    @FXML DatePicker editCatalogueStartingDate;
    @FXML DatePicker editCatalogueEndingDate;
    @FXML TextArea editCatalogueDescription;

    @FXML Button viewLogButton;
    @FXML Button addCatalogueClearButton;
    @FXML Button editCatalogueClearButton;

    ImageView selectedCatalogueImage;

    // Setting up animation destination, duration, and object
    static final double actionBarWidth = 100.0;
    static final double outOfBoundsAnchorPane = -350.0 - actionBarWidth;
    static final double startOfBoundsAnchorPane = 0.0;
    static final Duration animationDuration = Duration.seconds(0.1);
    TranslateTransition addCataloguePaneOpenAnimation;
    TranslateTransition addCataloguePaneCloseAnimation;
    TranslateTransition editCataloguePaneOpenAnimation;
    TranslateTransition editCataloguePaneCloseAnimation;


    public void initialize(URL url, ResourceBundle rb) {
        // Initializing the animation objects with their corresponding pane
        addCataloguePaneOpenAnimation = new TranslateTransition(animationDuration, addCataloguePane);
        addCataloguePaneCloseAnimation = new TranslateTransition(animationDuration, addCataloguePane);
        editCataloguePaneOpenAnimation = new TranslateTransition(animationDuration, editCataloguePane);
        editCataloguePaneCloseAnimation = new TranslateTransition(animationDuration, editCataloguePane);

        // Hooking up the animation objects with the destinations
        addCataloguePaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        addCataloguePaneCloseAnimation.setToX(startOfBoundsAnchorPane);
        editCataloguePaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        editCataloguePaneCloseAnimation.setToX(startOfBoundsAnchorPane);

        // Populate combo box and set default value
        searchComboBox.getItems().addAll("ID", "Name", "Products", "Discounts",
                "Starting Date", "Ending Date", "Description");
        searchComboBox.setValue("Name");

        // Configure product table columns and items
        catalogueIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("catalogueId"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        userUsernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("userUsername"));
        productsTableColumn.setCellValueFactory(new PropertyValueFactory<>("productsName"));
        discountsTableColumn.setCellValueFactory(new PropertyValueFactory<>("productsDiscount"));
        startingDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        endingDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        catalogueTableView.setItems(Catalogue.catalogues);

        // Disable view log button
        if (LoginController.getInstance().getClass() == Product_Manager.class) {
            viewLogButton.setDisable(true);
        }

        // Initialize suppliers in add and edit product combobox
        addCatalogueProducts.setItems(Product.products);
        addCatalogueProducts.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                try {
                    return product.getName();
                } catch(Exception exception){
                    return "";
                }
            }

            @Override
            public Product fromString(String name) {
                return addCatalogueProducts.getItems().stream().filter(product ->
                        product.getName().equals(name)).findFirst().orElse(null);
            }
        });
        editCatalogueProducts.setItems(Product.products);
        editCatalogueProducts.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                try {
                    return product.getName();
                } catch(Exception exception){
                    return "";
                }
            }

            @Override
            public Product fromString(String name) {
                return editCatalogueProducts.getItems().stream().filter(product ->
                        product.getName().equals(name)).findFirst().orElse(null);
            }
        });

        // Configure selected products table columns
        addCatalogueSelectedProductsNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addCatalogueSelectedProductsDiscountTableColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        editCatalogueSelectedProductsNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        editCatalogueSelectedProductsDiscountTableColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
    }

    public void addCatalogueButton_OnAction(Event event) {
        if (addCataloguePane.getTranslateX() != outOfBoundsAnchorPane) {
            resetPanes();
            addCataloguePaneOpenAnimation.play();
        }
        else {
            addCataloguePaneCloseAnimation.play();
        }
    }

    public void addCatalogueSelectedProductsAddButton_OnAction(Event event) {
        try {
            Product selectedProduct = addCatalogueProducts.getSelectionModel().getSelectedItem();
            if (addCatalogueSelectedProducts.getItems().contains(selectedProduct)) {
                throw new DuplicateException();
            }
            selectedProduct.setDiscount(Double.parseDouble(addCatalogueDiscount.getText()));
            addCatalogueSelectedProducts.getItems().add(selectedProduct);
        } catch (DuplicateException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Product already inside selected products.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (NumberFormatException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Discount must be a number.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void addCatalogueSelectedProductsRemoveButton_OnAction(Event event) {
        try {
            Product selectedProduct = addCatalogueProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                throw new NullPointerException();
            }
            addCatalogueSelectedProducts.getItems().remove(selectedProduct);
            addCatalogueProducts.getItems().add(selectedProduct);
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Product must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void addCatalogueSubmitButton_OnAction (Event event) {
        List<Integer> nameData = new ArrayList<>();
        for (Product product : addCatalogueSelectedProducts.getItems()) {
            nameData.add(product.getProductId());
        }

        List<Double> discountData = new ArrayList<>();
        for (Product product : addCatalogueSelectedProducts.getItems()) {
            discountData.add(product.getDiscount());
        }

        Catalogue newCatalogue = new Catalogue(addCatalogueName.getText(), nameData, discountData,
                addCatalogueStartingDate.getValue(), addCatalogueEndingDate.getValue(), addCatalogueDescription.getText()
        );
        Catalogue.catalogues.add(newCatalogue);
        Log.productLogs.add(new Log("Added catalogue: " + newCatalogue.getName()));
        addCatalogueClearButton.fire();
        addCataloguePaneCloseAnimation.play();
        refreshTableView();
    }

    public void addCatalogueClearButton_OnAction (Event event) {
        addCatalogueName.setText(null);
        addCatalogueProducts.getSelectionModel().clearSelection();
        addCatalogueDiscount.setText(null);
        addCatalogueSelectedProducts.getItems().clear();
        addCatalogueStartingDate.setValue(null);
        addCatalogueEndingDate.setValue(null);
        addCatalogueDescription.setText(null);
    }

    public void addCatalogueCancelButton_OnAction (Event event) {
        addCatalogueClearButton.fire();
        addCataloguePaneCloseAnimation.play();
    }

    public void editCatalogueButton_OnAction(Event event) {
        try {
            Catalogue selectedCatalogue = catalogueTableView.getSelectionModel().getSelectedItem();
            if (selectedCatalogue == null) {
                throw new NullPointerException();
            }

            if (editCataloguePane.getTranslateX() != outOfBoundsAnchorPane) {
                resetPanes();
                editCataloguePaneOpenAnimation.play();
            }
            else {
                editCataloguePaneCloseAnimation.play();
            }

            editCatalogueName.setText(selectedCatalogue.getName());
            int productIdCount = 0;
            for (Integer productId: selectedCatalogue.getProductsId()) {
                for (Product product: Product.products) {
                    if (productId == product.getProductId()) {
                        Product catalogueProduct = product;
                        catalogueProduct.setDiscount(selectedCatalogue.getProductsDiscount().get(productIdCount));
                        editCatalogueSelectedProducts.getItems().add(catalogueProduct);
                    }
                }
                productIdCount++;
            }
            editCatalogueStartingDate.setValue(selectedCatalogue.getDateStart());
            editCatalogueEndingDate.setValue(selectedCatalogue.getDateEnd());
            editCatalogueDescription.setText(selectedCatalogue.getDescription());

            Log.productLogs.add(new Log("Edited catalogue: " + selectedCatalogue.getName()));
            editCataloguePaneCloseAnimation.play();
        }  catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Catalogue must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void editCatalogueSelectedProductsAddButton_OnAction(Event event) {
        try {
            Product selectedProduct = editCatalogueProducts.getSelectionModel().getSelectedItem();
            if (editCatalogueSelectedProducts.getItems().contains(selectedProduct)) {
                throw new DuplicateException();
            }
            selectedProduct.setDiscount(Double.parseDouble(editCatalogueDiscount.getText()));
            editCatalogueSelectedProducts.getItems().add(selectedProduct);
        } catch (DuplicateException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Product already inside selected products.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (NumberFormatException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Discount must be a number.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void editCatalogueSelectedProductsRemoveButton_OnAction(Event event) {
        try {
            Product selectedProduct = editCatalogueSelectedProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                throw new NullPointerException();
            }
            editCatalogueSelectedProducts.getItems().remove(selectedProduct);
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Product must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (Exception exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText(exception.toString());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void editCatalogueSubmitButton_OnAction (Event event) {
        Catalogue selectedCatalogue = catalogueTableView.getSelectionModel().getSelectedItem();

        List<Integer> nameData = new ArrayList<>();
        for (Product product : editCatalogueSelectedProducts.getItems()) {
            nameData.add(product.getProductId());
        }

        List<Double> discountData = new ArrayList<>();
        for (Product product : editCatalogueSelectedProducts.getItems()) {
            discountData.add(product.getDiscount());
        }

        selectedCatalogue.setName(editCatalogueName.getText());
        selectedCatalogue.setProductsId(nameData);
        selectedCatalogue.setProductsDiscount(discountData);
        selectedCatalogue.setDateStart(editCatalogueStartingDate.getValue());
        selectedCatalogue.setDateEnd(editCatalogueEndingDate.getValue());
        selectedCatalogue.setDescription(editCatalogueDescription.getText());

        Log.productLogs.add(new Log("Edited catalogue: " + selectedCatalogue.getName()));
        editCatalogueClearButton.fire();
        editCataloguePaneCloseAnimation.play();
        refreshTableView();
    }

    public void editCatalogueClearButton_OnAction (Event event) {
        editCatalogueName.setText(null);
        editCatalogueProducts.getSelectionModel().clearSelection();
        editCatalogueDiscount.setText(null);
        editCatalogueSelectedProducts.getItems().clear();
        editCatalogueStartingDate.setValue(null);
        editCatalogueEndingDate.setValue(null);
        editCatalogueDescription.setText(null);
    }

    public void editCatalogueCancelButton_OnAction (Event event) {
        editCataloguePaneCloseAnimation.play();
    }

    public void deleteCatalogueButton_OnAction(Event event) {
        try {
            Catalogue selectedCatalogue = catalogueTableView.getSelectionModel().getSelectedItem();
            if (selectedCatalogue == null) {
                throw new NullPointerException();
            }

            Alert confirmationPopup = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete the catalogue permanently?",
                    ButtonType.YES, ButtonType.NO);
            confirmationPopup.showAndWait();

            if (confirmationPopup.getResult() == ButtonType.YES) {
                Catalogue.catalogues.remove(selectedCatalogue);
            }

            Log.productLogs.add(new Log("Deleted catalogue: " + selectedCatalogue.getName()));
            refreshTableView();
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Catalogue must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void exportCatalogueButton_OnAction (Event event) {

    }

    public void viewLogButton_OnAction (Event event) throws IOException {
        Stage logStage = new Stage();
        logStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Log.fxml"))));
        logStage.setWidth(800);
        logStage.setHeight(600);
        logStage.setTitle("Catalogue Log");
        logStage.show();
    }

    public void resetPanes() {
        addCataloguePaneCloseAnimation.play();
        editCataloguePaneCloseAnimation.play();
    }

    private void refreshTableView() {
        catalogueTableView.getColumns().get(0).setVisible(false);
        catalogueTableView.getColumns().get(0).setVisible(true);
    }

    public void searchText_OnChange (Event event) {
        Predicate<Catalogue> searchComboBoxValue = null;
        switch (searchComboBox.getValue().toString()) {
            case "ID":
                searchComboBoxValue = catalogue -> String.valueOf(catalogue.getCatalogueId()).contains(searchText.getText());
                break;
            case "Name":
                searchComboBoxValue = catalogue -> catalogue.getName().contains(searchText.getText());
                break;
            case "Products":
                searchComboBoxValue = catalogue -> String.valueOf(catalogue.getProductsName()).contains(searchText.getText());
                break;
            case "Discounts":
                searchComboBoxValue = catalogue -> String.valueOf(catalogue.getProductsDiscount()).contains(searchText.getText());
                break;
            case "Starting Date":
                searchComboBoxValue = catalogue -> String.valueOf(catalogue.getDateStart()).contains(searchText.getText());
                break;
            case "Ending Date":
                searchComboBoxValue = catalogue -> String.valueOf(catalogue.getDateEnd()).contains(searchText.getText());
                break;
            case "Description":
                searchComboBoxValue = catalogue -> String.valueOf(catalogue.getDescription()).contains(searchText.getText());
                break;
        }
        catalogueTableView.setItems(Catalogue.catalogues.filtered(searchComboBoxValue));
    }
}