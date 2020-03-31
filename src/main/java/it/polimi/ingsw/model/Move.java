package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * type of effect: give worker permission to move in other cell
 */

public class Move implements Effect{
    Cell[][] map;
    private Boolean myWorker;
    private Boolean searchPeople;
    private Boolean knock;
    private Boolean specificMovetype;
    private Boolean noUp;                           //usata da Athena all'inizio dello switch
    private Position lastMoveInitialPosition;

    public Move(Cell[][] map, Boolean myWorker, Boolean knock, Boolean searchPeople, Boolean specificMovetype, Boolean noUp) {
        this.myWorker = myWorker;
        this.knock = knock;
        this.searchPeople = searchPeople;
        this.specificMovetype = specificMovetype;
        this.noUp = noUp;
        this.map = map;
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
                if ( map[i][j].getHeight()!=4 && map[i][j].getHeight()-map[row][column].getHeight()<=1) {
                    if (!(i==row && j==column))
                       list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    //celle consentite a seconda del god posseduto e del tipo di mossa che si sta per fare
    @Override
    public ArrayList<Position> availableWithGod(Worker target) {
        Position actualPosition = new Position(target.getPosition().getRow(),target.getPosition().getColumn());
        int r,c;
        ArrayList<Position> possibleCells= new ArrayList<>(availableCells(actualPosition,1));
        for (int i = 0; i < possibleCells.size(); i++) {
                r = possibleCells.get(i).getRow();
                c = possibleCells.get(i).getColumn();
                if (!searchPeople){
                    //levo le celle occupate
                    if (map[r][c].getWorkerID()!=0) {
                        possibleCells.remove(possibleCells.get(i));
                        i--;
                    }
                    else if (specificMovetype) {
                        //nomoveup di prometeo e athena
                        if (noUp) {
                            if (map[r][c].getHeight() - map[actualPosition.getRow()][actualPosition.getColumn()].getHeight() >= 1) {
                                possibleCells.remove(possibleCells.get(i));
                                i--;
                            }
                        } else
                            //not before di artemis
                            if (r == lastMoveInitialPosition.getRow() && c == lastMoveInitialPosition.getColumn()) {
                                possibleCells.remove(possibleCells.get(i));
                                i--;
                            }
                    }
                }
                else {
                    //considero solo quelle con people non mie per apollo e mino
                    if (map[r][c].getWorkerID() == 0 || map[r][c].getWorkerID()/10==target.getWorkerID()/10) {
                        possibleCells.remove(possibleCells.get(i));
                        i--;
                    }
                    else if (knock){
                        //controllo il knock di mino
                        r = 2*r - actualPosition.getRow();
                        c = 2*c - actualPosition.getColumn();
                        if (map[r][c].getWorkerID()!=0 || r<0 || r>4 || c<0 || c>4) {
                            possibleCells.remove(possibleCells.get(i));
                            i--;
                        }
                    }
                }
            }
        return possibleCells;
    }

    //effettiva mossa del worker
    @Override
    public int executeAction(Position chosenCell, Worker worker) {
        int downUp=0;
        //if (availableWithGod(worker).contains(chosenCell)) {
            downUp=map[chosenCell.getRow()][chosenCell.getColumn()].getHeight()-map[worker.getPosition().getRow()][worker.getPosition().getColumn()].getHeight();
            map[worker.getPosition().getRow()][worker.getPosition().getColumn()].setWorkerID(0);
            map[chosenCell.getRow()][chosenCell.getColumn()].setWorkerID(worker.getWorkerID());
            worker.getPosition().setRow(chosenCell.getRow());
            worker.getPosition().setColumn(chosenCell.getColumn());
        //}
        //else
        //    System.out.println("Move not available");

        return downUp;
    }

    //mossa obbligata del nemico
    @Override
    public void executeAutoAction(Worker enemy) {
        int r,c;
        if (!knock) {
            enemy.setPosition(lastMoveInitialPosition);
            map[enemy.getPosition().getRow()][enemy.getPosition().getColumn()].setWorkerID(enemy.getWorkerID());
        }
        else {
            r = 2*enemy.getPosition().getRow() - lastMoveInitialPosition.getRow();
            c = 2*enemy.getPosition().getColumn() - lastMoveInitialPosition.getColumn();
            enemy.getPosition().setRow(r);
            enemy.getPosition().setColumn(c);
            map[r][c].setWorkerID(enemy.getWorkerID());
        }
    }

    //setter e getter vari
    @Override
    public void setLastMoveInitialPosition(Position lastMoveInitialPosition) {
        this.lastMoveInitialPosition = lastMoveInitialPosition;
    }
    @Override
    public Boolean getMyWorker() {
        return myWorker;
    }
    @Override
    public void setNoUp( Boolean noUp) {
        this.noUp = noUp;
    }

    public void setLastBuildPosition(Position lastBuildPosition) {  }

}