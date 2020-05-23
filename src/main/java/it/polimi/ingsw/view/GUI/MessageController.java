package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MessageController extends GUIController {

    @FXML
    Label message = new Label("");

    @FXML
    AnchorPane pane;

    private GUIHandler guiHandler;

    @FXML
    public void showMessage(){
        this.guiHandler = super.getGuiHandler();
        message.setText(guiHandler.getMessage());
        message.setLayoutY(300);
        message.setLayoutX(150);
        pane.getChildren().add(message);
    }

}
