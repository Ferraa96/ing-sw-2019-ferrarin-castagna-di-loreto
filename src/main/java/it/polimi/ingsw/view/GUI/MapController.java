package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MapController extends GUIController {

    @FXML
    ImageView map;

    @FXML
    AnchorPane pane;

    @FXML
    public void initialize(){
        map.fitHeightProperty().bind(pane.heightProperty());
        map.fitWidthProperty().bind(pane.widthProperty());
    }

}
