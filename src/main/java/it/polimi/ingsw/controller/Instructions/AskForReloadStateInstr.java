package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

public class AskForReloadStateInstr implements Serializable, MessageInterface {
    private boolean response = false;

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
