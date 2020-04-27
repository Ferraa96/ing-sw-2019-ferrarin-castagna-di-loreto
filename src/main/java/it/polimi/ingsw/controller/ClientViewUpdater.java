package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.view.ViewInterface;

/**
 * handle the messages from the server
 */
public class ClientViewUpdater {
    private final ViewInterface view;

    /**
     * adapter server-client
     * @param view the view instance
     */
    public ClientViewUpdater(ViewInterface view) {
        this.view = view;
    }

    /**
     * translates the command from the server to call to methods in the client
     * @param commands the command sent by the server
     */
    public void receive(Object commands) {
        if(commands instanceof MoveInstr) {
            view.move(((MoveInstr) commands).getMovements());
            view.updateScreen();
        } else if(commands instanceof BuildInstr) {
            if(!((BuildInstr) commands).isDome()) {
                view.buildBlock(((BuildInstr) commands).getPos(), ((BuildInstr) commands).getHeight());
            } else {
                view.buildDome(((BuildInstr) commands).getPos(), ((BuildInstr) commands).getHeight());
            }
            view.updateScreen();
        } else if(commands instanceof AskForReloadStateInstr) {
            view.askForReloadState();
        } else if(commands instanceof Cell[][]) {
            view.reloadState((Cell[][]) commands);
            view.updateScreen();
        } else if(commands instanceof ChoosePosInstr) {
            view.choosePosition(((ChoosePosInstr) commands).getAvailablePositions());
        } else if(commands instanceof ChooseCardInstr) {
            view.chooseCard(((ChooseCardInstr) commands).getAlreadyChosen());
        } else if(commands instanceof ChooseCardListInstr) {
            view.chooseCardList(((ChooseCardListInstr) commands).getNumPlayers());
        } else if(commands instanceof FirstPositioningInstr) {
            view.firstPositioning(((FirstPositioningInstr) commands).getPositions());
        } else if(commands instanceof SetNameInstr) {
            view.setName(((SetNameInstr) commands).getAllNames(), ((SetNameInstr) commands).getPlayerID());
        } else if(commands instanceof ChooseWorkerInstr) {
            view.chooseWorker();
        } else if(commands instanceof SetPowerInstr) {
            view.choosePower();
        } else if(commands instanceof String) {
            view.handleEndGame((String) commands);
        }
        else {
            System.out.println("Comando non previsto");
        }
    }
}
