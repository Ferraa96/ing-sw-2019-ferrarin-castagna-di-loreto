package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;
import java.util.Map;

public class MoveInstr implements Serializable {
    private final Map<Integer, Position> movements;

    public MoveInstr(Map<Integer, Position> movements) {
        this.movements = movements;
    }

    public Map<Integer, Position> getMovements() {
        return movements;
    }
}
