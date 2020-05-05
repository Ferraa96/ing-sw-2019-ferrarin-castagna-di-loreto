package it.polimi.ingsw.controller.Server;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.model.ModelInterface;

/**
 * handle the messages from the client
 */
public class ModelUpdater implements MessageVisitor {
    private final ModelInterface turn;

    public ModelUpdater(ModelInterface turn) {
        this.turn = turn;
    }

    @Override
    public void visit(AskForReloadStateInstr msg) {
        turn.loadState(msg.isResponse());
    }

    @Override
    public void visit(BuildInstr msg) {

    }

    @Override
    public void visit(ChooseCardInstr msg) {
        turn.setCards(msg.getChosenCard());
    }

    @Override
    public void visit(ChooseCardListInstr msg) {
        turn.setInitialCards(msg.getChosenCards());
    }

    @Override
    public void visit(ChoosePosInstr msg) {
        turn.apply(msg.getChosenPos());
    }

    @Override
    public void visit(ChooseWorkerInstr msg) {
        turn.selectCorrectWorker(msg.getChosenWorker());
    }

    @Override
    public void visit(FirstPositioningInstr msg) {
        turn.setWorkersPosition(msg.getPositions());
    }

    @Override
    public void visit(MoveInstr msg) {

    }

    @Override
    public void visit(SetNameInstr msg) {
        turn.setPlayerName(msg.getName());
    }

    @Override
    public void visit(SetPowerInstr msg) {
        turn.providePosition(msg.isPower());
    }

    @Override
    public void visit(HandleEndGameInstr msg) {
        turn.handleDisconnection(msg.getClientID());
    }

    @Override
    public void visit(LoadGameInstr msg) {

    }
}