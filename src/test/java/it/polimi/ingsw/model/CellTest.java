package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CellTest {
    Board board= new Board();
    Cell[][] map;

    @Test
    public void checkMapEasy() {
        map=board.getBoard();
        map[2][2].setWorkerID(11);
        assertEquals(0,map[2][2].getHeight());
        assertEquals(0,map[3][3].getHeight());
        assertEquals(11,map[2][2].getWorkerID());
        assertEquals(0,map[3][3].getWorkerID());
    }
}
