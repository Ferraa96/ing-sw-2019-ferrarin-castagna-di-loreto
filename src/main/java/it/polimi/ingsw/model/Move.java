package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.Instruction;

import java.util.ArrayList;
import java.util.List;

/**
 * type of effect: give worker permission to move in other cell
 */

public class Move implements Effect{
    private Cell[][] map;
    private boolean searchPeople;
    private boolean knock;
    private boolean specificMovetype;
    private boolean noUp = false;
    private boolean notBefore;
    private Position lastMoveInitialPosition;
    private List<Position> possibleCells;
    private Commands moveMessage = new Commands();
    private int downUp;

    public Move(Cell[][] map, boolean knock, boolean searchPeople, boolean specificMovetype, boolean notBefore) {
        this.knock = knock;
        this.searchPeople = searchPeople;
        this.specificMovetype = specificMovetype;
        this.notBefore = notBefore;
        this.map = map;
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
        if(left < 0) {
            left = 0;
        }
        else if(right > 5) {
            right = 5;
        }
        if(up < 0) {
            up = 0;
        }
        else if(down > 5) {
            down = 5;
        }
        for(int i = up; i < down; i++) {
            for(int j = left; j < right; j++) {
                if (map[i][j].getHeight() != 4 && map[i][j].getHeight()-map[row][column].getHeight()<=1) {
                    if (!(i == row && j == column)) {
                        list.add(new Position(i, j));
                    }
                }
            }
        }
        return list;
    }

    /**
     * remove correct positions if you can't move up
     * @param target interested worker
     * @param r row index
     * @param c column index
     * @param curr possibleCells current cell
     * @return index of cell in possibleCells
     */
    private int noMoveUp(Worker target, int r, int c, int curr) {
        if (noUp)
            if (map[r][c].getHeight() - map[target.getPosition().getRow()][target.getPosition().getColumn()].getHeight() == 1) {
                possibleCells.remove(possibleCells.get(curr));
                curr--;
            }
        return curr;
    }

    /**
     * remove correct positions considering if you are searching for people or not
     * @param target interested worker
     * @param r row index
     * @param c column index
     * @param curr possibleCells current cell
     * @return index of cell in possibleCells
     */
    private int people(Worker target, int r, int c, int curr) {
        if (!searchPeople) {
            if (map[r][c].getWorkerID() != -1) {
                possibleCells.remove(possibleCells.get(curr));
                curr--;
            }
        }
        else if (map[r][c].getWorkerID() == -1 || map[r][c].getWorkerID() / 2 == target.getWorkerID() / 2) {
            possibleCells.remove(possibleCells.get(curr));
            curr--;
        }
        return curr;
    }

    /**
     * remove correct positions considering mino's knock
     * @param target interested worker
     * @param r row index
     * @param c column index
     * @param curr possibleCells current cell
     * @return index of cell in possibleCells
     */
    private int knockable(Worker target, int r, int c, int curr) {
        if (knock){
            //controllo il knock di mino
            r = 2*r - target.getPosition().getRow();
            c = 2*c - target.getPosition().getColumn();
            if (r<0 || r>4 || c<0 || c>4) {
                possibleCells.remove(possibleCells.get(curr));
                curr--;
            }
            else if (map[r][c].getWorkerID() != -1) {
                possibleCells.remove(possibleCells.get(curr));
                curr--;
            }
        }
        return curr;
    }

    /**
     * remove correct positions considering specific move condition
     * @param target interested worker
     * @param r row index
     * @param c column index
     * @param curr possibleCells current cell
     * @return index of cell in possibleCells
     */
    private int specific(Worker target, int r, int c, int curr) {
        if (specificMovetype) {
            //nomoveup di prometheus
            if (!notBefore) {
                if (map[r][c].getHeight() - map[target.getPosition().getRow()][target.getPosition().getColumn()].getHeight() >= 1) {
                    possibleCells.remove(possibleCells.get(curr));
                    curr--;
                }
            } else
                //not before di artemis
                if (r == lastMoveInitialPosition.getRow() && c == lastMoveInitialPosition.getColumn()) {
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
        Position actualPosition = new Position(target.getPosition().getRow(), target.getPosition().getColumn());
        int r,c;
        possibleCells= new ArrayList<>(availableCells(actualPosition));
        for (int i = 0; i < possibleCells.size(); i++) {
            r = possibleCells.get(i).getRow();
            c = possibleCells.get(i).getColumn();
            int j = i;
            i = noMoveUp(target, r, c, i);
            if (i == j) {
                i = people(target, r, c, i);
                if (i == j) {
                    i = knockable(target, r, c, i);
                    i = specific(target, r, c, i);
                }
            }
        }
        return possibleCells;
    }

    /**
     * do the action and update the map
     * @param chosenCell cell selected
     * @param worker target of action
     * @return message you have to send to view
     */
    @Override
    public Commands executeAction(Position chosenCell, Worker worker) {
        downUp=map[chosenCell.getRow()][chosenCell.getColumn()].getHeight()-map[worker.getPosition().getRow()][worker.getPosition().getColumn()].getHeight();
        map[worker.getPosition().getRow()][worker.getPosition().getColumn()].setWorkerID(-1);
        map[chosenCell.getRow()][chosenCell.getColumn()].setWorkerID(worker.getWorkerID());
        worker.setPosition( new Position(chosenCell.getRow(),chosenCell.getColumn()));
        moveMessage.setInstruction(Instruction.move);
        moveMessage.getMovement().put(worker.getWorkerID(),chosenCell);
        return moveMessage;
    }

    /**
     * auto move for forced targets
     * @param enemy worker forced by mino/apollo
     * @return message you have to send to view
     */
    @Override
    public Commands executeAutoAction(Worker enemy) {
        int r, c;
        if (!knock) {
            enemy.setPosition(lastMoveInitialPosition);
            map[enemy.getPosition().getRow()][enemy.getPosition().getColumn()].setWorkerID(enemy.getWorkerID());
            moveMessage.getMovement().put(enemy.getWorkerID(),lastMoveInitialPosition);
        }
        else {
            r = 2 * enemy.getPosition().getRow() - lastMoveInitialPosition.getRow();
            c = 2 * enemy.getPosition().getColumn() - lastMoveInitialPosition.getColumn();
            enemy.setPosition(new Position(r, c));
            map[r][c].setWorkerID(enemy.getWorkerID());
            moveMessage.getMovement().put(enemy.getWorkerID(),enemy.getPosition());
        }
        return moveMessage;
    }

    /**
     * set last move
     * @param lastMoveInitialPosition initial cell of last move
     */
    @Override
    public void setLastMoveInitialPosition(Position lastMoveInitialPosition) {
        this.lastMoveInitialPosition = lastMoveInitialPosition;
    }

    /**
     * used for athena
     * @param noUp true if athena triggers her power
     */
    @Override
    public void setNoUp( Boolean noUp) {
        this.noUp = noUp;
    }

    /**
     * getter
     * @return height difference of the move
     */
    @Override
    public int getDownUp() {
        return this.downUp;
    }

    /**
     * not used here
     * @param lastBuildPosition cell of last build
     */
    public void setLastBuildPosition(Position lastBuildPosition) {  }

}