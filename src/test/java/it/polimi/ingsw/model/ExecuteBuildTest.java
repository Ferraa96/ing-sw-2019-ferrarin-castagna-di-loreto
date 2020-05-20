package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ExecuteBuildTest {
    Board board = new Board();
    Cell[][] map;
    Build standardBuild = new Build(board.getMap(),true,false);
    Build hephaestusBuild = new Build(board.getMap(),false,true);
    Build atlasBuild = new Build(board.getMap(),false,false);
    Build demeterBuild = new Build(board.getMap(),true,true);
    Position position = new Position(2,2);
    Worker target = new Worker(1,0);

    @Test
    public void realMove() {
        int i;
        Position mossa= new Position(1,2);
        map=board.getMap();
        target.setPosition(position);
        map[2][2].setWorkerID(11);
        map[2][2].setHeight(1);
        map[4][0].setWorkerID(12);
        map[4][0].setHeight(3);
        map[3][1].setWorkerID(1);
        map[3][1].setHeight(0);
        map[1][3].setWorkerID(2);
        map[1][3].setHeight(2);
        map[2][1].setWorkerID(21);
        map[2][1].setHeight(3);
        map[3][2].setHeight(1);
        map[1][1].setHeight(4);
        map[1][2].setHeight(1);
        map[2][3].setHeight(4);
        map[3][3].setHeight(3);

        standardBuild.executeAction(mossa,target);
        i=standardBuild.getDownUp();
        assertEquals(0,i);
        assertEquals(2,target.getWorkerID());
        assertEquals(2,map[1][2].getHeight());
        assertEquals(2,target.getPosition().getRow());
        assertEquals(2,target.getPosition().getColumn());
        assertEquals(11,map[2][2].getWorkerID());
        map[3][2].setWorkerID(22);
        hephaestusBuild.executeAction(mossa,target);
        assertEquals(4,map[1][2].getHeight());
        demeterBuild.executeAction(new Position(3,3),target);
        assertEquals(4,map[1][2].getHeight());
        assertEquals(2,target.getPosition().getRow());
        assertEquals(2,target.getPosition().getColumn());
        atlasBuild.executeAction(new Position(0,0),target);
        atlasBuild.executeAutoAction(target,mossa,target);
        assertEquals(0,map[0][0].getHeight());
        assertEquals(true, map[0][0].isDome());
    }
}