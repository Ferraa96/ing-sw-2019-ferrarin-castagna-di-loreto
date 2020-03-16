package it.polimi.ingsw.Controller;

import java.io.Serializable;

public class Commands implements Serializable {
    private int raw;
    private int column;
    private int height;
    private int player;
    private int chosenCard;
    private int effect;         // 0 => move, 1 => buildBlock, 2 => buildDome, 3 => myTurn, 4 => setPlayerID, 5 => endGame

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setChosenCard(int chosenCard) {
        this.chosenCard = chosenCard;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}