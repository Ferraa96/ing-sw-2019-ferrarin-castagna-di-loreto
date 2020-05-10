package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;
import java.util.List;

/**
 * allows the player to set the name
 */
public class SetNameInstr implements Serializable, MessageInterface {
    private List<String> allNames;
    private String name;

    public SetNameInstr(List<String> allNames) {
        this.allNames = allNames;
    }

    public SetNameInstr(String name) {
        this.name = name;
    }

    public List<String> getAllNames() {
        return allNames;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
