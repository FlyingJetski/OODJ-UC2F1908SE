package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.objects.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ViewController implements Initializable {
    @FXML AnchorPane productView;
    @FXML ImageView productImageView;
    @FXML Pagination productViewPagination;
    @FXML AnchorPane catalogueView;
    @FXML ImageView catalogueImageView;
    @FXML AnchorPane imageBackgroundPane;
    @FXML Pagination catalogueViewPagination;
    @FXML AnchorPane supplierView;
    @FXML AnchorPane userView;

    @FXML TextField viewProductId;
    @FXML TextField viewProductName;
    @FXML ComboBox<Category> viewProductCategoryId;
    @FXML ComboBox<Supplier> viewProductSupplierId;
    @FXML TextField viewProductQuantity;
    @FXML TextField viewProductPurchasingPrice;
    @FXML TextField viewProductSellingPrice;
    @FXML TextField viewProductProfitMargin;
    @FXML ImageView viewProductImageView;

    @FXML TextField viewCatalogueName;
    @FXML TableView<Product> viewCatalogueSelectedProducts;
    @FXML TableColumn viewCatalogueSelectedProductsNameTableColumn;
    @FXML TableColumn viewCatalogueSelectedProductsDiscountTableColumn;
    @FXML DatePicker viewCatalogueStartingDate;
    @FXML DatePicker viewCatalogueEndingDate;
    @FXML TextArea viewCatalogueDescription;

    @FXML TextField viewSupplierId;
    @FXML TextField viewSupplierName;
    @FXML TextField viewSupplierAddress;
    @FXML TextField viewSupplierPostCode;
    @FXML TextField viewSupplierCountry;
    @FXML TextField viewSupplierStateOrProvince;
    @FXML TextField viewSupplierCity;
    @FXML TextField viewSupplierContactNumber;
    @FXML TextField viewSupplierEmailAddress;
    @FXML TextField viewSupplierStatus;

    @FXML TextField viewUserId;
    @FXML TextField viewUserUsername;
    @FXML ComboBox viewUserRole;
    @FXML TextField viewUserName;
    @FXML TextField viewUserAddress;
    @FXML TextField viewUserContactNumber;
    @FXML TextField viewUserEmailAddress;
    @FXML TextField viewUserStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switch(NavigationController.getSelectedButton().getId()) {
            case "productButton":
                productViewPagination.setPageFactory((pageIndex) -> {
                    if (pageIndex == 0) {
                        return productView;
                    } else {
                        imageBackgroundPane.toFront();
                        productViewPagination.toFront();
                        return productImageView;
                    }
                });

                Product selectedProduct = ProductController.getSelectedProductToView();
                System.out.println(selectedProduct);
                viewProductId.setText(String.valueOf(selectedProduct.getProductId()));
                viewProductName.setText(selectedProduct.getName());
                viewProductCategoryId.setConverter(new StringConverter<Category>() {
                    @Override
                    public String toString(Category category) {
                        try {
                            return category.getCategoryName();
                        } catch(Exception exception){
                            return "";
                        }
                    }

                    @Override
                    public Category fromString(String categoryName) {
                        return viewProductCategoryId.getItems().stream().filter(category ->
                                category.getCategoryName().equals(categoryName)).findFirst().orElse(null);
                    }
                });
                Predicate<Category> categoryPredicate = category -> category.getCategoryId() == selectedProduct.getCategoryId();
                viewProductCategoryId.setValue(Category.categories.filtered(categoryPredicate).get(0));
                viewProductSupplierId.setConverter(new StringConverter<Supplier>() {
                    @Override
                    public String toString(Supplier supplier) {
                        try {
                            return supplier.getName();
                        } catch(Exception exception){
                            return "";
                        }
                    }

                    @Override
                    public Supplier fromString(String supplierName) {
                        return viewProductSupplierId.getItems().stream().filter(supplier ->
                                supplier.getName().equals(supplierName)).findFirst().orElse(null);
                    }
                });
                Predicate<Supplier> supplierPredicate = supplier -> supplier.getSupplierId() == selectedProduct.getSupplierId();
                viewProductSupplierId.setValue(Supplier.suppliers.filtered(supplierPredicate).get(0));
                viewProductQuantity.setText(String.valueOf(selectedProduct.getQuantity()));
                viewProductPurchasingPrice.setText(String.valueOf(selectedProduct.getPurchasingPrice()));
                viewProductSellingPrice.setText(String.valueOf(selectedProduct.getSellingPrice()));
                viewProductProfitMargin.setText(String.valueOf(selectedProduct.getProfitMargin()));
                try {
                    Image loadedImage = new Image("/data/product images/" + selectedProduct.getProductId());
                    viewProductImageView.setImage(loadedImage);
                    productImageView.setImage(loadedImage);
                } catch (IllegalArgumentException exception) {
                    Image placeholderImage = new Image("/images/Product_Image_Placeholder.png");
                    viewProductImageView.setImage(placeholderImage);
                    productImageView.setImage(placeholderImage);
                }

                productViewPagination.toFront();
                break;

            case "catalogueButton":
                catalogueViewPagination.setPageFactory((pageIndex) -> {
                    if (pageIndex == 0) {
                        return catalogueView;
                    } else {
                        imageBackgroundPane.toFront();
                        catalogueViewPagination.toFront();
                        return catalogueImageView;
                    }
                });

                Catalogue selectedCatalogue = CatalogueController.getSelectedCatalogueToView();
                viewCatalogueName.setText(selectedCatalogue.getName());
                int productIdCount = 0;
                for (Integer productId: selectedCatalogue.getProductsId()) {
                    for (Product product: Product.products) {
                        if (productId == product.getProductId()) {
                            Product catalogueProduct = product;
                            catalogueProduct.setDiscount(selectedCatalogue.getProductsDiscount().get(productIdCount));
                            viewCatalogueSelectedProducts.getItems().add(catalogueProduct);
                        }
                    }
                    productIdCount++;
                }
                viewCatalogueStartingDate.setValue(selectedCatalogue.getDateStart());
                viewCatalogueEndingDate.setValue(selectedCatalogue.getDateEnd());
                viewCatalogueDescription.setText(selectedCatalogue.getDescription());

                try {
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

                    PDFRenderer pdfRenderer = new PDFRenderer(document);
                    int pageIndex = 0;
                    int dpi = 300;

                    BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(pageIndex, dpi, ImageType.RGB);
                    Image catalogueImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    catalogueImageView.setImage(catalogueImage);
                    document.close();
                } catch (IOException exception) {

                }

                catalogueViewPagination.toFront();
                break;

            case "supplierButton":
                Supplier selectedSupplier = SupplierController.getSelectedSupplierToView();
                viewSupplierId.setText(String.valueOf(selectedSupplier.getSupplierId()));
                viewSupplierName.setText(selectedSupplier.getName());
                viewSupplierAddress.setText(selectedSupplier.getAddress());
                viewSupplierPostCode.setText(selectedSupplier.getPostCode());
                viewSupplierCountry.setText(selectedSupplier.getCountry());
                viewSupplierStateOrProvince.setText(selectedSupplier.getStateOrProvince());
                viewSupplierCity.setText(selectedSupplier.getCity());
                viewSupplierContactNumber.setText(selectedSupplier.getContactNumber());
                viewSupplierEmailAddress.setText(selectedSupplier.getEmailAddress());
                viewSupplierStatus.setText(selectedSupplier.getStatusText());
                supplierView.toFront();
                break;

            case "userButton":
                User selectedUser = UserController.getSelectedUserToView();
                viewUserId.setText(String.valueOf(selectedUser.getUserId()));
                viewUserUsername.setText(selectedUser.getUsername());
                viewUserRole.setValue(selectedUser.getRole());
                viewUserName.setText(selectedUser.getName());
                viewUserAddress.setText(selectedUser.getAddress());
                viewUserContactNumber.setText(selectedUser.getContactNumber());
                viewUserEmailAddress.setText(selectedUser.getEmailAddress());
                viewUserStatus.setText(selectedUser.getStatusText());
                userView.toFront();
                break;
        }
    }
}
