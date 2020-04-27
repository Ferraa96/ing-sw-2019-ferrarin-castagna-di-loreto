package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;
import java.util.List;

public class SetNameInstr implements Serializable {
    private int playerID;
    private List<String> allNames;
    private String name;

    public SetNameInstr(int playerID, List<String> allNames) {
        this.playerID = playerID;
        this.allNames = allNames;
    }

    public SetNameInstr(String name) {
        this.name = name;
    }

    public int getPlayerID() {
        return playerID;
    }

    public List<String> getAllNames() {
        return allNames;
    }

    public String getName() {
        return name;
    }
}
