package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;
import java.util.List;

public class EliminationNotification implements Serializable, MessageInterface {
    private int clientID;
    private final String playerName;
    private final List<Position> eliminatedWorkers;

    public EliminationNotification(int clientID, String playerName, List<Position> eliminatedWorkers) {
        this.clientID = clientID;
        this.playerName = playerName;
        this.eliminatedWorkers = eliminatedWorkers;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Position> getEliminatedWorkers() {
        return eliminatedWorkers;
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
