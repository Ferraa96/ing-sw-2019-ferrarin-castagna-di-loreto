package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.Client.SocketClient;
import it.polimi.ingsw.controller.Instructions.SetNameNotification;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Movement;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

public class GUIHandler implements ViewInterface {

    private SocketClient socketClient;
    private GUI gui;
    private String name = "";
    private String ip;
    private String currentScene = "welcome";
    private int clientID;

    public GUIHandler(GUI gui){
        this.gui = gui;
    }

    public void getLoginInfo(String name,String ip){
        this.name = name;
        this.ip = ip;
        if(socketClient.connectGUI(ip,this)){
            socketClient.send(new SetNameNotification(name, clientID));
        }else{
            setName();
        }
    }

    public void setScene(String scene){

        switch(scene){
            case "login":
                socketClient = new SocketClient(1);
                setName();
                break;
            case "cards":
                currentScene="login";
                gui.showSelectionCards();
                break;
            case "map":
                currentScene="cards";
                gui.showMap();
                break;
        }

    }

    @Override
    public void setName(){
        gui.showLogin();
    }


    @Override
    public void chooseCardList(int num) {
        setScene("cards");
        boolean ok = true;
        while(ok == true) {
            if(currentScene.equals("map")){
                ok = false;
                break;
            }
        }
    }

    @Override
    public void setID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public void chooseCard(List<Integer> cardList) {

    }

    @Override
    public void firstPositioning(List<Position> availablePos, String godName, boolean isMyTurn) {

    }

    @Override
    public void chooseWorker(List<Position> availableWorkers) {

    }

    @Override
    public void choosePower() {

    }

    @Override
    public void choosePosition(List<Position> list) {

    }

    @Override
    public void move(List<Movement> movements) {

    }

    @Override
    public void buildBlock(Position position, int height) {

    }

    @Override
    public void buildDome(Position position, int height) {

    }

    @Override
    public void updateScreen() {

    }

    @Override
    public void askForReloadState() {

    }

    @Override
    public void reloadState(Cell[][] map, List<String> godNames) {

    }

    @Override
    public void handleDisconnection(int playerDisconnected) {

    }

    @Override
    public void notificationForOtherClient(String message) {

    }

    @Override
    public void elimination(boolean elim, String eliminatedPlayer) {

    }

    @Override
    public void win(boolean win, String winnerName) {

    }

}
