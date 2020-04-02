package it.polimi.ingsw.model;

import com.google.gson.annotations.SerializedName;

public class SaveState {
    @SerializedName("map")
    private Cell[][] map;
    @SerializedName("playerTurn")
    private int playerID;
    @SerializedName("effectNum")
    private int effectNum;
    @SerializedName("workerID")
    private int workerID;
    @SerializedName("effectType")
    private boolean effectType;

    public Cell[][] getMap() {
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getEffectNum() {
        return effectNum;
    }

    public void setEffectNum(int effectNum) {
        this.effectNum = effectNum;
    }

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

    public boolean isEffectType() {
        return effectType;
    }

    public void setEffectType(boolean effectType) {
        this.effectType = effectType;
    }
}
