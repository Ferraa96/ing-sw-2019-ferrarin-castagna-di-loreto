package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * regroup all different types of effects
 */
public interface Effect {

    ArrayList<Position> availableCells(Position actualposition, int numMoves);
    ArrayList<Position> availableWithGod(Worker target);
    int executeAction(Position chosenCell, Worker worker);
    Boolean getMyWorker();
    void setLastMoveInitialPosition(Position lastMoveInitialPosition);
    void setNoUp(Boolean noUp);
    void executeAutoAction(Worker target);
    public void setLastBuildPosition(Position lastBuildPosition);
}
