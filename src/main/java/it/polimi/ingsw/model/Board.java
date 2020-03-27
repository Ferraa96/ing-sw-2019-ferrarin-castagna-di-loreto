package it.polimi.ingsw.model;

public class Board {
    private Cell[][] cell;

    public Board() {
        cell = new Cell[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                cell[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getBoard() {
        return cell;
    }
}