package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Effects.Build;
import it.polimi.ingsw.model.Game.Board;
import it.polimi.ingsw.model.Game.Cell;
import it.polimi.ingsw.model.Game.Position;
import it.polimi.ingsw.model.Player.Worker;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AvailableBuildTest {
    Board board = new Board();
    Cell[][] map;
    Build standardBuild = new Build(board.getMap(), true, false);
    Build hephaestusBuild = new Build(board.getMap(),false,true);
    Build atlasBuild = new Build(board.getMap(),false,false);
    Build demeterBuild = new Build(board.getMap(),true,true);
    Position position = new Position(2, 2);
    Worker target = new Worker(1, 0);

    @Test
    public void availableBuildEasy() {
        target.setPosition(position);
        List<Position> availablePos = standardBuild.availableWithGod(target);
        assertEquals(8, availablePos.size());
        assertEquals(2, availablePos.get(4).getRow());
        assertEquals(3, availablePos.get(4).getColumn());
    }

    @Test
    public void checkPeople() {
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
        // 1 escape
        map[2][1].setHeight(1);
        List<Position> availablePos2 = standardBuild.availableWithGod(target);
        assertEquals(8, availablePos2.size());
        // diventa occupata
        map[2][1].setWorkerID(22);
        map[1][1].setHeight(1);
        List<Position> availablePos5 = standardBuild.availableWithGod(target);
        assertEquals(7, availablePos5.size());
        // overall standard con (2,1) occupata dal worker
        map[2][2].setHeight(3);
        List<Position> availablePos3 = standardBuild.availableWithGod(target);
        assertEquals(7, availablePos3.size());
        assertEquals(3, availablePos3.get(3).getColumn());
        assertEquals(2, availablePos3.get(3).getRow());
        // diventa cupola
        map[2][3].setHeight(3);
        map[2][3].setDome(true);
        List<Position> availablePos4 = standardBuild.availableWithGod(target);
        assertEquals(6, availablePos4.size());
        assertEquals(1, availablePos4.get(3).getColumn());
        assertEquals(3, availablePos4.get(3).getRow());
    }

    @Test
    public void fullMap() {
        map = board.getMap();
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
        map[1][1].setHeight(3);
        map[1][1].setDome(true);
        map[1][2].setHeight(1);
        map[2][3].setHeight(3);
        map[2][3].setDome(true);
        map[3][3].setHeight(3);
        List<Position> availablePos = standardBuild.availableWithGod(target);
        assertEquals(2, availablePos.size());
        assertEquals(1, availablePos.get(0).getRow());
        assertEquals(2, availablePos.get(0).getColumn());
        List<Position> availablePos1 = hephaestusBuild.availableWithGod(target);
        assertEquals(1, availablePos1.size());
        assertEquals(1, availablePos1.get(0).getRow());
        assertEquals(2, availablePos1.get(0).getColumn());
        List<Position> availablePos2 = atlasBuild.availableWithGod(target);
        assertEquals(2, availablePos2.size());
        assertEquals(1, availablePos2.get(0).getRow());
        assertEquals(2, availablePos2.get(0).getColumn());
        assertEquals(3, availablePos2.get(1).getRow());
        assertEquals(3, availablePos2.get(1).getColumn());
        demeterBuild.setLastBuildPosition(new Position(1, 2));
        List<Position> availablePos4 = demeterBuild.availableWithGod(target);
        assertEquals(1, availablePos4.size());
        assertEquals(3,availablePos4.get(0).getRow());
        assertEquals(3,availablePos4.get(0).getColumn());
    }

    @Test
    public void edge() {
        map=board.getMap();
        target.setPosition(new Position(0,0));
        List<Position> availablePos = standardBuild.availableWithGod(target);
        assertEquals(3,availablePos.size());
        target.setPosition(new Position(2,0));
        map[2][1].setWorkerID(22);
        map[1][0].setWorkerID(22);
        map[3][0].setWorkerID(12);
        List<Position> availablePos1 = standardBuild.availableWithGod(target);
        assertEquals(2,availablePos1.size());
        assertEquals(1,availablePos1.get(0).getRow());
        assertEquals(1,availablePos1.get(0).getColumn());
    }
}