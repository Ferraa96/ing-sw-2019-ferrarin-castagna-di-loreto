package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * collect all the info of the player that will be stored in the save file
 */
public class PlayerInfo implements Serializable {
    private String playerName;
    private int chosenCard;
    private List<Position> workerPos;

    public PlayerInfo(String playerName) {
        this.playerName = playerName;
        workerPos = new ArrayList<>();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getChosenCard() {
        return chosenCard;
    }

    public void setChosenCard(int chosenCard) {
        this.chosenCard = chosenCard;
    }

    public List<Position> getWorkerPos() {
        return workerPos;
    }

    public void setWorkerPos(List<Position> workerPos) {
        this.workerPos = workerPos;
    }
}
