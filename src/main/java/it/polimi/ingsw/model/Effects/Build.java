package it.polimi.ingsw.model.Effects;

import it.polimi.ingsw.controller.Instructions.BuildNotification;
import it.polimi.ingsw.model.Game.Cell;
import it.polimi.ingsw.model.Game.Position;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * type of effect: give worker permission to build a block
 */
public class Build implements Effect {
    private final Cell[][] map;
    private final Boolean nextBlock;
    private final Boolean specific;
    private Position lastBuildPosition;
    private List<Position> possibleCells;

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
        right = column + 2;
        up = row - 1;
        down = row + 2;
        if(left < 0) { left = 0; }
        else if(right > 5) { right = 5; }
        if(up < 0) { up = 0; }
        else if(down > 5) { down = 5; }
        for(int i = up; i < down; i++) {
            for(int j = left; j < right; j++) {
                if (!map[i][j].isDome() && map[i][j].getWorkerID() == -1) {
                    if (!(i == row && j == column))
                        list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    /**
     * remove correct positions considering specific build condition
     * @param r row index
     * @param c column index
     * @param curr possibleCells current cell
     * @return index of cell in possibleCells
     */
    private int specificBuild(int r, int c, int curr) {
        if (specific) {
            if (!nextBlock) {
                //max height 2
                if (map[r][c].getHeight()>2) {
                    possibleCells.remove(possibleCells.get(curr));
                    curr--;
                }
            }
            else
                //notbefore di demeter
                if (r==lastBuildPosition.getRow() && c==lastBuildPosition.getColumn()) {
                    possibleCells.remove(possibleCells.get(curr));
                    curr--;
                }
        }
        return curr;
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
            possibleCells =new ArrayList<>(availableCells(actualPosition));
        for (int i = 0; i < possibleCells.size(); i++) {
                r = possibleCells.get(i).getRow();
                c = possibleCells.get(i).getColumn();
                i = specificBuild(r, c, i);
            }
        return possibleCells;
    }


    /**
     * do the action and update the map
     * @param chosenCell cell selected
     * @param worker target of action
     * @return message to send o view
     */
    @Override
    public BuildNotification executeAction(Position chosenCell, Worker worker) {
        int height = map[chosenCell.getRow()][chosenCell.getColumn()].getHeight();
        if (!nextBlock) {
            if (!specific) {
                map[chosenCell.getRow()][chosenCell.getColumn()].setDome(true);
                return new BuildNotification(chosenCell, height, true);
            }
            else {
                height = height + 2;
                map[chosenCell.getRow()][chosenCell.getColumn()].setHeight(height);
            }
        }
        else {
            height++;
            if (height==4){
                height=3;
                map[chosenCell.getRow()][chosenCell.getColumn()].setDome(true);
                return new BuildNotification(chosenCell, height, true);
            }
            map[chosenCell.getRow()][chosenCell.getColumn()].setHeight(height);
        }
        return new BuildNotification(chosenCell, height, false);
    }

    /**
     * not used here
     * @param enemy not used
     * @return nothing
     */
    public BuildNotification executeAutoAction(Worker enemy, Position pos, Worker worker) { return null;}

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

    public Position getLastPosition() {
        return lastBuildPosition;
    }

    /**
     * not used here
     * @param noUp true if athena triggers her power
     */
    @Override
    public void setNoUp(Boolean noUp) { }

    /**
     * getter
     * @return  height difference of the move
     */
    public int getDownUp() { return 0; }

}