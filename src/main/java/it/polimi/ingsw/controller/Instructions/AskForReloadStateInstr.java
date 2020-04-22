package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

public class AskForReloadStateInstr implements Serializable {
    private boolean response = false;

    public AskForReloadStateInstr() {}

    public AskForReloadStateInstr(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }
}
