package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.IOWriterReader;

import java.io.*;
import java.text.ParseException;

public class Main extends Application {
    @Override
    public void start(Stage loginStage) throws Exception{
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        Parent loginFXML = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        loginStage.setTitle("Product Catalogue System");
        loginStage.setScene(new Scene(loginFXML));
        loginStage.setWidth(screenBounds.getWidth());
        loginStage.setHeight(screenBounds.getHeight());
        loginStage.setMaximized(true);
        loginStage.show();
    }


    public static void main(String[] args) throws IOException, ParseException {
        IOWriterReader.onStartup();
        launch(args);
    }
}
