package it.polimi.ingsw.view.GUI;

public class Square {

    private int height;
    private int worker;
    private boolean isDome;

    public Square(){
        this.height = -1;
        this.worker = -1;
        this.isDome = false;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setWorker(int worker) {
        this.worker = worker;
    }

    public int getWorker() { return worker; }

    public boolean isDome() {
        return isDome;
    }

    public void setDome(boolean dome) {
        isDome = dome;
    }
}
