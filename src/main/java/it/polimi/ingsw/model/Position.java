package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * used to store a position and in the communication client/server
 */
public class Position implements Serializable {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isEqual(Position position) {
        return (this.row == position.getRow() && this.column == position.getColumn());
    }
}