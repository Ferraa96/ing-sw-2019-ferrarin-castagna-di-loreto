package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.Instruction;

import java.util.ArrayList;
import java.util.List;

/**
 * type of effect: give worker permission to move in other cell
 */
//sistemare Athena che blocca la moveup se attivata

//NB: chi chiama la Move guarda se il myWorker è false ,
//    se lo è allora va in autoproc e muove direttamente tanto availableMoves da solo la mossa forced che deve effettuare l'enemy
public class Move implements Effect{
    Cell[][] map;
    private Boolean myWorker;
    private Boolean searchPeople;
    private Boolean knock;
    private Boolean specificMovetype;
    private Boolean noUp;
    private Worker target;
    private ArrayList<Position> adiacentsCells;
    private Position lastMove_InitialPosition;

    /*    public Move(Cell[][] map) {
        this.map = map;
    }
*/

    public Move(Boolean myWorker, Boolean knock, Boolean searchPeople, Boolean specificMovetype, Boolean noUp) {
        this.myWorker = myWorker;
        this.knock = knock;
        this.searchPeople = searchPeople;
        this.specificMovetype = specificMovetype;
        this.noUp = noUp;
    }

    //8 celle attorno senza cupola
    @Override
    public ArrayList<Position> availableCells(int row, int column, int numMoves) {
        ArrayList<Position> list = new ArrayList<>();
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
                if (map[i][j].getHeight()!=4) {
                    list.add(new Position(i, j));
                    adiacentsCells.add(new Position(i,j));
                }
            }
        }
        return list;
    }

    //celle consentite a seconda del god posseduto e del tipo d mossa che si sta per fare
    public List<Position> availableMoves(Position actualposition) {
        int r,c;
        if (myWorker) {
            for (int i = 0; i < adiacentsCells.size(); i++) {
                r = adiacentsCells.get(i).getRow();
                c = adiacentsCells.get(i).getColumn();
                if (map[r][c].getHeight() - map[actualposition.getRow()][actualposition.getColumn()].getHeight ()>1)
                    adiacentsCells.remove(i);
                if (!searchPeople){
                    //levo le celle occupate
                    if (map[r][c].getWorkerID()!=0)
                        adiacentsCells.remove(i);
                    if (specificMovetype)
                        //nomoveup di prometeo e athena
                        if (noUp) {
                            if (map[r][c].getHeight() - map[actualposition.getRow()][actualposition.getColumn()].getHeight ()== 1)
                                adiacentsCells.remove(i);
                        }
                        else
                            //not before di artemis
                            if (r==lastMove_InitialPosition.getRow() && c==lastMove_InitialPosition.getColumn())
                                adiacentsCells.remove(i);
                }
                else {
                    //considero solo quelle con people per apollo e mino
                    if (map[r][c].getWorkerID() == 0)
                        adiacentsCells.remove(i);
                    if (knock){
                        //controllo il knock di mino
                        r = 2*r - actualposition.getRow();
                        c = 2*c - actualposition.getColumn();
                        if (map[r][c].getWorkerID()!=0 || r<0 || r>4 || c<0 || c>4)
                            adiacentsCells.remove(i);
                    }
                }
            }
        }
        else {
            //seconda azione di apollo e mino forced e unica
            adiacentsCells.clear();
            if (!knock)
                //samebefore di apollo
                adiacentsCells.add(new Position(lastMove_InitialPosition.getRow(),lastMove_InitialPosition.getColumn()));
            else {
                //knock di mino
                r = 2*actualposition.getRow() - lastMove_InitialPosition.getRow();
                c = 2*actualposition.getColumn() - lastMove_InitialPosition.getColumn();
                adiacentsCells.add(new Position(r,c));
            }
        }
        return adiacentsCells;
    }

    //immagino sia first positioning
    public List<Position> firstPositioning() {
        List<Position> list = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(map[i][j].getWorkerID() == 0) {
                    list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    //basicMove non credo serva
    public void basicMove(Position pos, int playerID) {
        Commands commands = new Commands(pos, playerID);
        commands.setInstruction(Instruction.move);
    }

    //setter e getter vari che bisognerà assegnare per dare senso alla Move quando viene usata
    public void setLastMove(Position lastMove_InitialPosition) {
        this.lastMove_InitialPosition = lastMove_InitialPosition;
    }

    public Worker getTarget() {
        return target;
    }

    public void setTarget(Worker target) {
        this.target = target;
    }


}
