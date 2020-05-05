package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;
import java.util.List;

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
