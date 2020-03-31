package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MoveTest {
    Board board = new Board();
    Cell[][] map;
    Move standardMove = new Move(board.getBoard(),true,false,false,false,false);
    Move apolloMove = new Move(board.getBoard(),true,false,true,false,false);
    Move minoMove = new Move(board.getBoard(),true,true,true,false,false);
    Move prometheusMove = new Move(board.getBoard(),true,false,false,true,true);
    Move artemisMove = new Move(board.getBoard(),true,false,false,true,false);
    Move minoReturn = new Move(board.getBoard(),false,true,true,false,false);
    Move apolloReturn = new Move(board.getBoard(),false,false,true,false,false);
    Position position = new Position(2,2);
    Worker target = new Worker(1,1);
    Worker enemy1 = new Worker(0,1);
    Worker enemy2 = new Worker(0,2);

    @Test
    public void availableMovesEasy() {
        target.setPosition(position);
        List<Position> availablePos = standardMove.availableCells(position, 1 );
        assertEquals(8, availablePos.size());
        assertEquals(1, availablePos.get(0).getRow());
        assertEquals(1, availablePos.get(0).getColumn());
        assertEquals(2, availablePos.get(4).getRow());
        assertEquals(3, availablePos.get(4).getColumn());
        assertEquals(3, availablePos.get(7).getRow());
        assertEquals(3, availablePos.get(7).getColumn());
        List<Position> availablePos1 = standardMove.availableWithGod(target );
        assertEquals(8,availablePos1.size());

    }

    @Test
    public void checkMapEasy() {
        map=board.getBoard();
        map[2][2].setWorkerID(11);
        assertEquals(0,map[2][2].getHeight());
        assertEquals(0,map[3][3].getHeight());
        assertEquals(11,map[2][2].getWorkerID());
        assertEquals(0,map[3][3].getWorkerID());
    }

    @Test
    public void checkPeopleNotPeople() {
        map=board.getBoard();
        target.setPosition(position);
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                map[i][j].setHeight(2);
            }
        }
        map[0][1].setHeight(0);
        // no escape
        map[2][2].setHeight(0);
        List<Position> availablePos1 = standardMove.availableCells(position, 1 );
        assertEquals(0,availablePos1.size());
        // 1 escape
        map[2][1].setHeight(1);
        ArrayList<Position> availablePos2 = standardMove.availableWithGod(target);
        assertEquals(1,availablePos2.size());
        assertEquals(1,availablePos2.get(0).getColumn());
        assertEquals(2,availablePos2.get(0).getRow());
        // diventa occupata
        map[2][1].setWorkerID(22);
        map[1][1].setHeight(1);
        ArrayList<Position> availablePos5 = standardMove.availableWithGod(target);
        assertEquals(1,availablePos5.size());
        assertEquals(1,availablePos5.get(0).getColumn());
        assertEquals(1,availablePos5.get(0).getRow());
        // overall standard con (2,1) occupata dal worker
        map[2][2].setHeight(3);
        List<Position> availablePos3 = standardMove.availableWithGod(target);
        assertEquals(7,availablePos3.size());
        assertEquals(3,availablePos3.get(3).getColumn());
        assertEquals(2,availablePos3.get(3).getRow());
        // overall Apollo con (2,1) occupata dal worker
        List<Position> availablePos6 = apolloMove.availableWithGod(target);
        assertEquals(1,availablePos6.size());
        assertEquals(1,availablePos6.get(0).getColumn());
        assertEquals(2,availablePos6.get(0).getRow());
        // diventa cupola
        map[2][3].setHeight(4);
        List<Position> availablePos4 = standardMove.availableWithGod(target);
        assertEquals(6,availablePos4.size());
        assertEquals(1,availablePos4.get(3).getColumn());
        assertEquals(3,availablePos4.get(3).getRow());
    }

    @Test
    public void checkMateNotMate() {
        map=board.getBoard();
        target.setPosition(position);
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                map[i][j].setHeight(2);
            }
        }
        map[0][1].setHeight(0);
        // no escape
        map[2][2].setHeight(0);
        List<Position> availablePos1 = standardMove.availableCells(position, 1 );
        assertEquals(0,availablePos1.size());
        // 1 escape but not people
        map[2][1].setHeight(1);
        ArrayList<Position> availablePos2 = apolloMove.availableWithGod(target);
        assertEquals(0,availablePos2.size());
        // 1 people
        map[2][1].setWorkerID(22);
        ArrayList<Position> availablePos3 = apolloMove.availableWithGod(target);
        assertEquals(1,availablePos3.size());
        assertEquals(1,availablePos3.get(0).getColumn());
        assertEquals(2,availablePos3.get(0).getRow());
        //1 people ma diventa il suo compagno
        map[2][1].setWorkerID(12);
        ArrayList<Position> availablePos4 = apolloMove.availableWithGod(target);
        assertEquals(0,availablePos4.size());
    }

    @Test
    public void fullMap() {
        map=board.getBoard();
        target.setPosition(position);
        map[2][2].setHeight(1);
        map[4][0].setWorkerID(12);
        map[4][0].setHeight(3);
        map[3][1].setWorkerID(1);
        map[3][1].setHeight(0);
        map[1][3].setWorkerID(2);
        map[1][3].setHeight(2);
        map[2][1].setWorkerID(21);
        map[2][1].setHeight(3);
        map[3][2].setWorkerID(22);
        map[3][2].setHeight(1);
        map[1][1].setHeight(4);
        map[1][2].setHeight(1);
        map[2][3].setHeight(4);
        map[3][3].setHeight(3);
        List<Position> availablePos7 = standardMove.availableCells(target.getPosition(),1);
        assertEquals(4,availablePos7.size());
        assertEquals(1,availablePos7.get(0).getRow());
        assertEquals(2,availablePos7.get(0).getColumn());
        List<Position> availablePos = standardMove.availableWithGod(target);
        assertEquals(1,availablePos.size());
        assertEquals(1,availablePos.get(0).getRow());
        assertEquals(2,availablePos.get(0).getColumn());
        List<Position> availablePos1 = apolloMove.availableWithGod(target);
        assertEquals(3,availablePos1.size());
        assertEquals(3,availablePos1.get(1).getRow());
        assertEquals(1,availablePos1.get(1).getColumn());
        List<Position> availablePos2 = minoMove.availableWithGod(target);
        assertEquals(2,availablePos2.size());
        assertEquals(1,availablePos2.get(0).getRow());
        assertEquals(3,availablePos2.get(0).getColumn());
        assertEquals(3,availablePos2.get(1).getRow());
        assertEquals(2,availablePos2.get(1).getColumn());
        List<Position> availablePos3 = prometheusMove.availableWithGod(target);
        assertEquals(1,availablePos3.size());
        assertEquals(1,availablePos3.get(0).getRow());
        assertEquals(2,availablePos3.get(0).getColumn());
        artemisMove.setLastMoveInitialPosition(new Position(1,2));
        List<Position> availablePos4 = artemisMove.availableWithGod(target);
        assertEquals(0,availablePos4.size());
    //    assertEquals(1,availablePos4.get(0).getRow());
    //    assertEquals(2,availablePos4.get(0).getColumn());

    }
    @Test
    public void realMove() {
        int i;
        Position mossa= new Position(1,2);
        map=board.getBoard();
        target.setPosition(position);
        map[2][2].setHeight(1);
        map[4][0].setWorkerID(12);
        map[4][0].setHeight(3);
        map[3][1].setWorkerID(1);
        map[3][1].setHeight(0);
        map[1][3].setWorkerID(2);
        map[1][3].setHeight(2);
        map[2][1].setWorkerID(21);
        map[2][1].setHeight(3);
        map[3][2].setWorkerID(22);
        map[3][2].setHeight(1);
        map[1][1].setHeight(4);
        map[1][2].setHeight(1);
        map[2][3].setHeight(4);
        map[3][3].setHeight(3);

 //       assertTrue(availablePos1.contains(mossa));
        i=standardMove.executeAction(mossa,target);
        assertEquals(0,i);
        assertEquals(11,target.getWorkerID());
        assertEquals(1,map[1][2].getHeight());
        assertEquals(1,target.getPosition().getRow());
        assertEquals(2,target.getPosition().getColumn());
        assertEquals(0,map[2][2].getWorkerID());
        enemy1.setPosition(new Position(3,1));
        apolloReturn.setLastMoveInitialPosition(new Position(2,2));
        apolloReturn.executeAutoAction(enemy1);
        assertEquals(1,map[2][2].getWorkerID());
        assertEquals(2,enemy1.getPosition().getRow());
        assertEquals(2,enemy1.getPosition().getColumn());
        enemy2.setPosition(new Position(1,3));
        minoReturn.setLastMoveInitialPosition(new Position(2,2));
        minoReturn.executeAutoAction(enemy2);
        assertEquals(2,map[0][4].getWorkerID());
        assertEquals(0,map[0][3].getWorkerID());
        assertEquals(0,enemy2.getPosition().getRow());
        assertEquals(4,enemy2.getPosition().getColumn());

    }
}