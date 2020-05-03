package it.polimi.ingsw.model;

import java.io.Serializable;

public class Movement implements Serializable {
    Position oldPos;
    Position newPos;

    public Movement(Position oldPos, Position newPos) {
        this.oldPos = oldPos;
        this.newPos = newPos;
    }

    public Position getOldPos() {
        return oldPos;
    }

    public Position getNewPos() {
        return newPos;
    }
}
