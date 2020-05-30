package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.Client.SocketClient;
import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Movement;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.view.ViewInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that recives informations from the controllers and takes care to call the correct display method
 * and communicate those information to the server
 *
 */
public class GUIHandler implements ViewInterface {

    private SocketClient socketClient;
    private final GUI gui;
    private int playernumber;
    private String name = "";
    private String imagepath;
    private List<String> godName = new ArrayList<>();
    private String message;
    private final List<String> userName = new ArrayList<>();
    private String state;
    private boolean isMyTurn;
    private Square[][] map;
    private int scanId = -1;
    private final List<Integer> chosen = new ArrayList<>();
    private final List<Position> availablePos = new ArrayList<>();
    private final List<Position> currentChoice = new ArrayList<>();

    public GUIHandler(GUI gui){
        this.gui = gui;
    }

    /**
     * let the player set his name
     * @param name the input
     *
     */
    public void getLoginInfo(String name,String ip){
        this.name = name;
        socketClient = new SocketClient();
        map = new Square[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                map[i][j] = new Square();
            }
        }
        if(socketClient.connectGUI(ip,this)){
            socketClient.send(new SetNameNotification(name));
        }else{
            setName();
        }
    }

    @Override
    public void setName(){
        state = "SET NAME";
        gui.showLogin();
    }

    public void getSelectedCards(List<Integer> chosen){
        this.chosen.addAll(chosen);
        socketClient.send(new ChooseCardListNotification(chosen));
    }

    /**
     * Call to choose 'num' cards between all
     * @param num the amount of players playing
     */
    @Override
    public void chooseCardList(int num) {
        playernumber = num;
        state = "CARD LIST";
        gui.showSelectionCards();
    }

    /**
     *Choose the card you want to play with between the ones chosen before
     */
    @Override
    public void chooseCard(List<Integer> cardList) {
        chosen.clear();
        this.chosen.addAll(cardList);
        state = "SET CARD";
        gui.showCardSelection();
    }

    /**
     * Used to get the card selected
     * @param imagepath the path to correct god image
     */
    public void getCard(int card, String imagepath){
        this.imagepath = imagepath;
        socketClient.send(new ChooseCardNotification(card));
    }

    /**
     * Method called for every player to set the initial positions
     * @param availablePos list of the available postions
     * @param godName the god card of the player who is playing in this turn
     * scanId is used to identify the player and connect it to the worker
     */
    @Override
    public void firstPositioning(List<Position> availablePos, List<String> godName, List<String> userName, int client, boolean isMyTurn) {
        scanId++;
        if (client == 0) {
            this.userName.addAll(userName);
            this.godName.addAll(godName);
        }
        this.playernumber = userName.size();
        if(isMyTurn) {
            this.isMyTurn = true;
            this.availablePos.addAll(availablePos);
            this.message = "Select Workers position";
            this.state = "FIRSTPOS";
        }else {
            this.isMyTurn = false;
            state = "WAIT";
            for(Position currPos: availablePos) {
                map[currPos.getRow()][currPos.getColumn()].setWorker(scanId);
            }
        }
        updateScreen();
    }

    /**
     * Used to get the position of the worker and bind it to the player
     * @param pos contains the position of the worker placed
     */
    public void setSelectedPos(Position pos){
        currentChoice.add(pos);
        this.state = "POS";
        map[pos.getRow()][pos.getColumn()].setWorker(scanId);
    }

    public void definePositions(){
        this.state = "WAIT";
        socketClient.send(new FirstPositioningNotification(currentChoice));
    }

    /**
     * Choose which of the two workers you want to move
     */
    @Override
    public void chooseWorker(List<Position> availableWorkers) {
        availablePos.clear();
        availablePos.addAll(availableWorkers);
        isMyTurn = true;
        message = "Select the worker you want to use";
        state = "SELECTWORKER";
        updateScreen();
    }

    public void defineWorker(Position pos){
        this.state = "WAIT";
        socketClient.send(new ChooseWorkerNotification(pos));
    }

    /**
     * Choose whether you want to activate the power or not
     */
    @Override
    public void choosePower() {
        this.message = "Do you want to activate the card power?";
        isMyTurn = true;
        state = "POWER";
        gui.showRequest();
    }

    public void getPowerSelection(boolean power){
        this.state = "WAIT";
        socketClient.send(new SetPowerNotification(power));
    }

    /**
     * After the worker selection choose where to move it
     * @param list contains the positions where the worker can go
     */
    @Override
    public void choosePosition(List<Position> list) {
        availablePos.clear();
        availablePos.addAll(list);
        isMyTurn = true;
        this.message = "Do your moves & buildings";
        state = "SELECTPOSITION";
        updateScreen();
    }

    public void defineMovement(Position pos){
        this.state = "WAIT";
        socketClient.send(new ChoosePosNotification(pos));
    }

    /**
     * Used to refresh the map deleting the elements from the old pos and adding them to the new one
     * @param movements Contains the old position and the new position after the movement is done
     */
    @Override
    public void move(List<Movement> movements) {
        List<Integer> id = new ArrayList<>();
        isMyTurn = true;
        for(Movement currMove: movements) {
            id.add(map[currMove.getOldPos().getRow()][currMove.getOldPos().getColumn()].getWorker());
            map[currMove.getOldPos().getRow()][currMove.getOldPos().getColumn()].setWorker(-1);
        }
        for(int i = 0; i < movements.size(); i++ ){
            map[movements.get(i).getNewPos().getRow()][movements.get(i).getNewPos().getColumn()].setWorker(id.get(i));
        }
    }

    /**
     * Set the new height after a build has been places
     */
    @Override
    public void buildBlock(Position position, int height) {
        state = "BLOCK";
        isMyTurn = true;
        map[position.getRow()][position.getColumn()].setHeight(height);
    }

    /**
     * Set the dome flag true when a dome has been built
     */
    @Override
    public void buildDome(Position position, int height) {
        state = "DOME";
        isMyTurn = true;
        map[position.getRow()][position.getColumn()].setHeight(height);
        map[position.getRow()][position.getColumn()].setDome(true);
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
    public void reloadState(Cell[][] map, List<String> godNames, List<String> userNames) {
        HashMap<Integer, Position> workerPos = new HashMap<>();
        message = "Loading game...";
        gui.showMessage();
        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 5; column++) {
                int height = map[row][column].getHeight();
                if(height > 0) {
                    if(map[row][column].isDome()) {
                        buildDome(new Position(row, column), height);
                    } else {
                        buildBlock(new Position(row, column), height);
                    }
                }
                if(map[row][column].getWorkerID() != -1) {
                    workerPos.put(map[row][column].getWorkerID(), new Position(row, column));
                }
            }
        }
        for(int i = 0; i < godNames.size(); i++) {
            List<Position> myWorker = new ArrayList<>();
            myWorker.add(workerPos.get(i * 2));
            myWorker.add(workerPos.get(i * 2 + 1));
            int id = i;
            imagepath = convertGod(godNames.get(id));
            firstPositioning(myWorker, godNames, userNames, id, false);
        }
    }

    @Override
    public void handleDisconnection(String message) {
        state = "END";
        this.message = message;
        socketClient.closeClient();
        gui.showMessage();
    }

    @Override
    public void notificationForOtherClient(String message) {
        this.message = message;
        isMyTurn = false;
        switch (state){
            case "SET NAME":
            case "CARD LIST":
            case "SET CARD":
                gui.showMessage();
                break;
            default:
                gui.showMap();
                break;
        }
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
        state = "END";
        if(win) {
            this.message = "You won";
        } else {
            this.message = winnerName + " won";
        }
        socketClient.closeClient();
        gui.showMessage();
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

    public List<String> getGodName() {
        return this.godName;
    }

    public List<String> getUsername(){
        return this.userName;
    }

    public String getName(){
        return this.name;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public String getMessage(){
        return this.message;
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