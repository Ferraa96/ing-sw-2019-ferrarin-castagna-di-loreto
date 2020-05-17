package it.polimi.ingsw.controller.Client;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.view.ViewInterface;

/**
 * handle the messages from the server
 */
public class ClientUpdater implements MessageVisitor {
    private final ViewInterface view;
    private int playerID;

    /**
     * adapter server-client
     * @param view the view instance
     */
    public ClientUpdater(ViewInterface view) {
        this.view = view;
    }

    @Override
    public void visit(AskForReloadStateNotification msg) {
        if(msg.getClientID() == playerID) {
            view.askForReloadState();
        } else {
            view.notificationForOtherClient("Il giocatore " + msg.getClientID() + " sta scegliendo se caricare la partita");
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
            view.notificationForOtherClient("Il giocatore " + msg.getClientID() + " sta scegliendo la carta");
        }
    }

    @Override
    public void visit(ChooseCardListNotification msg) {
        if(msg.getClientID() == playerID) {
            view.chooseCardList(msg.getNumPlayers());
        } else {
            view.notificationForOtherClient("Scelta delle carte di gioco...");
        }
    }

    @Override
    public void visit(ChoosePosNotification msg) {
        if(msg.getClientID() == playerID) {
            view.choosePosition(msg.getAvailablePositions());
        } else {
            view.notificationForOtherClient("Il giocatore " + msg.getClientID() + " sta scegliendo la posizione");
        }
    }

    @Override
    public void visit(ChooseWorkerNotification msg) {
        if(msg.getClientID() == playerID) {
            view.chooseWorker(msg.getAvailableWorkers());
        } else {
            view.notificationForOtherClient("Il giocatore " + msg.getClientID() + " sta scegliendo il lavoratore");
        }
    }

    @Override
    public void visit(FirstPositioningNotification msg) {
        if(msg.getClientID() == playerID) {
            if(!msg.isLoadPos()) {
                view.firstPositioning(msg.getPositions(), msg.getGodName(), true);
            }
        } else {
            if(msg.isLoadPos()) {
                view.firstPositioning(msg.getPositions(), msg.getGodName(), false);
            } else {
                view.notificationForOtherClient("Il giocatore " + msg.getClientID() + " sta posizionando i lavoratori");
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
            view.notificationForOtherClient("Il giocatore " + msg.getClientID() + " sta scegliendo il potere");
        }
    }

    @Override
    public void visit(DisconnectionNotification msg) {
        view.handleDisconnection(msg.getClientID());
    }

    @Override
    public void visit(LoadGameNotification msg) {
        view.reloadState(msg.getMap(), msg.getGodNames());
        view.updateScreen();
    }

    @Override
    public void visit(EliminationNotification msg) {
        view.elimination(msg.getClientID() == playerID, msg.getPlayerName());
    }

    @Override
    public void visit(WinNotification msg) {
        view.win(msg.getClientID() == playerID, msg.getPlayerName());
    }
}
