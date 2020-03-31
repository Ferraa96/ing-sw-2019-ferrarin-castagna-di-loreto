package it.polimi.ingsw.model;

/**
 * the 5x5 gamemap
 */
public class Board {
    private Cell[][] map;

    /**
     * board builder
     */
    public Board() {
        map = new Cell[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                map[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getBoard() {
        return map;
    }
}