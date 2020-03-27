package it.polimi.ingsw.model;

import java.util.List;

public class Build implements Effect {
    public List<Position> available(int row, int column) {
        return null;
    }

    @Override
    public List<Position> available(int row, int column, int numMoves) {
        return null;
    }
}