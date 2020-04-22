package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

public class SetPowerInstr implements Serializable {
    private boolean isPower;

    public SetPowerInstr() {}

    public SetPowerInstr(boolean isPower) {
        this.isPower = isPower;
    }

    public boolean isPower() {
        return isPower;
    }
}
