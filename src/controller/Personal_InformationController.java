package controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Authentication;
import model.Log;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class Personal_InformationController implements Initializable {
    @FXML AnchorPane editInformationPane;
    @FXML AnchorPane changePasswordPane;
    @FXML AnchorPane changeEmailPane;

    @FXML TableView<Log> personalLogTableView;
    @FXML TableColumn timestampTableColumn;
    @FXML TableColumn actionTableColumn;
    @FXML TextField userIdText;
    @FXML TextField usernameText;
    @FXML TextField roleText;
    @FXML TextField nameText;
    @FXML TextField addressText;
    @FXML TextField contactNumberText;
    @FXML TextField emailAddressText;
    @FXML TextField statusText;

    @FXML Button editInformationButton;
    @FXML Button editInformationSaveButton;
    @FXML Button editInformationDiscardButton;
    @FXML TextField changePasswordOldPassword;
    @FXML TextField changePasswordNewPassword;
    @FXML TextField changePasswordConfirmPassword;
    @FXML Button changePasswordClearButton;


    // Setting up animation destination, duration, and object
    static final double actionBarWidth = 100.0;
    static final double outOfBoundsAnchorPane = -350.0 - actionBarWidth;
    static final double startOfBoundsAnchorPane = 0.0;
    static final Duration animationDuration = Duration.seconds(0.1);
    TranslateTransition changePasswordPaneOpenAnimation;
    TranslateTransition changePasswordPaneCloseAnimation;
    TranslateTransition changeEmailPaneOpenAnimation;
    TranslateTransition changeEmailPaneCloseAnimation;


    public void initialize(URL url, ResourceBundle rb) {
        // Initializing the animation objects with their corresponding pane
        changePasswordPaneOpenAnimation = new TranslateTransition(animationDuration, changePasswordPane);
        changePasswordPaneCloseAnimation = new TranslateTransition(animationDuration, changePasswordPane);
        changeEmailPaneOpenAnimation = new TranslateTransition(animationDuration, changeEmailPane);
        changeEmailPaneCloseAnimation = new TranslateTransition(animationDuration, changeEmailPane);

        // Hooking up the animation objects with the destinations
        changePasswordPaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        changePasswordPaneCloseAnimation.setToX(startOfBoundsAnchorPane);
        changeEmailPaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        changeEmailPaneCloseAnimation.setToX(startOfBoundsAnchorPane);

        // Populate personal information
        userIdText.setText(String.valueOf(LoginController.getInstance().getUserId()));
        usernameText.setText(LoginController.getInstance().getUsername());
        roleText.setText(LoginController.getInstance().getRole());
        nameText.setText(LoginController.getInstance().getName());
        addressText.setText(LoginController.getInstance().getAddress());
        contactNumberText.setText(LoginController.getInstance().getContactNumber());
        emailAddressText.setText(LoginController.getInstance().getEmailAddress());
        statusText.setText(LoginController.getInstance().getStatusText());

        // Initialize table view and items
        ObservableList<Log> personalLog = FXCollections.observableArrayList();
        personalLog.addAll(Log.loginLogs);
        personalLog.addAll(Log.productLogs);
        personalLog.addAll(Log.catalogueLogs);
        personalLog.addAll(Log.supplierLogs);
        timestampTableColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        actionTableColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        Predicate<Log> logPredicate = log -> log.getUserId() == LoginController.getInstance().getUserId();
        personalLogTableView.setItems(personalLog.filtered(logPredicate));

        // Hide save and discard changes button
        editInformationSaveButton.setVisible(false);
        editInformationDiscardButton.setVisible(false);
    }

    public void editInformationButton_OnAction(Event event) {
        nameText.setDisable(false);
        addressText.setDisable(false);
        contactNumberText.setDisable(false);
        emailAddressText.setDisable(false);
        editInformationButton.setDisable(true);
        editInformationSaveButton.setVisible(true);
        editInformationDiscardButton.setVisible(true);
    }

    public void editInformationSaveButton_OnAction(Event event) {
        LoginController.getInstance().setName(nameText.getText());
        LoginController.getInstance().setAddress(addressText.getText());
        LoginController.getInstance().setContactNumber(contactNumberText.getText());
        LoginController.getInstance().setEmailAddress(emailAddressText.getText());

        nameText.setDisable(true);
        addressText.setDisable(true);
        contactNumberText.setDisable(true);
        emailAddressText.setDisable(true);
        editInformationButton.setDisable(false);
        editInformationSaveButton.setVisible(false);
        editInformationDiscardButton.setVisible(false);
    }

    public void editInformationDiscardButton_OnAction(Event event) {
        nameText.setText(LoginController.getInstance().getName());
        addressText.setText(LoginController.getInstance().getAddress());
        contactNumberText.setText(LoginController.getInstance().getContactNumber());
        emailAddressText.setText(LoginController.getInstance().getEmailAddress());

        nameText.setDisable(true);
        addressText.setDisable(true);
        contactNumberText.setDisable(true);
        emailAddressText.setDisable(true);
        editInformationButton.setDisable(false);
        editInformationSaveButton.setVisible(false);
        editInformationDiscardButton.setVisible(false);
    }

    public void changePasswordButton_OnAction(Event event) {
        if (changePasswordPane.getTranslateX() != outOfBoundsAnchorPane) {
            resetPanes();
            changePasswordPaneOpenAnimation.play();
        }
        else {
            changePasswordPaneCloseAnimation.play();
        }
    }

    public void changePasswordSubmitButton_OnAction (Event event) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (Authentication.toHashString(usernameText.getText(),
                changePasswordOldPassword.getText().toCharArray()).equals(LoginController.getInstance().getPassword())) {
            if (changePasswordNewPassword.getText().equals(changePasswordConfirmPassword.getText())) {
                LoginController.getInstance().setPassword(Authentication.toHashString(usernameText.getText(),
                        changePasswordNewPassword.getText().toCharArray()));
                changePasswordClearButton.fire();
                changePasswordPaneCloseAnimation.play();
            } else {
                Dialog dialog = new Dialog();
                dialog.setContentText("New password does not match.");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                dialog.show();
            }
        } else {
            Dialog dialog = new Dialog();
            dialog.setContentText("Old password is incorrect.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void changePasswordClearButton_OnAction (Event event) {
        changePasswordOldPassword.setText(null);
        changePasswordNewPassword.setText(null);
        changePasswordConfirmPassword.setText(null);
    }

    public void changePasswordCancelButton_OnAction (Event event) {
        changePasswordClearButton.fire();
        changePasswordPaneCloseAnimation.play();
    }

    public void changeEmailButton_OnAction(Event event) {
        if (changeEmailPane.getTranslateX() != outOfBoundsAnchorPane) {
            resetPanes();
            changeEmailPaneOpenAnimation.play();
        }
        else {
            changeEmailPaneCloseAnimation.play();
        }
    }

    public void resetPanes() {
        changePasswordPaneCloseAnimation.play();
        changeEmailPaneCloseAnimation.play();
    }

    public void changeEmailSubmitButton_OnAction (Event event) {

    }

    public void changeEmailClearButton_OnAction (Event event) {

    }

    public void changeEmailCancelButton_OnAction (Event event) {

    }


}
