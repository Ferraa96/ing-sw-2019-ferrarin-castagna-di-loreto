package it.polimi.ingsw.controller.Client;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.view.ViewInterface;

/**
 * handle the messages from the server
 */
public class ClientUpdater implements MessageVisitor {
    private final ViewInterface view;

    /**
     * adapter server-client
     * @param view the view instance
     */
    public ClientUpdater(ViewInterface view) {
        this.view = view;
    }

    @Override
    public void visit(AskForReloadStateInstr msg) {
        view.askForReloadState();
    }

    @Override
    public void visit(BuildInstr msg) {
        if(!msg.isDome()) {
            view.buildBlock(msg.getPos(), msg.getHeight());
        } else {
            view.buildDome(msg.getPos(), msg.getHeight());
        }
        view.updateScreen();
    }

    @Override
    public void visit(ChooseCardInstr msg) {
        view.chooseCard(msg.getAvailableCards());
    }

    @Override
    public void visit(ChooseCardListInstr msg) {
        view.chooseCardList(msg.getNumPlayers());
    }

    @Override
    public void visit(ChoosePosInstr msg) {
        view.choosePosition(msg.getAvailablePositions());
    }

    @Override
    public void visit(ChooseWorkerInstr msg) {
        view.chooseWorker(msg.getAvailableWorkers());
    }

    @Override
    public void visit(FirstPositioningInstr msg) {
        view.firstPositioning(msg.getPositions(), msg.getGodName(), msg.isMyTurn());
    }

    @Override
    public void visit(MoveInstr msg) {
        view.move(msg.getMovements());
        view.updateScreen();
    }

    @Override
    public void visit(SetNameInstr msg) {
        view.setName(msg.getAllNames());
    }

    @Override
    public void visit(SetPowerInstr msg) {
        view.choosePower();
    }

    @Override
    public void visit(HandleEndGameInstr msg) {
        view.handleEndGame(msg.getMessage());
    }

    @Override
    public void visit(LoadGameInstr msg) {
        view.reloadState(msg.getMap(), msg.getGodNames());
        view.updateScreen();
    }
}
