package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * regroup all different types of effects
 */
public interface Effect {

    ArrayList<Position> availableCells(int row, int column, int numMoves);
}
