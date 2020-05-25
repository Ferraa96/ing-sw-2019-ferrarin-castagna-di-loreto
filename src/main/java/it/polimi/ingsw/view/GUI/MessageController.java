package it.polimi.ingsw.view.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MessageController extends GUIController {

    @FXML
    Label message = new Label("");

    @FXML
    AnchorPane pane;

    @FXML
    Button close;

    private GUIHandler guiHandler;

    @Override
    public void start(){
        this.guiHandler = super.getGuiHandler();
        message.setText(guiHandler.getMessage());
        String state = guiHandler.getState();
        message.setLayoutY(300);
        message.setLayoutX(150);
        if(state.equals("END")){
            close = new Button("END");
            close.setLayoutX(150);
            close.setLayoutY(150);
            close.setOnAction(e->{
                guiHandler.closeClient();
                Platform.exit();
            });
            pane.getChildren().add(close);
        }
        pane.getChildren().add(message);
    }

}
