package it.polimi.ingsw.model;

import java.util.List;

public interface ModelInterface {

    void choosePosition(List<Position> list);
    void setCards(int chosen);
    void setInitialCards(List<Integer> cards);
}
