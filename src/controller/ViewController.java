package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import model.objects.Category;
import model.objects.Product;
import model.objects.Supplier;
import model.objects.User;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ViewController implements Initializable {
    @FXML AnchorPane productView;
    @FXML AnchorPane catalogueView;
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

    /*@FXML TextField viewCatalogueName;
    @FXML ComboBox<Product> viewCatalogueProducts;
    @FXML TextField viewCatalogueDiscount;
    @FXML TableView<Product> viewCatalogueSelectedProducts;
    @FXML TableColumn viewCatalogueSelectedProductsNameTableColumn;
    @FXML TableColumn viewCatalogueSelectedProductsDiscountTableColumn;
    @FXML Button viewCatalogueSelectedProductsAddButton;
    @FXML Button viewCatalogueSelectedProductsRemoveButton;
    @FXML DatePicker viewCatalogueStartingDate;
    @FXML DatePicker viewCatalogueEndingDate;
    @FXML TextArea viewCatalogueDescription;*/

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
    @FXML TextField viewUserPassword;
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
                productView.toFront();
                break;

            case "catalogueButton":
                catalogueView.toFront();
                break;

            case "supplierButton":
                Supplier selectedSupplier = SupplierController.getSelectedSupplierToView();
                System.out.println(selectedSupplier);
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
