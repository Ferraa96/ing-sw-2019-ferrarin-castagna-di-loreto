package it.polimi.ingsw.controller.Server;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.model.Game.ModelInterface;

/**
 * handle the messages from the client
 */
public class ModelUpdater implements NotificationVisitor {
    private final ModelInterface turn;

    public ModelUpdater(ModelInterface turn) {
        this.turn = turn;
    }

    @Override
    public void visit(AskForReloadStateNotification msg) {
        turn.loadState(msg.isResponse());
    }

    @Override
    public void visit(BuildNotification msg) {

    }

    @Override
    public void visit(ChooseCardNotification msg) {
        turn.setCards(msg.getChosenCard());
    }

    @Override
    public void visit(ChooseCardListNotification msg) {
        turn.setInitialCards(msg.getChosenCards());
    }

    @Override
    public void visit(ChoosePosNotification msg) {
        turn.apply(msg.getChosenPos());
    }

    @Override
    public void visit(ChooseWorkerNotification msg) {
        turn.selectCorrectWorker(msg.getChosenWorker());
    }

    @Override
    public void visit(FirstPositioningNotification msg) {
        turn.setWorkersPosition(msg.getPositions());
    }

    @Override
    public void visit(MoveNotification msg) {

    }

    @Override
    public void visit(SetNameNotification msg) {
        turn.setPlayerName(msg.getName(), msg.getClientID());
    }

    @Override
    public void visit(SetPowerNotification msg) {
        turn.providePosition(msg.isPower());
    }

    @Override
    public void visit(DisconnectionNotification msg) {
        turn.handleDisconnection(msg.getClientID());
    }

    @Override
    public void visit(LoadGameNotification msg) {

    }

    @Override
    public void visit(EliminationNotification msg) {

    }

    @Override
    public void visit(WinNotification msg) {

    }
}