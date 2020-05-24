package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.Client.SocketClient;
import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Movement;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.view.ViewInterface;

import java.util.ArrayList;
import java.util.List;

public class GUIHandler implements ViewInterface {

    private SocketClient socketClient;
    private final GUI gui;
    private int playernumber;
    private String name = "";
    private String imagepath;
    private int clientID;
    private String message;
    private String godname;
    private String state;
    private Square[][] map;
    private int playerId = 0;
    private final List<Integer> chosen = new ArrayList<>();
    private final List<Position> availablePos = new ArrayList<>();
    private final List<Position> currentChoice = new ArrayList<>();

    public GUIHandler(GUI gui){
        this.gui = gui;
    }

    public void getLoginInfo(String name,String ip){
        this.name = name;
        socketClient = new SocketClient(1);
        map = new Square[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                map[i][j] = new Square();
            }
        }
        if(socketClient.connectGUI(ip,this)){
            socketClient.send(new SetNameNotification(name, clientID));
        }else{
            setName();
        }
    }

    @Override
    public void setName(){
        gui.showLogin();
    }

    public void getSelectedCards(List<Integer> chosen){
        this.chosen.addAll(chosen);
        socketClient.send(new ChooseCardListNotification(chosen));
    }

    @Override
    public void chooseCardList(int num) {
        playernumber = num;
        gui.showSelectionCards();
    }

    @Override
    public void chooseCard(List<Integer> cardList) {
        chosen.clear();
        this.chosen.addAll(cardList);
        gui.showCardSelection();
    }

    public void getCard(int card, String imagepath){
        this.imagepath = imagepath;
        socketClient.send(new ChooseCardNotification(card));
    }

    @Override
    public void firstPositioning(List<Position> availablePos, String godName, boolean isMyTurn) {
        if(isMyTurn) {
            this.availablePos.addAll(availablePos);
            this.godname = godName;
            if(availablePos.size() == 23)
                this.playerId = 1;
            if(availablePos.size() == 21)
                this.playerId = 2;
            this.state = "FIRSTPOS";
        }else {
            if(availablePos.size() == 23)
                this.playerId = 1;
            if(availablePos.size() == 21)
                this.playerId = 2;
            for(Position currPos: availablePos) {
                map[currPos.getRow()][currPos.getColumn()].setGodname(godName);
                map[currPos.getRow()][currPos.getColumn()].setWorker(playerId);
            }
        }
        updateScreen();
    }

    public void setSelectedPos(Position pos){
        currentChoice.add(pos);
        map[pos.getRow()][pos.getColumn()].setGodname(godname);
        map[pos.getRow()][pos.getColumn()].setWorker(playerId);
    }

    public void definePositions(){
        socketClient.send(new FirstPositioningNotification(currentChoice));
    }

    @Override
    public void chooseWorker(List<Position> availableWorkers) {
        availablePos.clear();
        availablePos.addAll(availableWorkers);
        state = "SELECTWORKER";
        updateScreen();
    }

    public void defineWorker(Position pos){
        socketClient.send(new ChooseWorkerNotification(pos));
    }

    @Override
    public void choosePower() {
        this.message = "Do you want to activate the card power?";
        state = "POWER";
        gui.showRequest();
    }

    public void getPowerSelection(boolean power){
        socketClient.send(new SetPowerNotification(power));
    }

    @Override
    public void choosePosition(List<Position> list) {
        availablePos.clear();
        availablePos.addAll(list);
        state = "SELECTPOSITION";
        updateScreen();
    }

    public void defineMovement(Position pos){
        socketClient.send(new ChoosePosNotification(pos));
    }

    @Override
    public void move(List<Movement> movements) {
        for(Movement move : movements){
            map[move.getNewPos().getRow()][move.getNewPos().getColumn()].setWorker(map[move.getOldPos().getRow()][move.getOldPos().getColumn()].getWorker());
            map[move.getOldPos().getRow()][move.getOldPos().getColumn()].setWorker(-1);
        }
    }

    @Override
    public void buildBlock(Position position, int height) {
        map[position.getRow()][position.getColumn()].setHeight(height);
    }

    @Override
    public void buildDome(Position position, int height) {
        map[position.getRow()][position.getColumn()].setHeight(height);
    }

    @Override
    public void updateScreen() {
        gui.showMap();
    }

    @Override
    public void askForReloadState() {
        this.message = "Do you want to use an existent save?";
        state = "RELOAD";
        gui.showRequest();
    }

    public void getReloadSelection(boolean reload){
        socketClient.send(new AskForReloadStateNotification(reload));
    }

    @Override
    public void reloadState(Cell[][] map, List<String> godNames) {

    }

    @Override
    public void handleDisconnection(int playerDisconnected) {
        this.message = "Player " + playerDisconnected + " disconnected, leaving the game.";
        gui.showMessage();
        socketClient.closeClient();
    }

    @Override
    public void notificationForOtherClient(String message) {
        this.message = message;
        gui.showMessage();
    }

    @Override
    public void elimination(boolean elim, String eliminatedPlayer) {
        if(elim) {
            this.message = "You lost";
        } else {
            this.message = eliminatedPlayer + " lost.";
        }
        gui.showMessage();
    }

    @Override
    public void win(boolean win, String winnerName) {
        if(win) {
            this.message = "You won";
        } else {
            this.message = winnerName + " won";
        }
        gui.showMessage();
        socketClient.closeClient();
    }

    @Override
    public void setID(int clientID) {
        this.clientID = clientID;
    }

    public String getName(){
        return this.name;
    }

    public String getMessage(){
        return this.message;
    }

    public String getGodName(){
        return this.godname;
    }

    public int getPlayers(){
        return this.playernumber;
    }

    public String getState(){
        return this.state;
    }

    public List<Integer> getGodList(){
        return this.chosen;
    }

    public String getGodOnMap(){
        return this.imagepath;
    }

    public List<Position> getAvailablePos(){
        return this.availablePos;
    }

    public Square[][] getMap() {
        return map;
    }

}