package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

public class WinNotification implements Serializable, MessageInterface {
    private int clientID;
    private final String playerName;

    public WinNotification(int clientID, String playerName) {
        this.clientID = clientID;
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }

    @Override
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public int getClientID() {
        return clientID;
    }
}
