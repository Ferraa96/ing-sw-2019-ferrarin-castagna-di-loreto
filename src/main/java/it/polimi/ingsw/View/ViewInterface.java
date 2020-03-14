package it.polimi.ingsw.View;

public interface ViewInterface {

    public void move(int player, int raw, int column);
    public void buildBlock(int raw, int column, int height);
    public void buildDome(int raw, int column, int height);
    public void updateScreen();
}
