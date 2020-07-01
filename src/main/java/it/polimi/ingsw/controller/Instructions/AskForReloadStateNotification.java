package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

/**
 * ask the player if he wants to reload a previous game and return to the server his answer
 */
public class AskForReloadStateNotification implements Serializable, NotificationInterface {
    private int clientID;
    private boolean response;

    public AskForReloadStateNotification(int clientID) {
        this.clientID = clientID;
    }

    public AskForReloadStateNotification(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
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
