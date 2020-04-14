package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * store the status of the cell on the board
 */
public class Cell implements Serializable {
    private int workerID = -1;
    private int height = 0;
    private boolean isDome = false;

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

    public boolean isDome() {
        return isDome;
    }

    public void setDome(boolean dome) {
        isDome = dome;
    }
}