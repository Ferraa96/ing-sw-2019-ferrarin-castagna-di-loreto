package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class PowerController extends GUIController{

    @FXML
    Button YES,NO;

    private GUIHandler guiHandler;

    @FXML
    public void activatePower(){
        this.guiHandler = super.getGuiHandler();
        guiHandler.getPowerSelection(true);
    }

    @FXML
    public void dontactivatePower(){
        this.guiHandler = super.getGuiHandler();
        guiHandler.getPowerSelection(false);
    }

}
