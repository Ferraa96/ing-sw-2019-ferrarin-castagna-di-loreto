package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;

/**
 * notify to the client a build move done by a player
 */
public class BuildNotification implements Serializable, MessageInterface {
    private int clientID;
    private final Position pos;
    private final int height;
    private final boolean isDome;

    public BuildNotification(Position pos, int height, boolean isDome) {
        this.pos = pos;
        this.height = height;
        this.isDome = isDome;
    }

    public Position getPos() {
        return pos;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDome() {
        return isDome;
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
