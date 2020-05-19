package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;

public class WelcomeController extends GUIController{

    private GUIHandler guiHandler;

    @FXML
    public void startGame(){
        this.guiHandler = super.getGuiHandler();
        guiHandler.setScene("login");
    }

}
