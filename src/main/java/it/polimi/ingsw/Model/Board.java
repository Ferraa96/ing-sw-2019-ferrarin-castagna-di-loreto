package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class Board {
    private OnBoard[][] cell;
    private ArrayList<Card> cards;

    public Board() {
        cell = new OnBoard[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                cell[i][j] = new OnBoard() {
                    @Override
                    public OnBoardType getType() {
                        return OnBoardType.nothing;
                    }

                    @Override
                    public Position getPosition() {
                        return null;
                    }
                };
            }
        }
    }

    public OnBoard[][] getBoard() {
        return cell;
    }
}