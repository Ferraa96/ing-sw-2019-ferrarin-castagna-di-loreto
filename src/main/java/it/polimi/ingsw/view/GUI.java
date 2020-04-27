package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.SocketClient;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Position;

import java.util.List;
import java.util.Map;

public class GUI implements ViewInterface {
    private SocketClient socket;
    private int playerID;

    public GUI(SocketClient socket) {
        this.socket = socket;
    }

    @Override
    public void move(Map<Integer, Position> movement) {

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
    public void reloadState(Cell[][] map) {

    }

    @Override
    public void handleEndGame(String message) {

    }

    @Override
    public void chooseCard(List<Integer> cardList) {

    }

    @Override
    public void chooseCardList(int num) {

    }

    @Override
    public void firstPositioning(List<Position> availablePos) {

    }

    @Override
    public void setName(List<String> stringList, int playerID) {

    }

    @Override
    public void chooseWorker() {

    }

    @Override
    public void choosePower() {

    }

    @Override
    public void choosePosition(List<Position> list) {

    }
}