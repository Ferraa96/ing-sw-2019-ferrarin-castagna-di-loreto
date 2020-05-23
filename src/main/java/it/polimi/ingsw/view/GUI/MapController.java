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

    private ImageView [][] boardImages = new ImageView[5][5];
    private int callNumber = 0;

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
        godcard.setImage(new Image(guiHandler.getGodOnMap(), 300, 450, true, true));

        listavailable.addAll(guiHandler.getAvailablePos());
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
                if (mappa[i][j].getWorker() != -1) {
                             drawWorker(i, j);
                }

                if(mappa[i][j].getHeight()==1){
                    drawBuilding(i,j);
                }
            }
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
        if (callNumber == 0) {
            callNumber++;
            boardImages[0][0] = zz;
            boardImages[0][1] = zu;
            boardImages[0][2] = zd;
            boardImages[0][3] = zt;
            boardImages[0][4] = zq;
            boardImages[1][0] = uz;
            boardImages[1][1] = uu;
            boardImages[1][2] = ud;
            boardImages[1][3] = ut;
            boardImages[1][4] = uq;
            boardImages[2][0] = dz;
            boardImages[2][1] = du;
            boardImages[2][2] = dd;
            boardImages[2][3] = dt;
            boardImages[2][4] = dq;
            boardImages[3][0] = tz;
            boardImages[3][1] = tu;
            boardImages[3][2] = td;
            boardImages[3][3] = tt;
            boardImages[3][4] = tq;
            boardImages[4][0] = qz;
            boardImages[4][1] = qu;
            boardImages[4][2] = qd;
            boardImages[4][3] = qt;
            boardImages[4][4] = qq;
        }
        //if (mappa[row][column].getWorker() == 0)
        boardImages[row][column].setImage(new Image(findColor(mappa[row][column].getWorker()), 300, 450, true, true));
    }

    private String findColor(int playerId) {
        switch (playerId) {
            case 0: return "/images/workerGreen.png";
            case 1: return "/images/workerBrown.png";
            //manca un colore
            default: return "Errore, hai superato i 3 giocatori!";
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (position[i][j] != null)
                    position[i][j].setVisible(false);
            }
        }
    }

}
