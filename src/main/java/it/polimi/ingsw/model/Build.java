package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * type of effect: give worker permission to build a block
 */
public class Build implements Effect {
    Cell [][] map;
    private Boolean myWorker;
    private Boolean freeBlock;
    private Boolean specificPosition;
    private Boolean sameBefore;
    private Position lastBuildPosition;

    public Build(Boolean myWorker, Boolean freeBlock, Boolean specificPosition, Boolean sameBefore) {
        this.myWorker = myWorker;
        this.freeBlock = freeBlock;
        this.specificPosition = specificPosition;
        this.sameBefore = sameBefore;
    }

    //8 celle attorno senza cupola
    @Override
    public ArrayList<Position> availableCells(Position actualPosition, int numMoves) {
        ArrayList<Position> list = new ArrayList<>();
        int left, right, up, down;
        int row, column;
        row = actualPosition.getRow();
        column = actualPosition.getColumn();

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
                if (map[i][j].getHeight()!=4 && map[i][j].getWorkerID()==0) {
                    if (!(i==row && j==column))
                        list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    @Override
    public ArrayList<Position> availableWithGod(Worker target) {
        Position actualPosition = target.getPosition();
        int r,c;
            ArrayList<Position> possibleCells =new ArrayList<>(availableCells(actualPosition,1));
            for (Position current : possibleCells) {
                r = current.getRow();
                c = current.getColumn();
                if (specificPosition) {
                    if (sameBefore) {
                        //max height 2
                        if (map[r][c].getHeight()>2)
                            possibleCells.remove(current);
                    }
                    else
                        //notbefore di demeter
                        if (r==lastBuildPosition.getRow() && c==lastBuildPosition.getColumn())
                            possibleCells.remove(current);
                }
            }
        return possibleCells;
    }

    @Override
    public int executeAction(Position chosenCell, Worker worker) {
        //if (availableWithGod(worker).contains(chosenCell)) {
            if (freeBlock)
                map[chosenCell.getRow()][chosenCell.getColumn()].setHeight(map[chosenCell.getRow()][chosenCell.getColumn()].getHeight()+1);
            else
                map[chosenCell.getRow()][chosenCell.getColumn()].setHeight(4);
        //}
        //else
            //System.out.println("Build not available");
        return 0;
    }



    @Override
    public void executeAutoAction(Worker target) {
        map[lastBuildPosition.getRow()][lastBuildPosition.getColumn()].setHeight(map[lastBuildPosition.getRow()][lastBuildPosition.getColumn()].getHeight()+1);
    }

    //getter e setter vari
    @Override
    public Boolean getMyWorker() {
        return myWorker;
    }

    @Override
    public void setLastMoveInitialPosition(Position lastMoveInitialPosition) { }

    @Override
    public void setLastBuildPosition(Position lastBuildPosition) {
        this.lastBuildPosition = lastBuildPosition;
    }

    @Override
    public void setNoUp(Boolean noUp) { }

}