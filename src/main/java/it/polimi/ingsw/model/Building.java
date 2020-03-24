package it.polimi.ingsw.model;

public class Building implements OnBoard {
    private Position position;
    private int height;

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public OnBoardType getType() {
        return OnBoardType.building;
    }
}
