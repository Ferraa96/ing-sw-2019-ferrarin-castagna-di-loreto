package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LogController extends GUIController{

    @FXML
    TextField playerName;
    @FXML
    TextField IP;
    @FXML
    Button button;

    @FXML
    private void logIn() {
        GUIHandler guiHandler = super.getGuiHandler();

        if((playerName.getText().equals("")) || (IP.getText().equals(""))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Username or IP");
            alert.showAndWait();
        }else{
            guiHandler.getLoginInfo(playerName.getText(), IP.getText());
            button.disableProperty().setValue(true);
        }
    }

}
