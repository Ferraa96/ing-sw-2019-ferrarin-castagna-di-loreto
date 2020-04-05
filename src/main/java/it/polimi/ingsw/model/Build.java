package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.Instruction;

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
                if (map[i][j].getHeight()!=4 && map[i][j].getWorkerID() == -1) {
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
    public Commands executeAction(Position chosenCell, Worker worker) {
        Commands buildMessage = new Commands();
        if (!nextBlock) {
            if (!specific) {
                buildMessage.setInstruction(Instruction.buildDome);
                buildMessage.setPosition(chosenCell);
                buildMessage.setHeight(map[chosenCell.getRow()][chosenCell.getColumn()].getHeight());
                map[chosenCell.getRow()][chosenCell.getColumn()].setHeight(4);
            }
            else {
                map[chosenCell.getRow()][chosenCell.getColumn()].setHeight(map[chosenCell.getRow()][chosenCell.getColumn()].getHeight() + 2);
                buildMessage.setInstruction(Instruction.buildBlock);
                buildMessage.setPosition(chosenCell);
                buildMessage.setHeight(map[chosenCell.getRow()][chosenCell.getColumn()].getHeight());
            }
        }
        else {
            map[chosenCell.getRow()][chosenCell.getColumn()].setHeight(map[chosenCell.getRow()][chosenCell.getColumn()].getHeight() + 1);
            buildMessage.setInstruction(Instruction.buildBlock);
            buildMessage.setPosition(chosenCell);
            buildMessage.setHeight(map[chosenCell.getRow()][chosenCell.getColumn()].getHeight());
        }
        return buildMessage;
    }

    /**
     * not used here
     * @param enemy not used
     * @return nothing
     */
    public Commands executeAutoAction(Worker enemy) { return null; }

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

    /**
     * getter
     * @return  height difference of the move
     */
    public int getDownUp() { return 0; }

}