package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Effects.Movement;

import java.io.Serializable;
import java.util.List;

/**
 * notify to the player a move performed by another client
 */
public class MoveNotification implements Serializable, NotificationInterface {
    private int clientID;
    private final List<Movement> movements;

    public MoveNotification(List<Movement> movements) {
        this.movements = movements;
    }

    public List<Movement> getMovements() {
        return movements;
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
