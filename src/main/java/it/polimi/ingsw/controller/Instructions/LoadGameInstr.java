package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Cell;

import java.io.Serializable;

/**
 * contains all the informations that allow the client to reload a saved game
 */
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
