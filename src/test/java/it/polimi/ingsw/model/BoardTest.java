package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void boardTest() {
        Board board = new Board();
        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 5; column++) {
                assertEquals(0, board.getBoard()[row][column].getWorkerID());
                assertEquals(0, board.getBoard()[row][column].getHeight());
            }
        }
    }
}