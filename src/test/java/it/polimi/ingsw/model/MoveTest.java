package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MoveTest {
    Board board = new Board();
    Move move = new Move(board.getBoard());

    @Test
    public void availableMoves() {
        List<Position> availablePos = move.available(2, 3, 1);
        assertEquals(7, availablePos.size());
        assertEquals(1, availablePos.get(0).getRow());
        assertEquals(2, availablePos.get(0).getColumn());
    }

    @Test
    public void firstPositioning() {
        List<Position> availablePos = move.firstPositioning();
        assertEquals(23, availablePos.size());
    }

    @Test
    public void basicMove() {
    }
}