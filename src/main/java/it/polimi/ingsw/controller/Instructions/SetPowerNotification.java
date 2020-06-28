package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

/**
 * allows the player activate the power of his card
 */
public class SetPowerNotification implements Serializable, NotificationInterface {
    private int clientID;
    private boolean isPower;

    public SetPowerNotification(int clientID) {
        this.clientID = clientID;
    }

    public SetPowerNotification(boolean isPower) {
        this.isPower = isPower;
    }

    public boolean isPower() {
        return isPower;
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
