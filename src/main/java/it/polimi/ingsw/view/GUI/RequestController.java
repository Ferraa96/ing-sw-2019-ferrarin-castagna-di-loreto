package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;


public class RequestController extends GUIController{


    @FXML
    Button YES,NO;

    @FXML
    Label message;

    @FXML
    AnchorPane pane;

    private GUIHandler guiHandler;

    private String state;

    @Override
    public void start(){
        this.guiHandler = super.getGuiHandler();
        state = guiHandler.getState();
        YES = new Button("YES");
        NO = new Button("NO");
        YES.setLayoutX(95);
        YES.setLayoutY(170);
        NO.setLayoutX(260);
        NO.setLayoutY(170);
        message = new Label();
        message.setText(guiHandler.getMessage());
        message.setLayoutX(70);
        message.setLayoutY(50);
        message.setFont(Font.font ("Franklin Gothic Medium", 18));

        pane.getChildren().addAll(YES,NO,message);

        YES.setOnAction(e-> confirmRequest());
        NO.setOnAction(e->denyRequest());

    }

    @FXML
    public void confirmRequest(){
        switch (state) {
            case "POWER":
                guiHandler.getPowerSelection(true);
                break;
            case "RELOAD":
                guiHandler.getReloadSelection(true);
                break;
        }
    }

    @FXML
    public void denyRequest(){
        switch (state) {
            case "POWER":
                guiHandler.getPowerSelection(false);
                break;
            case "RELOAD":
                guiHandler.getReloadSelection(false);
                break;
        }
    }

}
