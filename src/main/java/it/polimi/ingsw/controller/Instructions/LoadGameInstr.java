package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Cell;

import java.io.Serializable;

public class LoadGameInstr implements Serializable, MessageInterface {
    private final Cell[][] map;

    public LoadGameInstr(Cell[][] map) {
        this.map = map;
    }

    public Cell[][] getMap() {
        return map;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
