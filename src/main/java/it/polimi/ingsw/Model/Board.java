package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class Board {
    Cell[][] cell;
    ArrayList<Card> cards;

    public Board() {
        cell = new Cell[5][5];
    }
}
