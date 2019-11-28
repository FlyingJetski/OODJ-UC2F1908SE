package controller;

import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Log;
import model.objects.Product_Manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CatalogueController implements Initializable {
    @FXML AnchorPane addCataloguePane;
    @FXML AnchorPane editCataloguePane;

    @FXML Button viewLogButton;

    // Setting up animation destination, duration, and object
    static final double actionBarWidth = 100.0;
    static final double outOfBoundsAnchorPane = -350.0 - actionBarWidth;
    static final double startOfBoundsAnchorPane = 0.0;
    static final Duration animationDuration = Duration.seconds(0.1);
    TranslateTransition addCataloguePaneOpenAnimation;
    TranslateTransition addCataloguePaneCloseAnimation;
    TranslateTransition editCataloguePaneOpenAnimation;
    TranslateTransition editCataloguePaneCloseAnimation;


    public void initialize(URL url, ResourceBundle rb) {
        // Initializing the animation objects with their corresponding pane
        addCataloguePaneOpenAnimation = new TranslateTransition(animationDuration, addCataloguePane);
        addCataloguePaneCloseAnimation = new TranslateTransition(animationDuration, addCataloguePane);
        editCataloguePaneOpenAnimation = new TranslateTransition(animationDuration, editCataloguePane);
        editCataloguePaneCloseAnimation = new TranslateTransition(animationDuration, editCataloguePane);

        // Hooking up the animation objects with the destinations
        addCataloguePaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        addCataloguePaneCloseAnimation.setToX(startOfBoundsAnchorPane);
        editCataloguePaneOpenAnimation.setToX(outOfBoundsAnchorPane);
        editCataloguePaneCloseAnimation.setToX(startOfBoundsAnchorPane);

        // Disable view log button
        if (LoginController.getInstance().getClass() == Product_Manager.class) {
            viewLogButton.setDisable(true);
        }
    }

    public void addCatalogueButton_OnAction(Event event) {
        if (addCataloguePane.getTranslateX() != outOfBoundsAnchorPane) {
            resetPanes();
            addCataloguePaneOpenAnimation.play();
        }
        else {
            addCataloguePaneCloseAnimation.play();
        }
    }

    public void editCatalogueButton_OnAction(Event event) {
        if (editCataloguePane.getTranslateX() != outOfBoundsAnchorPane) {
            resetPanes();
            editCataloguePaneOpenAnimation.play();
        }
        else {
            editCataloguePaneCloseAnimation.play();
        }
    }

    public void deleteCatalogueButton_OnAction(Event event) {
        Alert confirmationPopup = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete the catalogue permanently?",
                ButtonType.YES, ButtonType.NO);
        confirmationPopup.showAndWait();

        if (confirmationPopup.getResult() == ButtonType.YES) {
            //do stuff
            Log.catalogueLogs.add(new Log("Deleted catalogue: " + "placeholder"));
        }
    }

    public void viewLogButton_OnAction (Event event) throws IOException {
        Stage logStage = new Stage();
        logStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Log.fxml"))));
        logStage.setWidth(800);
        logStage.setHeight(600);
        logStage.setTitle("Catalogue Log");
        logStage.show();
    }

    public void resetPanes() {
        addCataloguePaneCloseAnimation.play();
        editCataloguePaneCloseAnimation.play();
    }

    /*
     * Start of Add Catalogue buttons
     */

    public void addCatalogueSelectedProductsAddButton_OnAction(Event event) {

    }

    public void addCatalogueSelectedProductsRemoveButton_OnAction(Event event) {

    }

    public void addCatalogueSubmitButton_OnAction (Event event) {

    }

    public void addCatalogueClearButton_OnAction (Event event) {

    }

    public void addCatalogueCancelButton_OnAction (Event event) {

    }


    /*
     * Start of Edit Catalogue buttons
     */

    public void editCatalogueSelectedProductsAddButton_OnAction(Event event) {

    }

    public void editCatalogueSelectedProductsRemoveButton_OnAction(Event event) {

    }

    public void editCatalogueSubmitButton_OnAction (Event event) {

    }

    public void editCatalogueClearButton_OnAction (Event event) {

    }

    public void editCatalogueCancelButton_OnAction (Event event) {

    }
}