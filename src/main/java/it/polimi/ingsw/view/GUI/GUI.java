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
 * Class that take care of everything that must be displayed
 */
public class GUI extends Application implements UIRender{

    private GUIController currentController;

    private GUIHandler guiHandler;

    private Stage stage;

    private static Logger logger = Logger.getLogger(GUI.class.getName());

    @Override
    public void initialize() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
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
                this.stage.setResizable(true);
            }

            this.stage.setMaximized(fullScreen);

            this.stage.setOnCloseRequest((WindowEvent t) -> {
                Platform.exit();
                System.exit(0);
            });

            if (currentController != null) {
                currentController.setGuiHandler(guiHandler);
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
            showScene("/fxml/login.fxml",false);
            updateStageInfo("LOGIN");
    }

    @Override
    public void showSelectionCards(){
        showScene("/fxml/choosecardslist.fxml",false);
        updateStageInfo("SELECT CARDS");
    }

    @Override
    public void showMap(){
            showScene("/fxml/map.fxml",true);
            updateStageInfo("SANTORINI");
    }

}