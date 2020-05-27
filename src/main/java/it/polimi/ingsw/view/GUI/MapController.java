package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.Position;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class MapController extends GUIController {

    @FXML
    ImageView map;
    @FXML
    ImageView pane1,pane2;
    @FXML
    ImageView godcard,godcard1,godcard2;
    @FXML
    ImageView worker,building;

    @FXML
    Label playername1 = new Label("");
    @FXML
    Label playername2 = new Label("");
    @FXML
    Label playername3 = new Label("");
    @FXML
    Label action = new Label("");

    @FXML
    AnchorPane pane;

    private GUIHandler guiHandler;

    private final List<Position> listavailable = new ArrayList<>();

    private Square[][] mappa;

    private int counter = 0;

    private int NumofPlayers;

    private String currentstate;

    private final List<String> players = new ArrayList<>();

    private final Button[][] position = new Button[5][5];

    private final List<String> godname = new ArrayList<>();

    @Override
    public void start(){
        this.guiHandler = super.getGuiHandler();
        godcard.setImage(new Image(guiHandler.getGodOnMap(), 300, 450, true, true));

        listavailable.addAll(guiHandler.getAvailablePos());
        mappa = guiHandler.getMap();
        currentstate = guiHandler.getState();
        boolean isMyturn = guiHandler.isMyTurn();
        NumofPlayers = guiHandler.getPlayers();

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

        //set the playername and the action
        playername1.setText(guiHandler.getName());
        playername1.setLayoutX(87);
        playername1.setLayoutY(471);
        playername1.setFont(Font.font ("Franklin Gothic Medium", 12));
        pane1.setVisible(false);
        pane2.setVisible(false);

        players.clear();
        for(String curr : guiHandler.getUsername()){
            if(!curr.equals(guiHandler.getName()))
            players.add(curr);
        }

        godname.clear();
        for(String curr : guiHandler.getGodName()){
            if(!convertGod(curr).equals(guiHandler.getGodOnMap()))
                godname.add(curr);
        }

        if(godname.size()>=1) {
            pane1.setVisible(true);
            godcard1.setImage(new Image(convertGod(godname.get(0)), 300, 450, true, true));
            playername2.setText(players.get(0));
            playername2.setLayoutX(835);
            playername2.setLayoutY(473);
            playername2.setFont(Font.font("Franklin Gothic Medium", 12));
        }

        if(godname.size()>=2){
            pane2.setVisible(true);
            godcard2.setImage(new Image(convertGod(godname.get(1)), 300, 450, true, true));
            playername3.setText(players.get(1));
            playername3.setLayoutX(835);
            playername3.setLayoutY(125);
            playername3.setFont(Font.font ("Franklin Gothic Medium", 12));
        }

        setMessage();
        if(isMyturn) {
            doAction();
        }

    }

    private void setMessage() {
        action.setText(guiHandler.getMessage());
        action.setLayoutX(20);
        action.setLayoutY(20);
        action.setFont(Font.font("Franklin Gothic Medium", 20));
        pane.getChildren().addAll(playername1,playername2,action);
        if(NumofPlayers == 3)
            pane.getChildren().add(playername3);
    }

    private void doAction(){
        //creates the buttons for the available position
        for(Position curr : listavailable) {
            int i = curr.getRow();
            int j = curr.getColumn();
            position[i][j] = new Button();
            position[i][j].setStyle("-fx-background-color: rgba(255,213,0,0.18); -fx-border-color: #fff000; -fx-border-width: 5px;");
            position[i][j].setLayoutX(288 + (80 * j));
            position[i][j].setLayoutY(75 + (75 * i));
            position[i][j].setPrefWidth(71);
            position[i][j].setPrefHeight(69);
            pane.getChildren().add(position[i][j]);
        }


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

    private String convertGod(String godName){
        switch(godName){
            case "Apollo":{
                return "/images/01.png";
            }
            case "Artemis":{
                return "/images/02.png";
            }
            case "Athena":{
                return "/images/03.png";
            }
            case "Atlas":{
                return "/images/04.png";
            }
            case "Demeter":{
                return "/images/05.png";
            }
            case "Hephaestus":{
                return "/images/06.png";
            }
            case "Minotaur":{
                return "/images/08.png";
            }
            case "Pan":{
                return "/images/09.png";
            }
            case "Prometheus":{
                return "/images/10.png";
            }
            default:{
                return "Invalid Choicqe";
            }
        }
    }

}
