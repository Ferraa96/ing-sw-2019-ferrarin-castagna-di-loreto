package it.polimi.ingsw.Model;

import java.io.Serializable;

public class Position implements Serializable {
    private int row;
    private int column;
    private OnBoardType type;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setColumn(int posY) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public void setType(OnBoardType type) {
        this.type = type;
    }

    public OnBoardType getType() {
        return type;
    }
}