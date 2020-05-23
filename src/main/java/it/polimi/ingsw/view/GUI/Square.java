package it.polimi.ingsw.view.GUI;

public class Square {

    private int height;
    private boolean worker;
    private String godname;

    public Square(){
        this.height = 0;
        this.worker = false;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setGodname(String godname) {
        this.godname = godname;
    }

    public int getHeight() {
        return height;
    }

    public String getGodname() {
        return godname;
    }

    public void setWorker(boolean worker) {
        this.worker = worker;
    }

    public boolean isWorker() {
        return worker;
    }
}
