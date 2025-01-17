package it.polimi.ingsw.model.IO;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.Game.Cell;
import it.polimi.ingsw.model.Game.PlayerInfo;
import it.polimi.ingsw.model.Game.Position;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class SaveState implements Serializable {
    @SerializedName("players")
    private List<PlayerInfo> players;
    @SerializedName("eliminatedPlayers")
    private List<Integer> eliminatedPlayers;
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
    @SerializedName("noClimb")
    private boolean noClimb;        //true if Athena's power is active

    private Position previousPos;   //used by Artemis, Demeter

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerInfo> players) {
        this.players = players;
    }

    public List<Integer> getEliminatedPlayers() {
        return eliminatedPlayers;
    }

    public void setEliminatedPlayers(List<Integer> eliminatedPlayers) {
        this.eliminatedPlayers = eliminatedPlayers;
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

    public void setNoClimb(boolean noClimb) {
        this.noClimb = noClimb;
    }

    public boolean isNoClimb() {
        return noClimb;
    }

    public Position getPreviousPos() {
        return previousPos;
    }

    public void setPreviousPos(Position previousPos) {
        this.previousPos = previousPos;
    }
}
