package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

/**
 * ask the player if he wants to reload a previous game and return to the server his answer
 */
public class AskForReloadStateInstr implements Serializable, MessageInterface {
    private boolean response;

    public AskForReloadStateInstr() {}

    public AskForReloadStateInstr(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
