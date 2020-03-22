package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Position;

import java.util.List;

public interface ViewInterface {

    void setPlayerID(int playerID);
    void move(int player, Position position);
    void buildBlock(Position position, int height);
    void buildDome(Position position, int height);
    void updateScreen();
    void chooseCard(List<Integer> cardList);
    void chooseCardList(int num);
    void firstPositioning(List<Position> availablePos);
}