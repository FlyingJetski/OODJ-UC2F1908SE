package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Authentication;
import model.IOWriterReader;
import model.Log;
import model.objects.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class LoginController {
    @FXML private Button confirmButton;
    @FXML private GridPane gridPane;

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
            Log.loginLogs.add(new Log("Logged in: " + instance.getUsername()));

            Parent navigationFXML = FXMLLoader.load(getClass().getResource("/view/Navigation.fxml"));
            Scene navigationScene = new Scene(navigationFXML);

            /*navigationScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    if (ke.getCode() == KeyCode.ESCAPE) {
                        System.out.println("Key Pressed: " + ke.getCode());
                        ke.consume(); // <-- stops passing the event to next node
                    }
                }
            });*/

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
        }
    }

    @FXML
    public void forgotPasswordButton_OnAction(Event e){
        System.out.println("Button2 clicked");
    }
}