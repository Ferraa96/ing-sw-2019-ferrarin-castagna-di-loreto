package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.Commands;
import it.polimi.ingsw.Controller.SocketInterface;

public class GUI implements ViewInterface {
    private SocketInterface socket;
    private int playerID;

    public GUI(SocketInterface socket) {
        this.socket = socket;
    }

    @Override
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public void move(int player, int raw, int column) {

    }

    @Override
    public void buildBlock(int raw, int column, int height) {

    }

    @Override
    public void buildDome(int raw, int column, int height) {

    }

    @Override
    public void updateScreen() {

    }

    @Override
    public void updateServer(Commands commands) {

    }
}