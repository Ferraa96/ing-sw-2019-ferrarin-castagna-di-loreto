package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ExecuteMoveTest {
    Board board = new Board();
    Cell[][] map;
    Move standardMove = new Move(board.getMap(),false,false,false,false);
    Move minoMove = new Move(board.getMap(),true,true,false,false);
    Move minoReturn = new Move(board.getMap(),true,true,false,false);
    Move apolloReturn = new Move(board.getMap(),false,true,false,false);
    Position position = new Position(2,2);
    Worker target = new Worker(1,0);
    Worker enemy1 = new Worker(0,0);
    Worker enemy2 = new Worker(0,1);

    @Test
    public void realMove() {
        int i;
        Position mossa= new Position(1,2);
        map=board.getMap();
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
        map[1][1].setHeight(4);
        map[1][2].setHeight(1);
        map[2][3].setHeight(4);
        map[3][3].setHeight(3);

        standardMove.executeAction(mossa,target);
        i=standardMove.getDownUp();
        assertEquals(0,i);
        assertEquals(2,target.getWorkerID());
        assertEquals(1,map[1][2].getHeight());
        assertEquals(1,target.getPosition().getRow());
        assertEquals(2,target.getPosition().getColumn());
        assertEquals(-1,map[2][2].getWorkerID());
        enemy1.setPosition(new Position(3,1));
        apolloReturn.setLastMoveInitialPosition(new Position(2,2));
        apolloReturn.executeAutoAction(enemy1);
        assertEquals(0,map[2][2].getWorkerID());
        assertEquals(2,enemy1.getPosition().getRow());
        assertEquals(2,enemy1.getPosition().getColumn());
        enemy2.setPosition(new Position(1,3));
        minoReturn.setLastMoveInitialPosition(new Position(2,2));
        minoReturn.executeAutoAction(enemy2);
        assertEquals(1,map[0][4].getWorkerID());
        assertEquals(-1,map[0][3].getWorkerID());
        assertEquals(0,enemy2.getPosition().getRow());
        assertEquals(4,enemy2.getPosition().getColumn());
    }

    @Test
    public void edgeKnock() {
        map=board.getMap();
        target.setPosition(new Position(0,0));
        List<Position> availablePos = standardMove.availableWithGod(target);
        assertEquals(3,availablePos.size());
        target.setPosition(new Position(2,1));
        map[2][0].setWorkerID(5);
        map[1][2].setWorkerID(4);
        map[1][1].setWorkerID(3);
        List<Position> availablePos1 = minoMove.availableWithGod(target);
        assertEquals(1,availablePos1.size());
        assertEquals(1,availablePos1.get(0).getRow());
        assertEquals(2,availablePos1.get(0).getColumn());
    }

}