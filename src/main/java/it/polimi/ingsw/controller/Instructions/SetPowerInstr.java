package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

/**
 * allows the player activate the power of his card
 */
public class SetPowerInstr implements Serializable, MessageInterface {
    private boolean isPower;

    public SetPowerInstr() {}

    public SetPowerInstr(boolean isPower) {
        this.isPower = isPower;
    }

    public boolean isPower() {
        return isPower;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
