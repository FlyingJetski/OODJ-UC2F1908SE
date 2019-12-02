package controller;

import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.DuplicateException;
import model.NullValueException;
import model.objects.Log;
import model.objects.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.util.Matrix;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class CatalogueController implements Initializable {
    private static Catalogue selectedCatalogueToView = null;

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
        catalogueTableView.setRowFactory( tableView -> {
            TableRow<Catalogue> tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!tableRow.isEmpty()) ) {
                    Catalogue selectedCatalogue = tableRow.getItem();
                    selectedCatalogueToView = selectedCatalogue;
                    Stage viewStage = new Stage();
                    try {
                        viewStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/View.fxml"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    viewStage.setWidth(400);
                    viewStage.setHeight(500);
                    viewStage.setTitle("Catalogue View");
                    viewStage.setResizable(false);
                    viewStage.show();
                }
            });
            return tableRow;
        });

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
        try {
            for (Catalogue catalogue: Catalogue.catalogues) {
                if (catalogue.getName().equals(addCatalogueName.getText())) {
                    throw new DuplicateException();
                }
            }
            if (addCatalogueStartingDate.getValue() == null || addCatalogueEndingDate.getValue() == null) {
                throw new NullValueException.Date();
            }

            List<Integer> nameData = new ArrayList<>();
            for (Product product : addCatalogueSelectedProducts.getItems()) {
                nameData.add(product.getProductId());
            }

            List<Double> discountData = new ArrayList<>();
            for (Product product : addCatalogueSelectedProducts.getItems()) {
                discountData.add(product.getDiscount());
            }

            if (nameData == null) {
                throw new NullValueException.Product();
            }

            if (addCatalogueName.getText() == null || nameData == null || discountData == null ||
                    addCatalogueStartingDate.getValue() == null || addCatalogueEndingDate.getValue() == null ||
                    addCatalogueDescription.getText() == null) {
                throw new NullValueException();
            }
            Catalogue newCatalogue = new Catalogue(addCatalogueName.getText(), nameData, discountData,
                    addCatalogueStartingDate.getValue(), addCatalogueEndingDate.getValue(), addCatalogueDescription.getText()
            );
            Catalogue.catalogues.add(newCatalogue);
            Log.catalogueLogs.add(new Log("Added catalogue: " + newCatalogue.getName()));
            addCatalogueClearButton.fire();
            addCataloguePaneCloseAnimation.play();
            refreshTableView();
        } catch (DuplicateException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Catalogue name has already been used.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (NullValueException.Date exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Starting and ending date must be a date.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (NullValueException.Product exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("There must be selected products.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (NullValueException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("All fields must be filled.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
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
        try {
            Catalogue selectedCatalogue = catalogueTableView.getSelectionModel().getSelectedItem();
            for (Catalogue catalogue: Catalogue.catalogues) {
                if (catalogue.getName().equals(editCatalogueName.getText()) &&
                        !editCatalogueName.getText().equals(selectedCatalogue.getName())) {
                    throw new DuplicateException();
                }
            }
            if (editCatalogueStartingDate.getValue() == null || editCatalogueEndingDate.getValue() == null) {
                throw new NullValueException.Date();
            }

            List<Integer> nameData = new ArrayList<>();
            for (Product product : editCatalogueSelectedProducts.getItems()) {
                nameData.add(product.getProductId());
            }

            List<Double> discountData = new ArrayList<>();
            for (Product product : editCatalogueSelectedProducts.getItems()) {
                discountData.add(product.getDiscount());
            }

            if (nameData == null) {
                throw new NullValueException.Product();
            }

            if (editCatalogueName.getText() == null || nameData == null || discountData == null ||
                    editCatalogueStartingDate.getValue() == null || editCatalogueEndingDate.getValue() == null ||
                    editCatalogueDescription.getText() == null) {
                throw new NullValueException();
            }

            selectedCatalogue.setName(editCatalogueName.getText());
            selectedCatalogue.setProductsId(nameData);
            selectedCatalogue.setProductsDiscount(discountData);
            selectedCatalogue.setDateStart(editCatalogueStartingDate.getValue());
            selectedCatalogue.setDateEnd(editCatalogueEndingDate.getValue());
            selectedCatalogue.setDescription(editCatalogueDescription.getText());

            Log.catalogueLogs.add(new Log("Edited catalogue: " + selectedCatalogue.getName()));
            editCatalogueClearButton.fire();
            editCataloguePaneCloseAnimation.play();
            refreshTableView();
        } catch (DuplicateException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Catalogue name has already been used.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (NullValueException.Date exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Starting and ending date must be a date.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (NullValueException.Product exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("There must be selected products.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (NullValueException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("All fields must be filled.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
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

            Log.catalogueLogs.add(new Log("Deleted catalogue: " + selectedCatalogue.getName()));
            refreshTableView();
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Catalogue must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void exportCatalogueButton_OnAction (Event event) throws IOException {
        try {
            Catalogue selectedCatalogue = catalogueTableView.getSelectionModel().getSelectedItem();
            if (selectedCatalogue == null) {
                throw new NullPointerException();
            }

            ButtonType pdfButton = new ButtonType("PDF (document)", ButtonBar.ButtonData.OK_DONE);
            ButtonType pngButton = new ButtonType("PNG (image)", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert confirmationPopup = new Alert(Alert.AlertType.INFORMATION, "What format would you like to export the catalogue to?",
                    pdfButton, pngButton);
            confirmationPopup.showAndWait();

            if (confirmationPopup.getResult() == pdfButton) {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                PDPageContentStream content = new PDPageContentStream(document, page);
                PDFont font = PDType1Font.HELVETICA;
                PDFont fontBold = PDType1Font.HELVETICA_BOLD;
                int titleFont = 48;
                int contentFont = 32;
                int descriptionFont = 20;

                content.beginText();
                content.setLeading(50.0f);
                content.newLineAtOffset(25, 700);

                content.setFont(font, titleFont);
                String titleString = selectedCatalogue.getName();
                content.showText(titleString);
                content.newLine();

                content.setFont(font, descriptionFont);
                String descriptionString = selectedCatalogue.getDescription();
                content.showText(descriptionString);
                content.newLine();
                content.newLine();

                content.setFont(font, contentFont);
                int productsIndexCount = 0;
                for (Integer productId: selectedCatalogue.getProductsId()) {
                    Predicate<Product> productPredicate = product -> product.getProductId() == productId;
                    String productName = Product.products.filtered(productPredicate).get(0).getName();

                    String contentString = productName + " - " +
                            selectedCatalogue.getProductsDiscount().get(productsIndexCount) + "% OFF";
                    content.showText(contentString);
                    content.newLine();
                    productsIndexCount++;
                }
                content.newLine();

                content.setFont(font, contentFont);
                String contentString = "ONLY FROM";
                content.showText(contentString);
                content.newLine();

                content.setFont(fontBold, titleFont);
                titleString = selectedCatalogue.getDateStart().toString();
                content.showText(titleString);
                content.newLine();

                content.setFont(font, contentFont);
                contentString = "UNTIL";
                content.showText(contentString);
                content.newLine();

                content.setFont(fontBold, titleFont);
                titleString = selectedCatalogue.getDateEnd().toString() + "!!";
                content.showText(titleString);
                content.newLine();

                content.endText();
                content.close();

                document.addPage(page);

                Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                final DirectoryChooser directoryChooser = new DirectoryChooser();
                File exportDirectory = directoryChooser.showDialog(mainStage);

                document.save(exportDirectory + "/" + selectedCatalogue.getName() + ".pdf");
                document.close();
                Log.catalogueLogs.add(new Log("Exported catalogue as pdf: " + selectedCatalogue.getName()));
            } else if (confirmationPopup.getResult() == pngButton) {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                PDPageContentStream content = new PDPageContentStream(document, page);
                PDFont font = PDType1Font.HELVETICA;
                PDFont fontBold = PDType1Font.HELVETICA_BOLD;
                int titleFont = 48;
                int contentFont = 32;
                int descriptionFont = 20;

                content.beginText();
                content.setLeading(50.0f);
                content.newLineAtOffset(25, 700);

                content.setFont(font, titleFont);
                String titleString = selectedCatalogue.getName();
                content.showText(titleString);
                content.newLine();

                content.setFont(font, descriptionFont);
                String descriptionString = selectedCatalogue.getDescription();
                content.showText(descriptionString);
                content.newLine();
                content.newLine();

                content.setFont(font, contentFont);
                int productsIndexCount = 0;
                for (Integer productId: selectedCatalogue.getProductsId()) {
                    Predicate<Product> productPredicate = product -> product.getProductId() == productId;
                    String productName = Product.products.filtered(productPredicate).get(0).getName();

                    String contentString = productName + " - " +
                            selectedCatalogue.getProductsDiscount().get(productsIndexCount) + "% OFF";
                    content.showText(contentString);
                    content.newLine();
                    productsIndexCount++;
                }
                content.newLine();

                content.setFont(font, contentFont);
                String contentString = "ONLY FROM";
                content.showText(contentString);
                content.newLine();

                content.setFont(fontBold, titleFont);
                titleString = selectedCatalogue.getDateStart().toString();
                content.showText(titleString);
                content.newLine();

                content.setFont(font, contentFont);
                contentString = "UNTIL";
                content.showText(contentString);
                content.newLine();

                content.setFont(fontBold, titleFont);
                titleString = selectedCatalogue.getDateEnd().toString() + "!!";
                content.showText(titleString);
                content.newLine();

                content.endText();
                content.close();

                document.addPage(page);

                Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                final DirectoryChooser directoryChooser = new DirectoryChooser();
                File exportDirectory = directoryChooser.showDialog(mainStage);

                PDFRenderer pdfRenderer = new PDFRenderer(document);
                int pageIndex = 0;
                int dpi = 300;

                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(pageIndex, dpi, ImageType.RGB);
                File outputFile = new File(exportDirectory + "/" + selectedCatalogue.getName() + ".png");
                ImageIO.write(bufferedImage, "png", outputFile);
                document.close();
                Log.catalogueLogs.add(new Log("Exported catalogue as png: " + selectedCatalogue.getName()));
            }
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Catalogue must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
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

    public static Catalogue getSelectedCatalogueToView() {
        return selectedCatalogueToView;
    }
}