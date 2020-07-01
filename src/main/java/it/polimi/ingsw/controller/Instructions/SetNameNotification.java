package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

/**
 * allows the player to set the name
 */
public class SetNameNotification implements Serializable, NotificationInterface {
    private int clientID;
    private boolean ok;
    private String name;

    public SetNameNotification(int clientID, boolean ok) {
        this.clientID = clientID;
        this.ok = ok;
    }

    public SetNameNotification(String name) {
        this.name = name;
    }

    public boolean isOk() {
        return ok;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(NotificationVisitor v) {
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
