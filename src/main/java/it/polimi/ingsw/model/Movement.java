package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * nome giocatore vincente, worker selezionabile
 * nomi giocatori, fix bug mino apollo
 */
public class Movement implements Serializable {
    private final Position oldPos;
    private final Position newPos;

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

    @Override
    public String toString() {
        return "old: " + oldPos + " new: " + newPos;
    }
}
