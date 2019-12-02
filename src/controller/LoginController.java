package controller;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Authentication;
import model.IOWriterReader;
import model.InsufficientPrivilegeException;
import model.objects.Log;
import model.objects.User;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;

public class LoginController {
    @FXML TextField usernameText;
    @FXML TextField passwordText;

    private static User instance;

    public LoginController() throws IOException {

    }

    public static User getInstance() {
        return instance;
    }

    @FXML
    public void confirmButton_OnAction(Event event) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        try {
            instance = Authentication.login(usernameText.getText(), passwordText.getText());
            if (!instance.getStatus()) {
                throw new InsufficientPrivilegeException();
            }
            Log.loginLogs.add(new Log("Logged in"));

            Parent navigationFXML = FXMLLoader.load(getClass().getResource("/view/Navigation.fxml"));
            Scene navigationScene = new Scene(navigationFXML);

            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setOnCloseRequest(closeEvent -> {
                try {
                    IOWriterReader.onExit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            mainStage.setScene(navigationScene);
            mainStage.show();
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Username or password is incorrect.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (IllegalArgumentException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Username or password is empty.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        } catch (InsufficientPrivilegeException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("User is deactivated. Please contact your administrator for further information");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }
}