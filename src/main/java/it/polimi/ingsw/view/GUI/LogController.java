package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LogController extends GUIController{

    @FXML
    TextField playerName;
    @FXML
    TextField IP, PORT;
    @FXML
    Button button;
    @FXML
    Label wait;
    @FXML
    ProgressIndicator wait1;

    private GUIHandler guiHandler;
    private String state;

    @Override
    public void start(){
        guiHandler = super.getGuiHandler();
        state = guiHandler.getState();
        wait.setVisible(false);
        wait1.setVisible(false);
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
                int port;
                try {
                    port = Integer.parseInt(PORT.getText());
                } catch (NumberFormatException e) {
                    port = 0;
                }
                guiHandler.getLoginInfo(playerName.getText(), port, IP.getText());
                button.disableProperty().setValue(true);
                playerName.editableProperty().setValue(false);
                IP.editableProperty().setValue(false);
                PORT.editableProperty().setValue(false);
                wait.setVisible(true);
                wait1.setVisible(true);
            }
        }
    }
}
