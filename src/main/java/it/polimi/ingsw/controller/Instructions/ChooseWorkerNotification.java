package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Game.Position;

import java.io.Serializable;
import java.util.List;

/**
 * lets the player choose which worker he wants to play with
 */
public class ChooseWorkerNotification implements Serializable, NotificationInterface {
    private int clientID;
    private Position chosenWorker;
    private List<Position> availableWorkers;

    public ChooseWorkerNotification(List<Position> availableWorkers, int clientID) {
        this.availableWorkers = availableWorkers;
        this.clientID = clientID;
    }

    public ChooseWorkerNotification(Position chosenWorker) {
        this.chosenWorker = chosenWorker;
    }

    public Position getChosenWorker() {
        return chosenWorker;
    }

    public List<Position> getAvailableWorkers() {
        return availableWorkers;
    }

    @Override
    public void accept(NotificationVisitor v) {
        v.visit(this);
    }

    @Override
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public int getClientID() {
        return clientID;
    }
}
