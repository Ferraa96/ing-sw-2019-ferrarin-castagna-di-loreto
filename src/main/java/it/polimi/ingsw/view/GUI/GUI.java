package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that has a reference to every scene and run on the JavaFX Application Thread to display them
 */
public class GUI extends Application implements UIRender{

    private GUIHandler guiHandler;

    private GUIController currentController;

    private Stage stage;

    private static Logger logger = Logger.getLogger(GUI.class.getName());

    @Override
    public void initialize() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
            this.stage = primaryStage;
            this.guiHandler = new GUIHandler(this);
            showScene("/fxml/welcome.fxml", false);
            updateStageInfo("WELCOME");
    }

    private void showScene(String fxmlResource, Boolean fullScreen){
        if (fxmlResource == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlResource));
            Scene scene = loader.load();
            currentController = loader.getController();

            if (this.stage != null) {
                this.stage.hide();
            } else {
                this.stage = new Stage();
                this.stage.resizableProperty().setValue(fullScreen);
            }

            this.stage.setMaximized(fullScreen);

            this.stage.setOnCloseRequest((WindowEvent t) -> {
                Platform.exit();
                System.exit(0);
            });

            if (currentController != null) {
                currentController.setGuiHandler(guiHandler);
                currentController.start();
            }

            this.stage.setScene(scene);
            this.stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot load scene with resource {0}", fxmlResource);
            logger.log(Level.SEVERE, "Exception when loading scene", e);
        }
    }

    private void updateStageInfo(String title) {
        if (this.stage != null) {
            this.stage.setTitle(title);
            //this.stage.getIcons().clear();
        }
    }

    @Override
    public void showLogin(){
        Platform.runLater( () -> {
            showScene("/fxml/login.fxml", false);
            currentController.start();
            updateStageInfo("LOGIN");
        });
    }

    @Override
    public void showSelectionCards(){
        Platform.runLater( () -> {
          showScene("/fxml/choosecardslist.fxml",false);
          updateStageInfo("SELECT CARDS");
        });
    }

    @Override
    public void showCardSelection(){
        Platform.runLater( () -> {
            showScene("/fxml/choosecard.fxml",false);
            updateStageInfo("PICK A CARD");
        });
    }

    @Override
    public void showMap(){
        Platform.runLater( () -> {
            showScene("/fxml/map.fxml",false);
            updateStageInfo("SANTORINI");
        });
    }

    @Override
    public void showMessage(){
        Platform.runLater( () -> {
            showScene("/fxml/message.fxml",false);
            updateStageInfo("WAIT");
        });
    }

    @Override
    public void showRequest(){
        Platform.runLater(() -> {
            showScene("/fxml/request.fxml", false);
            updateStageInfo("ACTION NEEDED");
        });
    }

    @Override
    public void refreshMap(){
        Platform.runLater(() -> {
            if(currentController!=null){
                currentController.refresh();
            }
        });
    }

}