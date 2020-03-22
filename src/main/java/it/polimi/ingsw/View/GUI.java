package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.SocketClient;
import it.polimi.ingsw.Model.Position;

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
}