package it.polimi.ingsw.model;

import javafx.geometry.Pos;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CellTest {
    Board board= new Board();
    Cell[][] map;
    Position pos = new Position(1,1);

    @Test
    public void checkMapEasy() {
        map=board.getMap();
        pos.toString();
        map[2][2].setWorkerID(11);
        map[0][0].setHeight(4);
        map[0][0].setDome(true);
        assertEquals(0,map[2][2].getHeight());
        assertEquals(0,map[3][3].getHeight());
        assertEquals(11,map[2][2].getWorkerID());
        assertEquals(-1,map[3][3].getWorkerID());
        assertEquals(true,map[0][0].isDome());
        map[3][3].setDome(true);
        assertEquals(0,map[3][3].getHeight());

    }
}
