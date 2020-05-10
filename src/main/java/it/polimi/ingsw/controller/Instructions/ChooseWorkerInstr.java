package it.polimi.ingsw.controller.Instructions;

import it.polimi.ingsw.model.Position;

import java.io.Serializable;
import java.util.List;

/**
 * lets the player choose which worker he wants to play with
 */
public class ChooseWorkerInstr implements Serializable, MessageInterface {
    private Position chosenWorker;
    private List<Position> availableWorkers;

    public ChooseWorkerInstr(List<Position> availableWorkers) {
        this.availableWorkers = availableWorkers;
    }

    public ChooseWorkerInstr(Position chosenWorker) {
        this.chosenWorker = chosenWorker;
    }

    public Position getChosenWorker() {
        return chosenWorker;
    }

    public List<Position> getAvailableWorkers() {
        return availableWorkers;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
