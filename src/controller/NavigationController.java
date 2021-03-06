package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.IOWriterReader;
import model.objects.Log;
import model.objects.Administrator;
import model.objects.Product_Manager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {
    private static Button selectedButton;

    @FXML BorderPane contentPane;

    @FXML Button homeButton;
    @FXML Button productButton;
    @FXML Button catalogueButton;
    @FXML Button supplierButton;
    @FXML Button userButton;
    @FXML Button personalInformationButton;
    @FXML Button openInExplorerButton;

    @FXML Label homeLabel;
    @FXML Label productLabel;
    @FXML Label catalogueLabel;
    @FXML Label supplierLabel;
    @FXML Label userLabel;
    @FXML Label personalLabel;
    @FXML Label informationLabel;
    @FXML Label viewDataInLabel;
    @FXML Label explorerLabel;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        homeButton.fire();
    }

    @FXML
    public void homeButton_OnAction(Event event) throws IOException {
        FXMLLoader loader = null;
        if (LoginController.getInstance().getClass() == Administrator.class) {
            loader = new FXMLLoader(getClass().getResource("/view/HomeAdministrator.fxml"));
        } else if (LoginController.getInstance().getClass() == Product_Manager.class) {
            loader = new FXMLLoader(getClass().getResource("/view/HomeProductManager.fxml"));
        }
        resetButtonsAndLabels();
        homeButton.setDisable(true);
        homeLabel.setDisable(true);
        contentPane.setCenter(loader.load());
        contentPane.toBack();
        selectedButton = homeButton;
    }

    @FXML
    public void productButton_OnAction(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Product.fxml"));
        resetButtonsAndLabels();
        productButton.setDisable(true);
        productLabel.setDisable(true);
        contentPane.setCenter(loader.load());
        contentPane.toBack();
        selectedButton = productButton;
    }

    @FXML
    public void catalogueButton_OnAction(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Catalogue.fxml"));
        resetButtonsAndLabels();
        catalogueButton.setDisable(true);
        catalogueLabel.setDisable(true);
        contentPane.setCenter(loader.load());
        contentPane.toBack();
        selectedButton = catalogueButton;
    }

    @FXML
    public void supplierButton_OnAction(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Supplier.fxml"));
        resetButtonsAndLabels();
        supplierButton.setDisable(true);
        supplierLabel.setDisable(true);
        contentPane.setCenter(loader.load());
        contentPane.toBack();
        selectedButton = supplierButton;
    }

    @FXML
    public void userButton_OnAction(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User.fxml"));
        resetButtonsAndLabels();
        userButton.setDisable(true);
        userLabel.setDisable(true);
        contentPane.setCenter(loader.load());
        contentPane.toBack();
        selectedButton = userButton;
    }

    @FXML
    public void personalInformationButton_OnAction(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Personal_Information.fxml"));
        resetButtonsAndLabels();
        personalInformationButton.setDisable(true);
        personalLabel.setDisable(true);
        informationLabel.setDisable(true);
        contentPane.setCenter(loader.load());
        contentPane.toBack();
        selectedButton = personalInformationButton;
    }

    @FXML
    public void openInExplorerButton_OnAction(Event event) throws IOException {
        Desktop.getDesktop().open(new File(IOWriterReader.dataDirectory));
    }

    @FXML
    public void logOutButton_OnAction (Event event) throws IOException {
        Alert confirmationPopup = new Alert(Alert.AlertType.WARNING, "Are you sure you want to log out?",
                ButtonType.YES, ButtonType.NO);
        confirmationPopup.showAndWait();

        if (confirmationPopup.getResult() == ButtonType.YES) {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent loginFXML = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            loginStage.setTitle("Product Catalogue System");
            loginStage.setScene(new Scene(loginFXML));
            loginStage.setWidth(screenBounds.getWidth());
            loginStage.setHeight(screenBounds.getHeight());
            loginStage.setMaximized(true);
            loginStage.setOnCloseRequest(closeEvent -> {
                try {
                    IOWriterReader.onExit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            loginStage.show();
            Log.loginLogs.add(new Log("Logged out"));
        }
    }

    @FXML
    private void resetButtonsAndLabels() {
        // Reset buttons
        homeButton.setDisable(false);
        productButton.setDisable(false);
        catalogueButton.setDisable(false);
        supplierButton.setDisable(false);
        userButton.setDisable(false);
        personalInformationButton.setDisable(false);

        // Reset labels
        homeLabel.setDisable(false);
        productLabel.setDisable(false);
        catalogueLabel.setDisable(false);
        supplierLabel.setDisable(false);
        userLabel.setDisable(false);
        personalLabel.setDisable(false);
        informationLabel.setDisable(false);

        // Disable buttons and labels for product managers
        if (LoginController.getInstance().getClass() == Product_Manager.class) {
            supplierButton.setDisable(true);
            supplierLabel.setDisable(true);
            userButton.setDisable(true);
            userLabel.setDisable(true);
            openInExplorerButton.setVisible(false);
            viewDataInLabel.setVisible(false);
            explorerLabel.setVisible(false);
        }
    }

    public static Button getSelectedButton() {
        return selectedButton;
    }
}
