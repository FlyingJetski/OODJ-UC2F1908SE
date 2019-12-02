package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.objects.Log;
import model.objects.*;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class HomeAdministratorController implements Initializable {
    @FXML Label loginCountLabel;
    @FXML TableView<Log> loginTableView;
    @FXML TableColumn loginTimestampTableColumn;
    @FXML TableColumn loginUserTableColumn;
    @FXML TableColumn loginActionTableColumn;

    @FXML PieChart userInfoPieChart;
    @FXML TableView<User> userInfoTableView;
    @FXML TableColumn userIdTableColumn;
    @FXML TableColumn usernameTableColumn;
    @FXML TableColumn roleTableColumn;
    @FXML TableColumn userNameTableColumn;
    @FXML TableColumn userAddressTableColumn;
    @FXML TableColumn userContactNumberTableColumn;
    @FXML TableColumn userEmailAddressTableColumn;
    @FXML TableColumn userStatusTableColumn;

    @FXML PieChart supplierInfoPieChart;
    @FXML TableView<Supplier> supplierInfoTableView;
    @FXML TableColumn supplierIdTableColumn;
    @FXML TableColumn nameTableColumn;
    @FXML TableColumn addressTableColumn;
    @FXML TableColumn postCodeTableColumn;
    @FXML TableColumn countryTableColumn;
    @FXML TableColumn stateOrProvinceTableColumn;
    @FXML TableColumn cityTableColumn;
    @FXML TableColumn contactNumberTableColumn;
    @FXML TableColumn emailAddressTableColumn;
    @FXML TableColumn statusTableColumn;

    @FXML PieChart productInfoPieChart;
    @FXML TableView<Product> productInfoTableView;
    @FXML TableColumn productIdTableColumn;
    @FXML TableColumn productNameTableColumn;
    @FXML TableColumn categoryIdTableColumn;
    @FXML TableColumn productSupplierIdTableColumn;
    @FXML TableColumn quantityTableColumn;
    @FXML TableColumn purchasingPriceTableColumn;
    @FXML TableColumn sellingPriceTableColumn;
    @FXML TableColumn profitMarginTableColumn;

    @FXML BarChart<String, Number> catalogueInfoBarChart;
    @FXML Axis catalogueInfoCategoryAxis;
    @FXML Axis catalogueInfoNumberAxis;
    @FXML TableView<Catalogue> catalogueInfoTableView;
    @FXML TableColumn catalogueIdTableColumn;
    @FXML TableColumn catalogueNameTableColumn;
    @FXML TableColumn userUsernameTableColumn;
    @FXML TableColumn productsTableColumn;
    @FXML TableColumn discountsTableColumn;
    @FXML TableColumn startingDateTableColumn;
    @FXML TableColumn endingDateTableColumn;
    @FXML TableColumn descriptionTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configure table columns and table items
        loginTimestampTableColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        loginUserTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        loginActionTableColumn.setCellValueFactory(new PropertyValueFactory<>("action"));

        userIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleTableColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        contactNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        emailAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("statusText"));

        supplierIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        countryTableColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        stateOrProvinceTableColumn.setCellValueFactory(new PropertyValueFactory<>("stateOrProvince"));
        cityTableColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        contactNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        emailAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("statusText"));

        productIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        productSupplierIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchasingPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("purchasingPrice"));
        sellingPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        profitMarginTableColumn.setCellValueFactory(new PropertyValueFactory<>("profitMargin"));

        catalogueIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("catalogueId"));
        catalogueNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        userUsernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("userUsername"));
        productsTableColumn.setCellValueFactory(new PropertyValueFactory<>("productsName"));
        discountsTableColumn.setCellValueFactory(new PropertyValueFactory<>("productsDiscount"));
        startingDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        endingDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        loginTableView.setItems(Log.loginLogs);
        Predicate<Log> loginPredicate = log -> ("" + log.getTimestamp().getDate() + log.getTimestamp().getMonth() +
                log.getTimestamp().getYear()).equals("" + new Date().getDate() + new Date().getMonth() +
                new Date().getYear());
        loginCountLabel.setText(String.valueOf(Log.loginLogs.filtered(loginPredicate).stream().count()));

        Predicate<User> activeUserPredicate = user -> user.getStatus()==true;
        FilteredList<User> activeUser = User.users.filtered(activeUserPredicate);
        long activeUserCount = activeUser.stream().count();
        Predicate<User> inactiveUserPredicate = user -> user.getStatus()==false;
        FilteredList<User> inactiveUser = User.users.filtered(inactiveUserPredicate);
        long inactiveUserCount = inactiveUser.stream().count();
        PieChart.Data activeUserPieChart = new PieChart.Data("Active: " + activeUserCount, activeUserCount);
        PieChart.Data inactiveUserPieChart = new PieChart.Data("Inactive: " + inactiveUserCount, inactiveUserCount);
        ObservableList<PieChart.Data> userInfoData = FXCollections.observableArrayList(activeUserPieChart,
                inactiveUserPieChart);
        userInfoPieChart.setTitle("User Status");
        userInfoPieChart.setLegendVisible(false);
        userInfoPieChart.setData(userInfoData);
        activeUserPieChart.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            userInfoTableView.setItems(activeUser);
        });
        inactiveUserPieChart.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            userInfoTableView.setItems(inactiveUser);
        });

        Predicate<Supplier> activeSupplierPredicate = supplier -> supplier.getStatus()==true;
        FilteredList<Supplier> activeSupplier = Supplier.suppliers.filtered(activeSupplierPredicate);
        long activeSupplierCount = activeSupplier.stream().count();
        Predicate<Supplier> inactiveSupplierPredicate = supplier -> supplier.getStatus()==false;
        FilteredList<Supplier> inactiveSupplier = Supplier.suppliers.filtered(inactiveSupplierPredicate);
        long inactiveSupplierCount = inactiveSupplier.stream().count();
        PieChart.Data activeSupplierPieChart = new PieChart.Data("Active: " + activeSupplierCount, activeSupplierCount);
        PieChart.Data inactiveSupplierPieChart = new PieChart.Data("Inactive: " + inactiveSupplierCount, inactiveSupplierCount);
        ObservableList<PieChart.Data> supplierInfoData = FXCollections.observableArrayList(activeSupplierPieChart,
                                                            inactiveSupplierPieChart);
        supplierInfoPieChart.setTitle("Supplier Status");
        supplierInfoPieChart.setLegendVisible(false);
        supplierInfoPieChart.setData(supplierInfoData);
        activeSupplierPieChart.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            supplierInfoTableView.setItems(activeSupplier);
        });
        inactiveSupplierPieChart.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            supplierInfoTableView.setItems(inactiveSupplier);
        });

        ObservableList<PieChart.Data> productInfoData = FXCollections.observableArrayList();
        for (Category category: Category.categories) {
            Predicate<Product> productCategoryPredicate = product -> product.getCategoryId() == category.getCategoryId();
            FilteredList<Product> filteredProduct = Product.products.filtered(productCategoryPredicate);
            long filteredProductCount = filteredProduct.stream().count();
            PieChart.Data filteredProductPieChart = new PieChart.Data(category.getCategoryName() + ": " +
                    filteredProductCount, filteredProductCount);
            productInfoData.add(filteredProductPieChart);
        }
        productInfoPieChart.setTitle("Product Status");
        productInfoPieChart.setLegendVisible(false);
        productInfoPieChart.setData(productInfoData);
        productInfoData.stream().forEach(pieData -> {
            pieData.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Predicate<Product> filteredProduct= product -> product.getCategoryName().
                        equals(String.valueOf(pieData.getName().split(":")[0]));
                productInfoTableView.setItems(Product.products.filtered(filteredProduct));
            });
        });

        XYChart.Series<String, Number> catalogueInfoData = new XYChart.Series();
        for (User user: User.users) {
            Predicate<Catalogue> catalogueUserPredicate = catalogue -> catalogue.getUserId() == user.getUserId();
            FilteredList<Catalogue> filteredCatalogue = Catalogue.catalogues.filtered(catalogueUserPredicate);
            long filteredCatalogueCount = filteredCatalogue.stream().count();
            XYChart.Data filteredCatalogueBarData = new XYChart.Data(user.getUsername(), filteredCatalogueCount);
            catalogueInfoData.getData().add(filteredCatalogueBarData);
        }
        catalogueInfoBarChart.getData().add(catalogueInfoData);
        for (XYChart.Data<String, Number> barData: catalogueInfoData.getData()){
            barData.getNode().setOnMousePressed((MouseEvent event) -> {
                Predicate<Catalogue> filteredCatalogue = catalogue -> catalogue.getUserUsername().
                        equals(barData.getXValue());
                catalogueInfoTableView.setItems(Catalogue.catalogues.filtered(filteredCatalogue));
            });
        }
    }
}
