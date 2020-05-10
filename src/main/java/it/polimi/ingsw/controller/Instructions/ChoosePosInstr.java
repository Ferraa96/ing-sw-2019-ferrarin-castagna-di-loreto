package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;
import java.util.List;

/**
 * lets the player choose the position of the move / build
 */
public class ChoosePosInstr implements Serializable, MessageInterface {
    private List<Position> availablePositions;
    private Position chosenPos;

    public ChoosePosInstr(Position chosenPos) {
        this.chosenPos = chosenPos;
    }

    public ChoosePosInstr(List<Position> availablePositions) {
        this.availablePositions = availablePositions;
    }

    public List<Position> getAvailablePositions() {
        return availablePositions;
    }

    public Position getChosenPos() {
        return chosenPos;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
