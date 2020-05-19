package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CardsSelectionController extends GUIController{

    @FXML
    Button cardbutton;

    private GUIHandler guiHandler;

    @FXML
    private void chooseCards() {
        this.guiHandler = super.getGuiHandler();

        //da implementare selezione delle carte

        guiHandler.setScene("map");
        }

}


