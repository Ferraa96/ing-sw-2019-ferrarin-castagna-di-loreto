package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;

public class ChooseWorkerInstr implements Serializable {
    private Position pos;

    public ChooseWorkerInstr() {}

    public ChooseWorkerInstr(Position pos) {
        this.pos = pos;
    }

    public Position getPos() {
        return pos;
    }
}
