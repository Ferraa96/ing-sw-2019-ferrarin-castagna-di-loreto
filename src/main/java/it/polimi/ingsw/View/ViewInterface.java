package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.Commands;

public interface ViewInterface {

    void setPlayerID(int playerID);
    void move(int player, int raw, int column);
    void buildBlock(int raw, int column, int height);
    void buildDome(int raw, int column, int height);
    void updateScreen();
    void updateServer(Commands commands);
}
