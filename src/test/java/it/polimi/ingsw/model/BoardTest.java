package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Game.Board;
import it.polimi.ingsw.model.Game.Cell;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void boardTest() {
        Board board = new Board();
        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 5; column++) {
                assertEquals(-1, board.getMap()[row][column].getWorkerID());
                assertEquals(0, board.getMap()[row][column].getHeight());
            }
        }
        board.toString();
        Board board2 = new Board();
        Cell[][] map;
        map = board.getMap();
        board2.setMap(map);
    }
}