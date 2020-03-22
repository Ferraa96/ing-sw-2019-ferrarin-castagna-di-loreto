package it.polimi.ingsw.Model;

public class Building implements OnBoard {
    private Position position;
    private int height;

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
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
