package it.polimi.ingsw.view.GUI;

import javafx.stage.Stage;

abstract class GUIController {

    private GUIHandler guiHandler;

    public void setGuiHandler(GUIHandler guiHandler){
        if(guiHandler == null){
        throw new NullPointerException("GUI Handler cannot be null");
        }
        this.guiHandler = guiHandler;
    }

    public GUIHandler getGuiHandler(){
        return this.guiHandler;
    }

}
