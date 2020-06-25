package it.polimi.ingsw.model.Game;

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
        System.out.println("\t0\t\t1\t\t2\t\t3\t\t4");
        string.append("  -------------------------------------\n");
        for(int raw = 0; raw < 5; raw++) {
            string.append(raw).append(" ");
            for(int column = 0; column < 5; column++) {
                string.append("|").append(map[raw][column].getWorkerID());
                if(map[raw][column].isDome()) {
                    string.append("/-\\");
                } else {
                    string.append("   ");
                }
                string.append(map[raw][column].getHeight());
            }
            string.append(" |\n");
            string.append("  ------------------------------------\n");
        }
        return string.toString();
    }
}