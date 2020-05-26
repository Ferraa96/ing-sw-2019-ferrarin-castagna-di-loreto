package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;

public class WelcomeController extends GUIController{

    @FXML
    public void startGame(){
        GUIHandler guiHandler = super.getGuiHandler();
        guiHandler.setName();
    }

}
