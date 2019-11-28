package controller;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import model.Authentication;
import model.objects.Administrator;
import model.objects.Product_Manager;
import model.objects.User;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class UserController implements Initializable {
    @FXML AnchorPane addUserPane;
    @FXML AnchorPane resetPasswordPane;

    @FXML ComboBox searchComboBox;
    @FXML TextField searchText;
    @FXML TableView<User> userTableView;
    @FXML TableColumn userIdTableColumn;
    @FXML TableColumn usernameTableColumn;
    @FXML TableColumn roleTableColumn;
    @FXML TableColumn nameTableColumn;
    @FXML TableColumn addressTableColumn;
    @FXML TableColumn contactNumberTableColumn;
    @FXML TableColumn emailAddressTableColumn;
    @FXML TableColumn statusTableColumn;

    @FXML TextField addUserUsername;
    @FXML TextField addUserPassword;
    @FXML ComboBox addUserRole;
    @FXML TextField addUserName;
    @FXML TextField addUserAddress;
    @FXML TextField addUserContactNumber;
    @FXML TextField addUserEmailAddress;

    @FXML Button addUserClearButton;

    // Setting up animation destination, duration, and object
    static final double actionBarWidth = 100.0;
    static final double outOfBoundsAnchorPane = -350.0 - actionBarWidth;
    static final double startOfBoundsAnchorPane = 0.0;
    static final Duration animationDuration = Duration.seconds(0.1);
    TranslateTransition addUserPaneOpenAnimation;
    TranslateTransition addUserPaneCloseAnimation;
    TranslateTransition resetPasswordPaneOpenAnimation;
    TranslateTransition resetPasswordPaneCloseAnimation;


    public void initialize(URL url, ResourceBundle rb) {
        // Initializing the animation objects with their corresponding pane
        addUserPaneOpenAnimation = new TranslateTransition(animationDuration, addUserPane);
        addUserPaneCloseAnimation = new TranslateTransition(animationDuration, addUserPane);
        resetPasswordPaneOpenAnimation = new TranslateTransition(animationDuration, resetPasswordPane);
        resetPasswordPaneCloseAnimation = new TranslateTransition(animationDuration, resetPasswordPane);

        // Hooking up the animation objects with the destinations
        addUserPaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        addUserPaneCloseAnimation.setToX(startOfBoundsAnchorPane);
        resetPasswordPaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        resetPasswordPaneCloseAnimation.setToX(startOfBoundsAnchorPane);

        // Populate combo box and set default value
        searchComboBox.getItems().addAll("User ID", "Username", "Role", "Name",
                "Address", "Contact Number", "Email Address");
        searchComboBox.setValue("Name");

        addUserRole.getItems().addAll("Product Manager", "Administrator");
        addUserRole.setValue("Product Manager");

        // Configure table columns and table items
        userIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleTableColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        contactNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        emailAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("statusText"));
        userTableView.setItems(User.users);
    }

    public void addUserButton_OnAction(Event event) {
        if (addUserPane.getTranslateX() != outOfBoundsAnchorPane) {
            addUserPaneOpenAnimation.play();
        }
        else {
            addUserPaneCloseAnimation.play();
        }
    }

    public void addUserSubmitButton_OnAction (Event event) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (addUserRole.getValue().toString().equals("Product Manager")) {
            User newUser = new Product_Manager (addUserUsername.getText(),
                    Authentication.toHashString(addUserUsername.getText(), addUserPassword.getText().toCharArray()),
                    addUserName.getText(), addUserAddress.getText(),
                    addUserContactNumber.getText(), addUserEmailAddress.getText());
            User.users.add(newUser);
        } else if (addUserRole.getValue().toString().equals("Administrator")) {
            User newUser = new Administrator (addUserUsername.getText(),
                    Authentication.toHashString(addUserUsername.getText(), addUserPassword.getText().toCharArray()),
                    addUserName.getText(), addUserAddress.getText(),
                    addUserContactNumber.getText(), addUserEmailAddress.getText());
            User.users.add(newUser);
        }
        addUserClearButton.fire();
        addUserPaneCloseAnimation.play();
        refreshTableView();
    }

    public void addUserClearButton_OnAction (Event event) {
        addUserUsername.setText(null);
        addUserPassword.setText(null);
        addUserRole.setValue("Product Manager");
        addUserName.setText(null);
        addUserAddress.setText(null);
        addUserContactNumber.setText(null);
        addUserEmailAddress.setText(null);
    }

    public void addUserCancelButton_OnAction (Event event) {
        addUserClearButton.fire();
        addUserPaneCloseAnimation.play();
    }

    public void changeStatusButton_OnAction(Event event) {
        try {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                throw new NullPointerException();
            }

            Alert confirmationPopup = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the status?",
                    ButtonType.YES, ButtonType.NO);
            confirmationPopup.showAndWait();

            if (confirmationPopup.getResult() == ButtonType.YES) {
                selectedUser.setStatus(!selectedUser.getStatus());
            }
            refreshTableView();
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("User must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void deleteUserButton_OnAction(Event event) {
        try {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                throw new NullPointerException();
            }

            Alert confirmationPopup = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete the user permanently?",
                    ButtonType.YES, ButtonType.NO);
            confirmationPopup.showAndWait();

            if (confirmationPopup.getResult() == ButtonType.YES) {
                User.users.remove(selectedUser);
            }
            refreshTableView();
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("User must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void resetPasswordButton_OnAction(Event event) {
        try {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                throw new NullPointerException();
            }

            TextInputDialog resetPasswordDialog = new TextInputDialog();
            resetPasswordDialog.setTitle("Reset password");
            resetPasswordDialog.setContentText("New password:");
            resetPasswordDialog.setHeaderText(null);
            resetPasswordDialog.setGraphic(null);
            Button okButton = (Button) resetPasswordDialog.getDialogPane().lookupButton(ButtonType.OK);
            TextField inputField = resetPasswordDialog.getEditor();
            BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isInvalid(inputField.getText()), inputField.textProperty());
            okButton.disableProperty().bind(isInvalid);

            if (resetPasswordDialog.showAndWait().toString() != Optional.empty().toString()) {
                Alert confirmationPopup = new Alert(Alert.AlertType.WARNING, "Are you sure you want to reset the password?",
                        ButtonType.YES, ButtonType.NO);
                confirmationPopup.showAndWait();

                if (confirmationPopup.getResult() == ButtonType.YES) {
                    selectedUser.setPassword(Authentication.toHashString(selectedUser.getUsername(),
                            resetPasswordDialog.getEditor().getText().toCharArray()));
                }
            }
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("User must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    public void viewLogButton_OnAction (Event event) throws IOException {
        Stage logStage = new Stage();
        logStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Log.fxml"))));
        logStage.setWidth(800);
        logStage.setHeight(600);
        logStage.setTitle("Login Log");
        logStage.show();
    }

    private void refreshTableView() {
        userTableView.getColumns().get(0).setVisible(false);
        userTableView.getColumns().get(0).setVisible(true);
    }

    public void searchText_OnChange (Event event) {
        Predicate<User> searchComboBoxValue = null;
        switch (searchComboBox.getValue().toString()) {
            case "User ID":
                searchComboBoxValue = user -> String.valueOf(user.getUserId()).contains(searchText.getText());
                break;
            case "Username":
                searchComboBoxValue = user -> user.getUsername().contains(searchText.getText());
                break;
            case "Role":
                searchComboBoxValue = user -> user.getRole().contains(searchText.getText());
                break;
            case "Name":
                searchComboBoxValue = user -> user.getName().contains(searchText.getText());
                break;
            case "Address":
                searchComboBoxValue = user -> user.getAddress().contains(searchText.getText());
                break;
            case "Contact Number":
                searchComboBoxValue = user -> user.getContactNumber().contains(searchText.getText());
                break;
            case "Email Address":
                searchComboBoxValue = user -> user.getEmailAddress().contains(searchText.getText());
                break;
        }
        userTableView.setItems(User.users.filtered(searchComboBoxValue));
    }

    private boolean isInvalid(String string) {
        if (string.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
