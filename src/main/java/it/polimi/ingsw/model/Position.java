package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * used to store a position and in the communication client/server
 */
public class Position implements Serializable {
    private final int row;
    private final int column;

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

    @Override
    public boolean equals(Object position) {
        if(!(position instanceof Position)) {
            return false;
        }
        Position pos = (Position) position;
        return (this.row == pos.getRow() && this.column == pos.getColumn());
    }

    @Override
    public String toString() {
        return "row: " + row + " column: " + column;
    }
}