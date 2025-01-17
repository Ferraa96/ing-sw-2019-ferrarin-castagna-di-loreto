package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Effects.Move;
import it.polimi.ingsw.model.Game.Board;
import it.polimi.ingsw.model.Game.Cell;
import it.polimi.ingsw.model.Game.Position;
import it.polimi.ingsw.model.Player.Worker;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AvailableMovesTest {
    Board board = new Board();
    Cell[][] map;
    Move standardMove = new Move(board.getMap(), false, false, false, false);
    Move apolloMove = new Move(board.getMap(), false, true, false, false);
    Move minoMove = new Move(board.getMap(), true, true, false, false);
    Move prometheusMove = new Move(board.getMap(), false, false, true, false);
    Move artemisMove = new Move(board.getMap(), false, false, true, true);
    Position position = new Position(2, 2);
    Worker target = new Worker(1, 0);

    @Test
    public void availableMovesEasy() {
        target.setPosition(position);
        List<Position> availablePos = standardMove.availableWithGod(target);
        assertEquals(8, availablePos.size());
        assertEquals(1, availablePos.get(0).getRow());
        assertEquals(1, availablePos.get(0).getColumn());
        assertEquals(2, availablePos.get(4).getRow());
        assertEquals(3, availablePos.get(4).getColumn());
        assertEquals(3, availablePos.get(7).getRow());
        assertEquals(3, availablePos.get(7).getColumn());
        List<Position> availablePos1 = standardMove.availableWithGod(target);
        assertEquals(8, availablePos1.size());

    }

    @Test
    public void checkPeopleNotPeople() {
        map = board.getMap();
        target.setPosition(position);
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                map[i][j].setHeight(2);
            }
        }
        map[0][1].setHeight(0);
        // no escape
        map[2][2].setHeight(0);
//        List<Position> availablePos1 = standardMove.availableCells(position, 1 );
//        assertEquals(0,availablePos1.size());
        // 1 escape
        map[2][1].setHeight(1);
        List<Position> availablePos2 = standardMove.availableWithGod(target);
        assertEquals(1, availablePos2.size());
        assertEquals(1, availablePos2.get(0).getColumn());
        assertEquals(2, availablePos2.get(0).getRow());
        // diventa occupata
        map[2][1].setWorkerID(22);
        map[1][1].setHeight(1);
        List<Position> availablePos5 = standardMove.availableWithGod(target);
        assertEquals(1, availablePos5.size());
        assertEquals(1, availablePos5.get(0).getColumn());
        assertEquals(1, availablePos5.get(0).getRow());
        // overall standard con (2,1) occupata dal worker
        map[2][2].setHeight(3);
        List<Position> availablePos3 = standardMove.availableWithGod(target);
        assertEquals(7, availablePos3.size());
        assertEquals(3, availablePos3.get(3).getColumn());
        assertEquals(2, availablePos3.get(3).getRow());
        // overall Apollo con (2,1) occupata dal worker
        List<Position> availablePos6 = apolloMove.availableWithGod(target);
        assertEquals(1, availablePos6.size());
        assertEquals(1, availablePos6.get(0).getColumn());
        assertEquals(2, availablePos6.get(0).getRow());
        // diventa cupola
        map[2][3].setHeight(3);
        map[2][3].setDome(true);
        List<Position> availablePos4 = standardMove.availableWithGod(target);
        assertEquals(6, availablePos4.size());
        assertEquals(1, availablePos4.get(3).getColumn());
        assertEquals(3, availablePos4.get(3).getRow());
    }

    @Test
    public void checkMateNotMate() {
        map = board.getMap();
        target.setPosition(position);
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                map[i][j].setHeight(2);
            }
        }
        map[0][1].setHeight(0);
        // no escape
        map[2][2].setHeight(0);
        //    List<Position> availablePos1 = standardMove.availableCells(position, 1);
        //    assertEquals(0,availablePos1.size());
        // 1 escape but not people
        map[2][1].setHeight(1);
        List<Position> availablePos2 = apolloMove.availableWithGod(target);
        assertEquals(0, availablePos2.size());
        // 1 people
        map[2][1].setWorkerID(1);
        List<Position> availablePos3 = apolloMove.availableWithGod(target);
        assertEquals(1, availablePos3.size());
        assertEquals(1, availablePos3.get(0).getColumn());
        assertEquals(2, availablePos3.get(0).getRow());
        //1 people ma diventa il suo compagno
        map[2][1].setWorkerID(3);
        List<Position> availablePos4 = apolloMove.availableWithGod(target);
        assertEquals(0, availablePos4.size());
    }

    @Test
    public void fullMap() {
        map = board.getMap();
        target.setPosition(position);
        map[2][2].setHeight(1);
        map[4][0].setWorkerID(3);
        map[4][0].setHeight(3);
        map[3][1].setWorkerID(0);
        map[3][1].setHeight(0);
        map[1][3].setWorkerID(1);
        map[1][3].setHeight(2);
        map[2][1].setWorkerID(4);
        map[2][1].setHeight(3);
        map[3][2].setWorkerID(5);
        map[3][2].setHeight(1);
        map[1][1].setHeight(3);
        map[1][1].setDome(true);
        map[1][2].setHeight(1);
        map[2][3].setHeight(3);
        map[2][3].setDome(true);
        map[3][3].setHeight(3);
        List<Position> availablePos = standardMove.availableWithGod(target);
        assertEquals(1, availablePos.size());
        assertEquals(1, availablePos.get(0).getRow());
        assertEquals(2, availablePos.get(0).getColumn());
        List<Position> availablePos1 = apolloMove.availableWithGod(target);
        assertEquals(3, availablePos1.size());
        assertEquals(3, availablePos1.get(1).getRow());
        assertEquals(1, availablePos1.get(1).getColumn());
        List<Position> availablePos2 = minoMove.availableWithGod(target);
        assertEquals(2, availablePos2.size());
        assertEquals(1, availablePos2.get(0).getRow());
        assertEquals(3, availablePos2.get(0).getColumn());
        assertEquals(3, availablePos2.get(1).getRow());
        assertEquals(2, availablePos2.get(1).getColumn());
        List<Position> availablePos3 = prometheusMove.availableWithGod(target);
        assertEquals(1, availablePos3.size());
        assertEquals(1, availablePos3.get(0).getRow());
        assertEquals(2, availablePos3.get(0).getColumn());
        artemisMove.setLastMoveInitialPosition(new Position(1, 2));
        artemisMove.setLastBuildPosition(new Position(1, 2));
        List<Position> availablePos4 = artemisMove.availableWithGod(target);
        assertEquals(0, availablePos4.size());

    }
}