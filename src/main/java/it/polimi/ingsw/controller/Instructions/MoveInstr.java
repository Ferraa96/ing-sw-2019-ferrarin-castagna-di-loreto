package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Movement;

import java.io.Serializable;
import java.util.List;

public class MoveInstr implements Serializable {
    private final List<Movement> movements;

    public MoveInstr(List<Movement> movements) {
        this.movements = movements;
    }

    public List<Movement> getMovements() {
        return movements;
    }
}
