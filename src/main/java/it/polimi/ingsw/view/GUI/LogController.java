package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LogController extends GUIController{

    @FXML
    TextField playerName;
    @FXML
    TextField IP, PORT;
    @FXML
    Button button;

    private GUIHandler guiHandler;
    private String state;

    @Override
    public void start(){
        guiHandler = super.getGuiHandler();
        state = guiHandler.getState();
        if(state.equals("RESET NAME")){
            IP.setVisible(false);
            PORT.setVisible(false);
        }
    }

    @FXML
    private void logIn() {

        if (state.equals("RESET NAME")) {
            guiHandler.GetUserName(playerName.getText());
            button.disableProperty().setValue(true);
        } else {
            if ((playerName.getText().equals("")) || (PORT.getText().equals("")) || (IP.getText().equals(""))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("You have to insert valid input!");
                alert.showAndWait();
            } else {
                guiHandler.getLoginInfo(playerName.getText(),Integer.parseInt(PORT.getText()), IP.getText());
                button.disableProperty().setValue(true);
            }
        }
    }
}
