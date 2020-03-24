package it.polimi.ingsw.model;

public class Worker implements OnBoard {
    private int workerID;
    private Position position;

    @Override
    public OnBoardType getType() {
        return OnBoardType.worker;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
