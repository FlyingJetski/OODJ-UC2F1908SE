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
import model.IllegalInputFormatException;
import model.NullValueException;
import model.RegularExpressionPattern;
import model.objects.Log;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Personal_InformationController implements Initializable {
    @FXML AnchorPane changePasswordPane;

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

        // Hooking up the animation objects with the destinations
        changePasswordPaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        changePasswordPaneCloseAnimation.setToX(startOfBoundsAnchorPane);

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
        try {
            boolean patternMatch = false;
            for (String contactNumberPatternString: RegularExpressionPattern.getContactNumberPatternStringArrayList()) {
                Pattern contactNumberPattern = Pattern.compile(contactNumberPatternString);
                Matcher contactNumberMatcher = contactNumberPattern.matcher(contactNumberText.getText());
                if (contactNumberMatcher.matches()) {
                    patternMatch = true;
                    break;
                }
            }
            if (!patternMatch) {
                throw new IllegalInputFormatException.ContactNumber();
            }
            Pattern emailAddressPattern = Pattern.compile(RegularExpressionPattern.getEmailAddressPatternString());
            Matcher emailAddressMatcher = emailAddressPattern.matcher(emailAddressText.getText());
            if (!emailAddressMatcher.matches()) {
                throw new IllegalInputFormatException.EmailAddress();
            }
            if (nameText.getText() == null || addressText.getText() == null || contactNumberText.getText() == null ||
                    emailAddressText.getText() == null) {
                throw new NullValueException();
            }
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
        } catch (IllegalInputFormatException.ContactNumber exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Contact number is invalid.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (IllegalInputFormatException.EmailAddress exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Email address is invalid.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (NullValueException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("All fields must be filled.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
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
        try {
            if (changePasswordOldPassword.getText() == null || changePasswordNewPassword.getText() == null ||
                    changePasswordConfirmPassword.getText() == null) {
                throw new NullValueException();
            }
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
        } catch (NullValueException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("All fields must be filled.");
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

    public void resetPanes() {
        changePasswordPaneCloseAnimation.play();
    }
}
