package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.Commands;
import it.polimi.ingsw.Controller.Instruction;

import java.util.ArrayList;
import java.util.List;

public class Move implements Effect {
    OnBoard[][] map;

    public Move(OnBoard[][] map) {
        this.map = map;
    }

    public List<Position> available(int row, int column, OnBoardType type, int numMoves) {
        List<Position> list = new ArrayList<>();
        int left, right, up, down;
        left = column - numMoves;
        right = column + numMoves + 1;
        up = row - numMoves;
        down = row + numMoves + 1;
        if(left < 0) {
            left = 0;
        } else if(right > 5) {
            right = 4;
        }
        if(up < 0) {
            up = 0;
        } else if(down > 5) {
            down = 4;
        }
        for(int i = up; i < down; i++) {
            for(int j = left; j < right; j++) {
                if(map[i][j].getType().equals(type)) {
                    list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    public List<Position> firstPositioning() {
        List<Position> list = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(map[i][j].getType().equals(OnBoardType.nothing)) {
                    list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    public void basicMove(Position pos, int playerID) {
        Commands commands = new Commands(pos, playerID);
        commands.setInstruction(Instruction.move);
    }
}
