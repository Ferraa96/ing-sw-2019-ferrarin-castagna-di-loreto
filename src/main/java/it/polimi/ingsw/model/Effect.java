package it.polimi.ingsw.model;

import java.util.List;

public interface Effect {

    public List<Position> available(int row, int column, int numMoves);
}
