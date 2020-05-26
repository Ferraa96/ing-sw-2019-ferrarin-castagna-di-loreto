package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;
import java.util.List;

/**
 * lets the player choose where he wants to position his workers in the first turn
 */
public class FirstPositioningNotification implements Serializable, MessageInterface {
    private int clientID;
    private final List<Position> positions;
    private String godName;
    private String userName;
    private boolean loadPos = false;

    public FirstPositioningNotification(List<Position> positions) {
        this.positions = positions;
    }

    public FirstPositioningNotification(List<Position> positions, String godName, String userName, int clientID) {
        this.positions = positions;
        this.godName = godName;
        this.userName = userName;
        this.clientID = clientID;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public String getGodName() {
        return godName;
    }

    public String getUserName() {
        return userName;
    }

    public void setLoadPos(boolean loadPos) {
        this.loadPos = loadPos;
    }

    public boolean isLoadPos() {
        return loadPos;
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
