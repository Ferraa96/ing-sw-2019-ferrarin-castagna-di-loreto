package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.Client.SocketClient;
import it.polimi.ingsw.controller.Instructions.ChooseCardListNotification;
import it.polimi.ingsw.controller.Instructions.ChooseCardNotification;
import it.polimi.ingsw.controller.Instructions.SetNameNotification;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Movement;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.view.ViewInterface;

import java.util.ArrayList;
import java.util.List;

public class GUIHandler implements ViewInterface {

    private SocketClient socketClient;
    private GUI gui;
    private int playernumber;
    private String name = "";
    private String ip;
    private String currentScene = "welcome";
    private String imagepath;
    private int card;
    private int clientID;
    private List<Integer> chosen = new ArrayList<>();

    public GUIHandler(GUI gui){
        this.gui = gui;
    }

    public void getLoginInfo(String name,String ip){
        this.name = name;
        this.ip = ip;
        socketClient = new SocketClient(1);
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
        this.card = card;
        this.imagepath = imagepath;
        socketClient.send(new ChooseCardNotification(this.card));
    }

    @Override
    public void firstPositioning(List<Position> availablePos, String godName, boolean isMyTurn) {
        if(isMyTurn) {
            gui.showMap();
        }
    }

    public String setGodOnMap(){
        return this.imagepath;
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
