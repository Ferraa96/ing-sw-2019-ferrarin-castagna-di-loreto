package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.Client.SocketClient;
import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Movement;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.view.ViewInterface;
import javafx.application.Platform;

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
    private final List<Integer> chosen = new ArrayList<>();
    private final List<Position> availablePos = new ArrayList<>();
    private final List<Position> currentChoise = new ArrayList<>();

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

    public String setNameOnMap(){
        return this.name;
    }

    @Override
    public void setName(){
        gui.showLogin();
    }

    public void getSelectedCards(List<Integer> chosen){
        this.chosen.addAll(chosen);
        socketClient.send(new ChooseCardListNotification(chosen));
    }

    public int setPlayers(){
        return this.playernumber;
    }

    @Override
    public void chooseCardList(int num) {
        playernumber = num;
        gui.showSelectionCards();
    }

    public String getState(){
        return this.state;
    }

    public List<Integer> setGodList(){
        return this.chosen;
    }

    @Override
    public void setID(int clientID) {
        this.clientID = clientID;
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
            this.state = "FIRSTPOS";
        }else {
            for(Position currPos: availablePos) {
                map[currPos.getRow()][currPos.getColumn()].setGodname(godName);
                map[currPos.getRow()][currPos.getColumn()].setWorker(true);
            }
        }
        updateScreen();
    }

    public String setGodOnMap(){
        return this.imagepath;
    }

    public List<Position> setAvailablePos(){
        return this.availablePos;
    }

    public void getSelectedPos(Position pos){
        currentChoise.add(pos);
        map[pos.getRow()][pos.getColumn()].setGodname(godname);
        map[pos.getRow()][pos.getColumn()].setWorker(true);
    }

    public Square[][] getMap() {
        return map;
    }

    public void definePositions(){
        socketClient.send(new FirstPositioningNotification(currentChoise));
    }

    @Override
    public void chooseWorker(List<Position> availableWorkers) {
        availablePos.clear();
        availablePos.addAll(availableWorkers);
        this.godname = map[availableWorkers.get(0).getRow()][availableWorkers.get(0).getColumn()].getGodname();
        state = "SELECTWORKER";
        updateScreen();
    }

    public String getGodName(){
        return this.godname;
    }

    public void defineWorker(Position pos){
        socketClient.send(new ChooseWorkerNotification(pos));
    }

    @Override
    public void choosePower() {
        gui.showRequestPower();
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

    @Override
    public void move(List<Movement> movements) {
        for(Movement move : movements){
            map[move.getOldPos().getRow()][move.getOldPos().getColumn()].setWorker(false);
            map[move.getNewPos().getRow()][move.getNewPos().getColumn()].setWorker(true);
        }
    }

    public void defineMovement(Position pos){
        socketClient.send(new ChoosePosNotification(pos));
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

    }

    @Override
    public void reloadState(Cell[][] map, List<String> godNames) {

    }

    @Override
    public void handleDisconnection(int playerDisconnected) {

    }

    public String setMessage(){
        return this.message;
    }

    @Override
    public void notificationForOtherClient(String message) {
        this.message = message;
        gui.showMessage();
    }

    @Override
    public void elimination(boolean elim, String eliminatedPlayer) {

    }

    @Override
    public void win(boolean win, String winnerName) {

    }

}
