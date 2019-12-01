package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.objects.Log;

import java.net.URL;
import java.util.ResourceBundle;

public class LogController implements Initializable {
    @FXML TableView<Log> loginTableView;
    @FXML TableColumn loginTimestampTableColumn;
    @FXML TableColumn loginUserTableColumn;
    @FXML TableColumn loginActionTableColumn;
    @FXML TableView<Log> productTableView;
    @FXML TableColumn productTimestampTableColumn;
    @FXML TableColumn productUserTableColumn;
    @FXML TableColumn productActionTableColumn;
    @FXML TableView<Log> catalogueTableView;
    @FXML TableColumn catalogueTimestampTableColumn;
    @FXML TableColumn catalogueUserTableColumn;
    @FXML TableColumn catalogueActionTableColumn;
    @FXML TableView<Log> supplierTableView;
    @FXML TableColumn supplierTimestampTableColumn;
    @FXML TableColumn supplierUserTableColumn;
    @FXML TableColumn supplierActionTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PropertyValueFactory timestamp = new PropertyValueFactory<>("timestamp");
        PropertyValueFactory user = new PropertyValueFactory<>("username");
        PropertyValueFactory action = new PropertyValueFactory<>("action");
        switch(NavigationController.getSelectedButton().getId()) {
            case "productButton":
                productTimestampTableColumn.setCellValueFactory(timestamp);
                productUserTableColumn.setCellValueFactory(user);
                productActionTableColumn.setCellValueFactory(action);
                productTableView.setItems(Log.productLogs);
                productTableView.toFront();
                break;
            case "catalogueButton":
                catalogueTimestampTableColumn.setCellValueFactory(timestamp);
                catalogueUserTableColumn.setCellValueFactory(user);
                catalogueActionTableColumn.setCellValueFactory(action);
                catalogueTableView.setItems(Log.catalogueLogs);
                catalogueTableView.toFront();
                break;
            case "supplierButton":
                supplierTimestampTableColumn.setCellValueFactory(timestamp);
                supplierUserTableColumn.setCellValueFactory(user);
                supplierActionTableColumn.setCellValueFactory(action);
                supplierTableView.setItems(Log.supplierLogs);
                supplierTableView.toFront();
                break;
            case "userButton":
                loginTimestampTableColumn.setCellValueFactory(timestamp);
                loginUserTableColumn.setCellValueFactory(user);
                loginActionTableColumn.setCellValueFactory(action);
                loginTableView.setItems(Log.loginLogs);
                loginTableView.toFront();
                break;
        }
    }
}
