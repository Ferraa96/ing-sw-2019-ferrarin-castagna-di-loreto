package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Game.Position;

import java.io.Serializable;
import java.util.List;

/**
 * lets the player choose the position of the move / build
 */
public class ChoosePosNotification implements Serializable, MessageInterface {
    private int clientID;
    private List<Position> availablePositions;
    private Position chosenPos;

    public ChoosePosNotification(Position chosenPos) {
        this.chosenPos = chosenPos;
    }

    public ChoosePosNotification(List<Position> availablePositions, int clientID) {
        this.availablePositions = availablePositions;
        this.clientID = clientID;
    }

    public List<Position> getAvailablePositions() {
        return availablePositions;
    }

    public Position getChosenPos() {
        return chosenPos;
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
