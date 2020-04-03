package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.SocketClient;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Position;

import java.util.List;

public class GUI implements ViewInterface {
    private SocketClient socket;
    private int playerID;

    public GUI(SocketClient socket) {
        this.socket = socket;
    }

    @Override
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public void move(int player, Position position) {

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
    public void chooseCard(List<Integer> cardList) {

    }

    @Override
    public void chooseCardList(int num) {

    }

    @Override
    public void firstPositioning(List<Position> availablePos) {

    }

    @Override
    public void resumeGame(Cell[][] cells) {

    }

    @Override
    public void setName(List<String> stringList) {

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