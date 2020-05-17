package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

/**
 * allows the player to set the name
 */
public class SetNameNotification implements Serializable, MessageInterface {
    private int clientID;
    private boolean ok;
    private String name;

    public SetNameNotification(int clientID, boolean ok) {
        this.clientID = clientID;
        this.ok = ok;
    }

    public SetNameNotification(String name, int clientID) {
        this.name = name;
        this.clientID = clientID;
    }

    public boolean isOk() {
        return ok;
    }

    public String getName() {
        return name;
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
