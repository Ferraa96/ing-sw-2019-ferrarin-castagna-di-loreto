package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Cell;

import java.io.Serializable;
import java.util.List;

/**
 * contains all the informations that allow the client to reload a saved game
 */
public class LoadGameNotification implements Serializable, MessageInterface {
    private int clientID;
    private final Cell[][] map;
    private List<String> godNames;

    public LoadGameNotification(Cell[][] map) {
        this.map = map;
    }

    public Cell[][] getMap() {
        return map;
    }

    public void setGodNames(List<String> godNames) {
        this.godNames = godNames;
    }

    public List<String> getGodNames() {
        return godNames;
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