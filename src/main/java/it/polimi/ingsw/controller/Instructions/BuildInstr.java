package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;

public class BuildInstr implements Serializable, MessageInterface {
    private final Position pos;
    private final int height;
    private final boolean isDome;

    public BuildInstr(Position pos, int height, boolean isDome) {
        this.pos = pos;
        this.height = height;
        this.isDome = isDome;
    }

    public Position getPos() {
        return pos;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDome() {
        return isDome;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
