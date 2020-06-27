package it.polimi.ingsw.view.GUI;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class WelcomeController extends GUIController{

    private GUIHandler guiHandler;

    @FXML
    ToggleButton startgame;
    @FXML
    Label welcome;

    @Override
    public void start(){
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        guiHandler = super.getGuiHandler();
        welcome.setEffect(ds);
        welcome.setCache(true);
        final ImageView toggleImage = new ImageView(new Image("/images/button-play-down.png", 63, 70, false, false));
        startgame.setGraphic(toggleImage);
    }

    @FXML
    public void startGame(){
        guiHandler.Login();
    }

}
