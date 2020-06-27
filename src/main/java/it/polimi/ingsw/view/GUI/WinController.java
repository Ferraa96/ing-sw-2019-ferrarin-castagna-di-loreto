package it.polimi.ingsw.view.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class WinController extends GUIController {

    @FXML
    Label WINMESSAGE;

    @FXML
    AnchorPane pane;

    @FXML
    Button close;

    @Override
    public void start(){
        GUIHandler guiHandler = super.getGuiHandler();
        WINMESSAGE.setText(guiHandler.getMessage());
        String state = guiHandler.getState();
        if(state.equals("END")){
            close = new Button("END");
            close.setLayoutX(220);
            close.setLayoutY(240);
            close.setOnAction(e-> Platform.exit());
            pane.getChildren().add(close);
        }
    }

}