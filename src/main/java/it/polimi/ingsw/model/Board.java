package it.polimi.ingsw.model;

public class Board {
    private OnBoard[][] cell;

    public Board() {
        cell = new OnBoard[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                cell[i][j] = new OnBoard() {
                    @Override
                    public OnBoardType getType() {
                        return OnBoardType.nothing;
                    }
                };
            }
        }
    }

    public OnBoard[][] getBoard() {
        return cell;
    }
}