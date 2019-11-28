package controller;

import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Log;
import model.objects.Supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SupplierController implements Initializable {
    @FXML AnchorPane addSupplierPane;
    @FXML AnchorPane changeStatusPane;
    @FXML AnchorPane editSupplierPane;
    @FXML AnchorPane deleteSupplierPane;

    @FXML ComboBox searchComboBox;
    @FXML TextField searchText;
    @FXML TableView<Supplier> supplierTableView;
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

    @FXML TextField addSupplierName;
    @FXML TextField addSupplierAddress;
    @FXML TextField addSupplierPostCode;
    @FXML TextField addSupplierCountry;
    @FXML TextField addSupplierStateOrProvince;
    @FXML TextField addSupplierCity;
    @FXML TextField addSupplierContactNumber;
    @FXML TextField addSupplierEmailAddress;

    @FXML TextField editSupplierName;
    @FXML TextField editSupplierAddress;
    @FXML TextField editSupplierPostCode;
    @FXML TextField editSupplierCountry;
    @FXML TextField editSupplierStateOrProvince;
    @FXML TextField editSupplierCity;
    @FXML TextField editSupplierContactNumber;
    @FXML TextField editSupplierEmailAddress;

    @FXML Button addSupplierClearButton;
    @FXML Button editSupplierClearButton;

    // Setting up animation destination, duration, and object
    static final double actionBarWidth = 100.0;
    static final double outOfBoundsAnchorPane = -350.0 - actionBarWidth;
    static final double startOfBoundsAnchorPane = 0.0;
    static final Duration animationDuration = Duration.seconds(0.1);
    TranslateTransition addSupplierPaneOpenAnimation;
    TranslateTransition addSupplierPaneCloseAnimation;
    TranslateTransition editSupplierPaneOpenAnimation;
    TranslateTransition editSupplierPaneCloseAnimation;


    public void initialize(URL url, ResourceBundle rb) {
        // Initializing the animation objects with their corresponding pane
        addSupplierPaneOpenAnimation = new TranslateTransition(animationDuration, addSupplierPane);
        addSupplierPaneCloseAnimation = new TranslateTransition(animationDuration, addSupplierPane);
        editSupplierPaneOpenAnimation = new TranslateTransition(animationDuration, editSupplierPane);
        editSupplierPaneCloseAnimation = new TranslateTransition(animationDuration, editSupplierPane);

        // Hooking up the animation objects with the destinations
        addSupplierPaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        addSupplierPaneCloseAnimation.setToX(startOfBoundsAnchorPane);
        editSupplierPaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        editSupplierPaneCloseAnimation.setToX(startOfBoundsAnchorPane);

        // Populate combo box and set default value
        searchComboBox.getItems().addAll("Supplier ID", "Name", "Address", "Post Code", "Country",
                                            "State/Province", "City", "Contact Number", "Email Address");
        searchComboBox.setValue("Name");

        // Configure table columns and table items
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
        supplierTableView.setItems(Supplier.suppliers);
    }

    public void addSupplierButton_OnAction(Event event) {
        if (addSupplierPane.getTranslateX() != outOfBoundsAnchorPane) {
            resetPanes();
            addSupplierPaneOpenAnimation.play();
        }
        else {
            addSupplierPaneCloseAnimation.play();
        }
    }

    public void addSupplierSubmitButton_OnAction (Event event) throws IOException {
        Supplier newSupplier = new Supplier(addSupplierName.getText(), addSupplierAddress.getText(),
                                            addSupplierPostCode.getText(), addSupplierCountry.getText(),
                                            addSupplierStateOrProvince.getText(), addSupplierCity.getText(),
                                            addSupplierContactNumber.getText(), addSupplierEmailAddress.getText());
        Supplier.suppliers.add(newSupplier);
        Log.supplierLogs.add(new Log("Added supplier: " + newSupplier.getName()));
        addSupplierClearButton.fire();
        addSupplierPaneCloseAnimation.play();
        refreshTableView();
    }

    public void addSupplierClearButton_OnAction (Event event) {
        addSupplierName.setText(null);
        addSupplierAddress.setText(null);
        addSupplierPostCode.setText(null);
        addSupplierCountry.setText(null);
        addSupplierStateOrProvince.setText(null);
        addSupplierCity.setText(null);
        addSupplierContactNumber.setText(null);
        addSupplierEmailAddress.setText(null);
    }

    public void addSupplierCancelButton_OnAction (Event event) {
        addSupplierClearButton.fire();
        addSupplierPaneCloseAnimation.play();
    }

    public void changeStatusButton_OnAction(Event event) {
        try {
            Supplier selectedSupplier = supplierTableView.getSelectionModel().getSelectedItem();
            if (selectedSupplier == null) {
                throw new NullPointerException();
            }

            Alert confirmationPopup = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the status?",
                    ButtonType.YES, ButtonType.NO);
            confirmationPopup.showAndWait();

            if (confirmationPopup.getResult() == ButtonType.YES) {
                selectedSupplier.setStatus(!selectedSupplier.getStatus());
            }

            Log.supplierLogs.add(new Log("Changed supplier status: " + selectedSupplier.getName()));
            refreshTableView();
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Supplier must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void editSupplierButton_OnAction(Event event) {
        try {
            Supplier selectedSupplier = supplierTableView.getSelectionModel().getSelectedItem();
            if (selectedSupplier == null) {
                throw new NullPointerException();
            }

            if (editSupplierPane.getTranslateX() != outOfBoundsAnchorPane) {
                resetPanes();
                editSupplierPaneOpenAnimation.play();
            }
            else {
                editSupplierPaneCloseAnimation.play();
            }

            editSupplierName.setText(selectedSupplier.getName());
            editSupplierAddress.setText(selectedSupplier.getAddress());
            editSupplierPostCode.setText(selectedSupplier.getPostCode());
            editSupplierCountry.setText(selectedSupplier.getCountry());
            editSupplierStateOrProvince.setText(selectedSupplier.getStateOrProvince());
            editSupplierCity.setText(selectedSupplier.getCity());
            editSupplierContactNumber.setText(selectedSupplier.getContactNumber());
            editSupplierEmailAddress.setText(selectedSupplier.getEmailAddress());
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Supplier must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }

    }

    public void editSupplierSubmitButton_OnAction (Event event) {
        Supplier selectedSupplier = supplierTableView.getSelectionModel().getSelectedItem();
        selectedSupplier.setName(editSupplierName.getText());
        selectedSupplier.setAddress(editSupplierAddress.getText());
        selectedSupplier.setPostCode(editSupplierPostCode.getText());
        selectedSupplier.setCountry(editSupplierCountry.getText());
        selectedSupplier.setStateOrProvince(editSupplierStateOrProvince.getText());
        selectedSupplier.setCity(editSupplierCity.getText());
        selectedSupplier.setContactNumber(editSupplierContactNumber.getText());
        selectedSupplier.setEmailAddress(editSupplierEmailAddress.getText());

        Log.supplierLogs.add(new Log("Edited supplier: " + selectedSupplier.getName()));
        editSupplierPaneCloseAnimation.play();
        refreshTableView();
    }

    public void editSupplierClearButton_OnAction (Event event) {
        editSupplierName.setText(null);
        editSupplierAddress.setText(null);
        editSupplierPostCode.setText(null);
        editSupplierCountry.setText(null);
        editSupplierStateOrProvince.setText(null);
        editSupplierCity.setText(null);
        editSupplierContactNumber.setText(null);
        editSupplierEmailAddress.setText(null);
    }

    public void editSupplierCancelButton_OnAction (Event event) {
        editSupplierPaneCloseAnimation.play();
    }

    public void deleteSupplierButton_OnAction(Event event) {
        try {
            Supplier selectedSupplier = supplierTableView.getSelectionModel().getSelectedItem();
            if (selectedSupplier == null) {
                throw new NullPointerException();
            }

            Alert confirmationPopup = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete the supplier permanently?",
                    ButtonType.YES, ButtonType.NO);
            confirmationPopup.showAndWait();

            if (confirmationPopup.getResult() == ButtonType.YES) {
                Supplier.suppliers.remove(selectedSupplier);
            }
            Log.supplierLogs.add(new Log("Deleted supplier: " + selectedSupplier.getName()));
            refreshTableView();
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("User must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void viewLogButton_OnAction (Event event) throws IOException {
        Stage logStage = new Stage();
        logStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Log.fxml"))));
        logStage.setWidth(800);
        logStage.setHeight(600);
        logStage.setTitle("Supplier Log");
        logStage.show();
    }

    public void resetPanes() {
        addSupplierPaneCloseAnimation.play();
        editSupplierPaneCloseAnimation.play();
    }

    private void refreshTableView() {
        supplierTableView.getColumns().get(0).setVisible(false);
        supplierTableView.getColumns().get(0).setVisible(true);
    }

    public void searchText_OnChange (Event event) {
        Predicate<Supplier> searchComboBoxValue = null;
        switch (searchComboBox.getValue().toString()) {
            case "Supplier ID":
                searchComboBoxValue = supplier -> String.valueOf(supplier.getSupplierId()).contains(searchText.getText());
                break;
            case "Name":
                searchComboBoxValue = supplier -> supplier.getName().contains(searchText.getText());
                break;
            case "Address":
                searchComboBoxValue = supplier -> supplier.getAddress().contains(searchText.getText());
                break;
            case "Post Code":
                searchComboBoxValue = supplier -> supplier.getPostCode().contains(searchText.getText());
                break;
            case "Country":
                searchComboBoxValue = supplier -> supplier.getCountry().contains(searchText.getText());
                break;
            case "State/Province":
                searchComboBoxValue = supplier -> supplier.getStateOrProvince().contains(searchText.getText());
                break;
            case "City":
                searchComboBoxValue = supplier -> supplier.getCity().contains(searchText.getText());
                break;
            case "Contact Number":
                searchComboBoxValue = supplier -> supplier.getContactNumber().contains(searchText.getText());
                break;
            case "Email Address":
                searchComboBoxValue = supplier -> supplier.getEmailAddress().contains(searchText.getText());
                break;
        }
        supplierTableView.setItems(Supplier.suppliers.filtered(searchComboBoxValue));
    }
}
