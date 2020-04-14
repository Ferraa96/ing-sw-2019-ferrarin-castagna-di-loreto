package it.polimi.ingsw.model;

import com.google.gson.annotations.SerializedName;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class SaveState implements Serializable {
    @SerializedName("players")
    private List<PlayerInfo> players;
    @SerializedName("gameMap")
    private Cell[][] gameMap;
    @SerializedName("actualPlayer")
    private int actualPlayer;
    @SerializedName("chosenWorker")
    private int chosenWorker;
    @SerializedName("actualEffect")
    private int actualEffect;
    @SerializedName("godPower")
    private boolean godPower;

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerInfo> players) {
        this.players = players;
    }

    /**
     * generate the name of the save file using the names of the players
     * @param names the list of all the names of the players
     * @return the name that will be used for the file
     */
    public String generateName(List<String> names) {
        Collections.sort(names);
        StringBuilder nameBuilder = new StringBuilder();
        for(String curr: names) {
            nameBuilder.append(curr);
        }
        nameBuilder.append("State");
        return nameBuilder.toString();
    }

    public int getActualPlayer() {
        return actualPlayer;
    }

    public void setActualPlayer(int actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    public int getActualEffect() {
        return actualEffect;
    }

    public void setActualEffect(int actualEffect) {
        this.actualEffect = actualEffect;
    }

    public int getChosenWorker() {
        return chosenWorker;
    }

    public void setChosenWorker(int chosenWorker) {
        this.chosenWorker = chosenWorker;
    }

    public void setGameMap(Cell[][] gameMap) {
        this.gameMap = gameMap;
    }

    public Cell[][] getGameMap() {
        return gameMap;
    }

    public void setGodPower(boolean godPower) {
        this.godPower = godPower;
    }

    public boolean isGodPower() {
        return godPower;
    }
}
