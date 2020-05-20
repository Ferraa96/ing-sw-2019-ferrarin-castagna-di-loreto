package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

public class MapController extends GUIController {

    @FXML
    ImageView map;
    @FXML
    ImageView godcard;

    @FXML
    Label playername;

    @FXML
    AnchorPane pane;

    private GUIHandler guiHandler;

    @FXML
    public void initialize() {
        map.fitHeightProperty().bind(pane.heightProperty());
        map.fitWidthProperty().bind(pane.widthProperty());
    }

}
