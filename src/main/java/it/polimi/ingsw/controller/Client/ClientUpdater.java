package it.polimi.ingsw.controller.Client;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.view.ViewInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * handle the messages from the server
 */
public class ClientUpdater implements NotificationVisitor {
    private final ViewInterface view;
    private int playerID;
    private List<String> allNames;

    /**
     * adapter server-client
     * @param view the view instance
     */
    public ClientUpdater(ViewInterface view) {
        this.view = view;
        allNames = new ArrayList<>();
    }

    @Override
    public void visit(AskForReloadStateNotification msg) {
        if(msg.getClientID() == playerID) {
            view.askForReloadState();
        } else {
            view.notificationForOtherClient("Player " + msg.getClientID() + " is choosing if he wants to reload the game");
        }
    }

    @Override
    public void visit(BuildNotification msg) {
        if(!msg.isDome()) {
            view.buildBlock(msg.getPos(), msg.getHeight());
        } else {
            view.buildDome(msg.getPos(), msg.getHeight());
        }
        view.updateScreen();
    }

    @Override
    public void visit(ChooseCardNotification msg) {
        if(msg.getClientID() == playerID) {
            view.chooseCard(msg.getAvailableCards());
        } else {
            view.notificationForOtherClient(allNames.get(msg.getClientID()) + " is choosing the card");
        }
    }

    @Override
    public void visit(ChooseCardListNotification msg) {
        allNames = msg.getUserNames();
        if(msg.getClientID() == playerID) {
            view.chooseCardList(msg.getNumPlayers());
        } else {
            view.notificationForOtherClient(allNames.get(msg.getClientID()) + " is choosing all the cards");
        }
    }

    @Override
    public void visit(ChoosePosNotification msg) {
        if(msg.getClientID() == playerID) {
            view.choosePosition(msg.getAvailablePositions());
        } else {
            view.notificationForOtherClient(allNames.get(msg.getClientID()) + " is choosing the position");
        }
    }

    @Override
    public void visit(ChooseWorkerNotification msg) {
        if(msg.getClientID() == playerID) {
            view.chooseWorker(msg.getAvailableWorkers());
        } else {
            view.notificationForOtherClient(allNames.get(msg.getClientID()) + " is choosing the worker");
        }
    }

    @Override
    public void visit(FirstPositioningNotification msg) {
        if(msg.getClientID() == playerID) {
            if(!msg.isLoadPos()) {
                view.firstPositioning(msg.getPositions(), msg.getGodNames(), allNames, msg.getClientID(), true);
            }
        } else {
            if(msg.isLoadPos()) {
                view.firstPositioning(msg.getPositions(), msg.getGodNames(), allNames, msg.getClientID(), false);
            } else {
                view.notificationForOtherClient(allNames.get(msg.getClientID()) + " is choosing where to position his workers");
            }
        }
    }

    @Override
    public void visit(MoveNotification msg) {
        view.move(msg.getMovements());
        view.updateScreen();
    }

    @Override
    public void visit(SetNameNotification msg) {
        playerID = msg.getClientID();
        if(!msg.isOk()) {
            view.setName();
        }
    }

    @Override
    public void visit(SetPowerNotification msg) {
        if(msg.getClientID() == playerID) {
            view.choosePower();
        } else {
            view.notificationForOtherClient(allNames.get(msg.getClientID()) + " is choosing the power");
        }
    }

    @Override
    public void visit(DisconnectionNotification msg) {
        view.handleDisconnection(msg.getMessage());
    }

    @Override
    public void visit(LoadGameNotification msg) {
        allNames = msg.getUserNames();
        view.reloadState(msg.getMap(), msg.getGodNames(), msg.getUserNames(), playerID);
        view.updateScreen();
    }

    @Override
    public void visit(EliminationNotification msg) {
        view.elimination(msg.getClientID() == playerID, msg.getPlayerName(), msg.getEliminatedWorkers());
    }

    @Override
    public void visit(WinNotification msg) {
        view.win(msg.getClientID() == playerID, msg.getPlayerName());
    }
}
