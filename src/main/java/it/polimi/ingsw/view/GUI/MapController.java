package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.Position;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class MapController extends GUIController {

    @FXML
    ImageView map;
    @FXML
    ImageView godcard;
    @FXML
    ImageView worker,building;

    @FXML
    Label playername = new Label("");

    @FXML
    AnchorPane pane;

    private GUIHandler guiHandler;

    private final List<Position> listavailable = new ArrayList<>();

    private Square[][] mappa;

    private int counter = 0;

    private String currentstate;

    private final Button[][] position = new Button[5][5];

    @Override
    public void start(){
        this.guiHandler = super.getGuiHandler();
        godcard.setImage(new Image(guiHandler.getGodOnMap(), 300, 450, true, true));

        listavailable.addAll(guiHandler.getAvailablePos());
        mappa = guiHandler.getMap();
        currentstate = guiHandler.getState();

        //get the already chosen workers/buildings
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(mappa[i][j].getHeight()!=-1){
                    drawBuilding(i,j);
                }
                if (mappa[i][j].getWorker()!=(-1)) {
                    drawWorker(i,j);
                }
            }
        }

        //creates the buttons for the available positions
        for(Position curr : listavailable) {
            int i = curr.getRow();
            int j = curr.getColumn();
            position[i][j] = new Button();
            position[i][j].setLayoutX(310 + (80 * j));
            position[i][j].setLayoutY(100 + (75 * i));
            pane.getChildren().add(position[i][j]);
        }

        //set the playername
        playername.setText(guiHandler.getName());
        playername.setLayoutX(87);
        playername.setLayoutY(471);
        pane.getChildren().add(playername);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int finalI = i;
                int finalJ = j;
                    if(position[i][j]!=null){
                    position[i][j].setOnAction(e -> {
                        Position pos = new Position(finalI, finalJ);
                        position[finalI][finalJ].setVisible(false);
                        counter++;
                        switch(currentstate){
                            case "FIRSTPOS":
                                guiHandler.setSelectedPos(pos);
                                drawWorker(finalI,finalJ);
                                if(counter==2) {
                                disableAll();
                                guiHandler.definePositions();
                                }
                                break;
                            case "SELECTWORKER":
                                guiHandler.defineWorker(pos);
                                break;
                            case "SELECTPOSITION":
                                guiHandler.defineMovement(pos);
                                break;
                        }
                      });
                    }
            }
        }
    }

    private void drawWorker(int row, int column){
        worker = new ImageView(new Image(findColor(mappa[row][column].getWorker())));
        worker.setLayoutX(288 + (80 * column));
        worker.setLayoutY(75 + (75 * row));
        pane.getChildren().add(worker);
    }

    private String findColor(int playerId) {
        switch (playerId) {
            case 0: return "/images/WorkerYellow.png";
            case 1: return "/images/WorkerBlue.png";
            case 2: return "/images/WorkerRed.png";
            default: return "Error loading images!";
        }
    }

    private void drawBuilding(int row,int column){
        building = new ImageView(findHeight(mappa[row][column].getHeight(),mappa[row][column].isDome()));
        building.setLayoutX(288 + (80 * column));
        building.setLayoutY(75 + (75 * row));
        pane.getChildren().add(building);
    }

    private String findHeight(int height,boolean dome){
        switch (height) {
            case 0:
                if(dome)
                    return  "/images/dome.png";
                break;
            case 1:
                if(dome)
                    return  "/images/Build1dome.png";
                return "/images/Build1.png";
            case 2:
                if(dome)
                    return  "/images/Build2dome.png";
                return "/images/Build2.png";
            case 3:
                if(dome)
                    return  "/images/Build3dome.png";
                return "/images/Build3.png";
        }
        return "Error loading images!";
    }

    private void disableAll(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (position[i][j] != null)
                    position[i][j].setVisible(false);
            }
        }
    }

}
