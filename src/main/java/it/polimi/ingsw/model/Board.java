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

    public Cell[][] getMap() {
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("-------------------------------------------\n");
        for(int raw = 0; raw < 5; raw++) {
            System.out.println("\t\t");
            for(int column = 0; column < 5; column++) {
                string.append("| ").append(map[raw][column].getWorkerID()).append("\t").append(map[raw][column].getHeight());
            }
            string.append(" |\n");
        }
        string.append("-------------------------------------------\n");
        return string.toString();
    }
}