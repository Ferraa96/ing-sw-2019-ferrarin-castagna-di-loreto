package it.polimi.ingsw.model;

/**
 * store the status of the cell on the board
 */
public class Cell {

    private int workerID = -1;
    private int height = 0;

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

    public int getHeight() { return height; }

    public void setHeight(int height) {
        this.height = height;
    }

}
