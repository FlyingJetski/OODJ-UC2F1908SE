package controller;

import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.Log;
import model.objects.Catalogue;
import model.objects.Product;
import model.objects.Product_Manager;
import model.objects.Supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CatalogueController implements Initializable {
    @FXML AnchorPane addCataloguePane;
    @FXML AnchorPane editCataloguePane;
    @FXML FlowPane catalogueFlowPane;

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

        // Disable view log button
        if (LoginController.getInstance().getClass() == Product_Manager.class) {
            viewLogButton.setDisable(true);
        }

        // Initialize suppliers in add and edit product combobox
        addCatalogueProducts.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                try {
                    return product.getProductName();
                } catch(Exception exception){
                    return "";
                }
            }

            @Override
            public Product fromString(String productName) {
                return addCatalogueProducts.getItems().stream().filter(product ->
                        product.getProductName().equals(productName)).findFirst().orElse(null);
            }
        });
        editCatalogueProducts.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                try {
                    return product.getProductName();
                } catch(Exception exception){
                    return "";
                }
            }

            @Override
            public Product fromString(String productName) {
                return editCatalogueProducts.getItems().stream().filter(product ->
                        product.getProductName().equals(productName)).findFirst().orElse(null);
            }
        });

        // Configure selected products table columns
        addCatalogueSelectedProductsNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addCatalogueSelectedProductsDiscountTableColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        editCatalogueSelectedProductsNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        editCatalogueSelectedProductsDiscountTableColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        /*ImageView imageView = new ImageView();
        imageView.setFitWidth(800.0);
        imageView.setFitHeight(400.0);
        Image image = new Image("/images/Catalogue.png");
        imageView.setImage(image);
        catalogueFlowPane.getChildren().add(imageView);*/
    }

    public void addCatalogueButton_OnAction(Event event) {
        if (addCataloguePane.getTranslateX() != outOfBoundsAnchorPane) {
            resetPanes();
            addCataloguePaneOpenAnimation.play();
        }
        else {
            addCataloguePaneCloseAnimation.play();
        }

        addCatalogueProducts.setItems(Product.products);
        List<Product> addCatalogueProductsList = addCatalogueProducts.getItems();
        ObservableList<Product> add = FXCollections.observableList(addCatalogueProductsList);
        addCatalogueProducts.setItems(add);
    }

    public void addCatalogueSelectedProductsAddButton_OnAction(Event event) {
            Product selectedProduct = addCatalogueProducts.getSelectionModel().getSelectedItem();
            selectedProduct.setDiscount(Double.parseDouble(addCatalogueDiscount.getText()));
            addCatalogueSelectedProducts.getItems().add(selectedProduct);
            addCatalogueProducts.getItems().remove(selectedProduct);

            List<String> columnNameData = new ArrayList<>();
            for (Product product : addCatalogueSelectedProducts.getItems()) {
                columnNameData.add(product.getProductName());
            }

            List<Double> columnDiscountData = new ArrayList<>();
            for (Product product : addCatalogueSelectedProducts.getItems()) {
                columnDiscountData.add(product.getDiscount());
            }

            System.out.println(columnNameData);
            System.out.println(columnDiscountData);
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
//        Catalogue newCatalogue = new Catalogue(addCatalogueName.getText(), , );
    }

    public void addCatalogueClearButton_OnAction (Event event) {

    }

    public void addCatalogueCancelButton_OnAction (Event event) {

    }

    public void editCatalogueButton_OnAction(Event event) {
        /*try {
            Catalogue selectedProduct = catalogueTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                throw new NullPointerException();
            }

            if (editCataloguePane.getTranslateX() != outOfBoundsAnchorPane) {
                resetPanes();
                editCataloguePaneOpenAnimation.play();
            }
            else {
                editCataloguePaneCloseAnimation.play();
            }

            ObservableList<Product> editCatalogueProducts = Product.products.filtered(product -> true);

            editProductProductName.setText(selectedProduct.getProductName());
            Predicate<Category> categoryPredicate = category -> category.getCategoryId() == selectedProduct.getCategoryId();
            editProductCategoryId.setValue(Category.categories.filtered(categoryPredicate).get(0));
            Predicate<Supplier> supplierPredicate = supplier -> supplier.getSupplierId() == selectedProduct.getSupplierId();
            editProductSupplierId.setValue(Supplier.suppliers.filtered(supplierPredicate).get(0));
            editProductQuantity.setText(String.valueOf(selectedProduct.getQuantity()));
            editProductPurchasingPrice.setText(String.valueOf(selectedProduct.getPurchasingPrice()));
            editProductSellingPrice.setText(String.valueOf(selectedProduct.getSellingPrice()));

            Log.productLogs.add(new Log("Edited product: " + selectedProduct.getProductName()));
            editProductPaneCloseAnimation.play();
        }  catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Product must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }*/
    }

    public void editCatalogueSelectedProductsAddButton_OnAction(Event event) {

    }

    public void editCatalogueSelectedProductsRemoveButton_OnAction(Event event) {

    }

    public void editCatalogueSubmitButton_OnAction (Event event) {

    }

    public void editCatalogueClearButton_OnAction (Event event) {

    }

    public void editCatalogueCancelButton_OnAction (Event event) {

    }

    public void deleteCatalogueButton_OnAction(Event event) {
        Alert confirmationPopup = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete the catalogue permanently?",
                ButtonType.YES, ButtonType.NO);
        confirmationPopup.showAndWait();

        if (confirmationPopup.getResult() == ButtonType.YES) {
            //do stuff
            Log.catalogueLogs.add(new Log("Deleted catalogue: " + "placeholder"));
        }
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
}