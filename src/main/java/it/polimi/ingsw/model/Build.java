package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * type of effect: give worker permission to build a block
 */
public class Build implements Effect {
    private Cell [][] map;
    private Boolean nextBlock;
    private Boolean specific;
    private Position lastBuildPosition;

    public Build(Cell[][] map, Boolean nextBlock, Boolean specific) {
        this.map = map;
        this.nextBlock = nextBlock;
        this.specific = specific;
    }

    /**
     * called by availableWithGod
     * @param actualPosition worker's position
     * @return list of position around
     */
    private List<Position> availableCells(Position actualPosition) {
        List<Position> list = new ArrayList<>();
        int left, right, up, down;
        int row, column;
        row = actualPosition.getRow();
        column = actualPosition.getColumn();

        left = column - 1;
        right = column + 1 + 1;
        up = row - 1;
        down = row + 1 + 1;
        if(left < 0) { left = 0; }
        else if(right > 5) { right = 4; }
        if(up < 0) { up = 0; }
        else if(down > 5) { down = 4; }
        for(int i = up; i < down; i++) {
            for(int j = left; j < right; j++) {
                if (map[i][j].getHeight()!=4 && map[i][j].getWorkerID()==0) {
                    if (!(i == row && j == column))
                        list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    /**
     * search free cells around
     * @param target worker interested
     * @return list of available positions
     */
    @Override
    public List<Position> availableWithGod(Worker target) {
        Position actualPosition = target.getPosition();
        int r,c;
            List<Position> possibleCells =new ArrayList<>(availableCells(actualPosition));
        for (int i = 0; i < possibleCells.size(); i++) {
                r = possibleCells.get(i).getRow();
                c = possibleCells.get(i).getColumn();
                if (specific) {
                    if (!nextBlock) {
                        //max height 2
                        if (map[r][c].getHeight()>2) {
                            possibleCells.remove(possibleCells.get(i));
                            i--;
                        }
                    }
                    else
                        //notbefore di demeter
                        if (r==lastBuildPosition.getRow() && c==lastBuildPosition.getColumn()) {
                            possibleCells.remove(possibleCells.get(i));
                            i--;
                        }

                }
            }
        return possibleCells;
    }

    /**
     * do the action and update the map
     * @param chosenCell cell selected
     * @param worker target of action
     * @return height difference between final and initial cell
     */
    @Override
    public int executeAction(Position chosenCell, Worker worker) {
        if (!nextBlock) {
            if (!specific)
                map[chosenCell.getRow()][chosenCell.getColumn()].setHeight(4);
            else
                map[chosenCell.getRow()][chosenCell.getColumn()].setHeight(map[chosenCell.getRow()][chosenCell.getColumn()].getHeight() + 2);
        }
        else
            map[chosenCell.getRow()][chosenCell.getColumn()].setHeight(map[chosenCell.getRow()][chosenCell.getColumn()].getHeight()+1);
        return 0;
    }

    /**
     * not used here
     * @param enemy worker forced by mino/apollo
     */
    public void executeAutoAction(Worker enemy) { }

    /**
     * not used here
     * @param lastMoveInitialPosition initial cell of last move
     */
    public void setLastMoveInitialPosition(Position lastMoveInitialPosition) { }

    /**
     * set last build
     * @param lastBuildPosition cell of last build
     */
    @Override
    public void setLastBuildPosition(Position lastBuildPosition) {
        this.lastBuildPosition = lastBuildPosition;
    }

    /**
     * not used here
     * @param noUp true if athena triggers her power
     */
    @Override
    public void setNoUp(Boolean noUp) { }

}