package it.polimi.ingsw.model;

/**
 * store worker's informations
 */
public class Worker {
    private int workerID;
    private Position position;

    public int getWorkerID() {
        return workerID;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
