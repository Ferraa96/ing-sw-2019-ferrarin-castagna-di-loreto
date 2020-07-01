package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

/**
 * notify the disconnection of one client, the game end
 */
public class DisconnectionNotification implements Serializable, NotificationInterface {
    private int clientID;
    private final String message;

    public DisconnectionNotification(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public int getClientID() {
        return clientID;
    }

    @Override
    public void accept(NotificationVisitor v) {
        v.visit(this);
    }
}
