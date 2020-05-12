package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;
import java.util.List;

/**
 * lets the player choose where he wants to position his workers in the first turn
 */
public class FirstPositioningInstr implements Serializable, MessageInterface {
    private final List<Position> positions;
    private String godName;
    private boolean myTurn;

    public FirstPositioningInstr(List<Position> positions) {
        this.positions = positions;
    }

    public FirstPositioningInstr(List<Position> positions, String godName, boolean myTurn) {
        this.positions = positions;
        this.godName = godName;
        this.myTurn = myTurn;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public String getGodName() {
        return godName;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
