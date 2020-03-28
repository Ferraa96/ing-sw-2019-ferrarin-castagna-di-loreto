package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * type of effect: give worker permission to build a block
 */
public class Build implements Effect {
    private Boolean freeBlock;
    private Boolean specificPosition;
    private Boolean sameBefore;

    public Build(Boolean freeBlock, Boolean specificPosition, Boolean sameBefore) {
        this.freeBlock = freeBlock;
        this.specificPosition = specificPosition;
        this.sameBefore = sameBefore;
    }

    public ArrayList<Position> availableCells(int row, int column) {
        return null;
    }

    @Override
    public ArrayList<Position> availableCells(int row, int column, int numMoves) {
        return null;
    }
}