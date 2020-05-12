package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Cell;

import java.io.Serializable;
import java.util.List;

/**
 * contains all the informations that allow the client to reload a saved game
 */
public class LoadGameInstr implements Serializable, MessageInterface {
    private final Cell[][] map;
    private List<String> godNames;

    public LoadGameInstr(Cell[][] map) {
        this.map = map;
    }

    public Cell[][] getMap() {
        return map;
    }

    public void setGodNames(List<String> godNames) {
        this.godNames = godNames;
    }

    public List<String> getGodNames() {
        return godNames;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
