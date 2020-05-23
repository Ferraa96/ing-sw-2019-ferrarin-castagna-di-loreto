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
    ImageView zz,zu,zd,zt,zq;
    @FXML
    ImageView uz,uu,ud,ut,uq;
    @FXML
    ImageView dz,du,dd,dt,dq;
    @FXML
    ImageView tz,tu,td,tt,tq;
    @FXML
    ImageView qz,qu,qd,qt,qq;

    @FXML
    Label playername = new Label("");

    @FXML
    Button setbutton;

    @FXML
    AnchorPane pane;

    private GUIHandler guiHandler;

    private final List<Position> listavailable = new ArrayList<>();

    private Square[][] mappa;

    private int counter = 0;

    private String currentstate;

    private final Button[][] position = new Button[5][5];

    @FXML
    public void setWorker(){
        this.guiHandler = super.getGuiHandler();
        godcard.setImage(new Image(guiHandler.setGodOnMap(), 300, 450, true, true));

        listavailable.addAll(guiHandler.setAvailablePos());
        mappa = guiHandler.getMap();
        currentstate = guiHandler.getState();

        setbutton.setVisible(false);

        //creates the buttons for the available positions
        for(Position curr : listavailable) {
            int i = curr.getRow();
            int j = curr.getColumn();
            position[i][j] = new Button();
            position[i][j].setLayoutX(310 + (80 * j));
            position[i][j].setLayoutY(100 + (75 * i));
            pane.getChildren().add(position[i][j]);
        }

        //get the already chosen workers/buildings
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (mappa[i][j].isWorker()) {
                             drawImage(i, j);
                }

                if(mappa[i][j].getHeight()==1){
                    drawBuilding(i,j);
                }
            }
        }

        //set the playername
        playername.setText(guiHandler.setNameOnMap());
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
                                drawImage(finalI,finalJ);
                                guiHandler.getSelectedPos(pos);
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

    private void drawImage(int row,int column){
        switch (row){
            case 0:{
                switch (column){
                    case 0:
                        zz.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 1:
                        zu.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 2:
                        zd.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 3:
                        zt.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 4:
                        zq.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                }
                break;}
            case 1:{
                switch (column){
                    case 0:
                        uz.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 1:
                        uu.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 2:
                        ud.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 3:
                        ut.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 4:
                        uq.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                }
                break;}
            case 2:{
                switch (column){
                    case 0:
                        dz.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 1:
                        du.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 2:
                        dd.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 3:
                        dt.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 4:
                        dq.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                }
                break;}
            case 3:{
                switch (column){
                    case 0:
                        tz.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 1:
                        tu.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 2:
                        td.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 3:
                        tt.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 4:
                        tq.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                }
                break;}
            case 4:{
                switch (column){
                    case 0:
                        qz.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 1:
                        qu.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 2:
                        qd.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 3:
                        qt.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                    case 4:
                        qq.setImage(new Image("/images/workerGreen.png", 300, 450, true, true));
                        break;
                }
                break;}
        }
    }

    private void drawBuilding(int row,int column){
        switch (row){
            case 0:{
                switch (column){
                    case 0:
                        zz.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 1:
                        zu.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 2:
                        zd.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 3:
                        zt.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 4:
                        zq.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                }
                break;}
            case 1:{
                switch (column){
                    case 0:
                        uz.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 1:
                        uu.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 2:
                        ud.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 3:
                        ut.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 4:
                        uq.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                }
                break;}
            case 2:{
                switch (column){
                    case 0:
                        dz.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 1:
                        du.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 2:
                        dd.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 3:
                        dt.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 4:
                        dq.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                }
                break;}
            case 3:{
                switch (column){
                    case 0:
                        tz.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 1:
                        tu.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 2:
                        td.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 3:
                        tt.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 4:
                        tq.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                }
                break;}
            case 4:{
                switch (column){
                    case 0:
                        qz.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 1:
                        qu.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 2:
                        qd.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 3:
                        qt.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                    case 4:
                        qq.setImage(new Image("/images/building1.png", 300, 450, true, true));
                        break;
                }
                break;}
        }
    }

    private void disableAll(){
        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 5; b++) {
                if (position[a][b] != null)
                    position[a][b].setVisible(false);
            }
        }
    }

}
