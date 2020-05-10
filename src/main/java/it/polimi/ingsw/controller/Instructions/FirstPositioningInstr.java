package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;
import java.util.List;

/**
 * lets the player choose where he wants to position his workers in the first turn
 */
public class FirstPositioningInstr implements Serializable, MessageInterface {
    private final List<Position> positions;

    public FirstPositioningInstr(List<Position> positions) {
        this.positions = positions;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
