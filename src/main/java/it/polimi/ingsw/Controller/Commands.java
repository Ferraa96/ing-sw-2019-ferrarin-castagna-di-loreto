package it.polimi.ingsw.Controller;

import java.io.Serializable;

public class Commands implements Serializable {
    private int raw;
    private int column;
    private int player;
    private int chosenCard;
    boolean move = false;
    boolean buildBlock = false;
    boolean buildDome = false;
    boolean myTurn = false;

    public Commands() {}

    public Commands(int raw, int column, int player) {
        this.raw = raw;
        this.column = column;
        this.player = player;
    }

    public int getRaw() {
        return raw;
    }

    public void setRaw(int raw) {
        this.raw = raw;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public boolean isBuildBlock() {
        return buildBlock;
    }

    public void setBuildBlock(boolean buildBlock) {
        this.buildBlock = buildBlock;
    }

    public boolean isBuildDome() {
        return buildDome;
    }

    public void setBuildDome(boolean buildDome) {
        this.buildDome = buildDome;
    }

    public boolean isChooseCard() {
        return myTurn;
    }

    public void setChosenCard(int chosenCard) {
        this.chosenCard = chosenCard;
    }
}