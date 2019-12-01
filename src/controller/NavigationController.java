package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import model.IOWriterReader;
import model.objects.Product_Manager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {
    public static Button selectedButton = null;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
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

    public void openInExplorerButton_OnAction(Event event) throws IOException {
        Desktop.getDesktop().open(new File(IOWriterReader.dataDirectory));
    }
}
