package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

/**
 * notify the disconnection of one client, the game end
 */
public class DisconnectionNotification implements Serializable, MessageInterface {
    private int clientID;

    @Override
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public int getClientID() {
        return clientID;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
