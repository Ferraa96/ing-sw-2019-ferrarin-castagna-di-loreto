package it.polimi.ingsw.Model;

public class Worker implements OnBoard {
    private int workerID;
    private Player owner;
    private Position position;

    @Override
    public OnBoardType getType() {
        return OnBoardType.worker;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
