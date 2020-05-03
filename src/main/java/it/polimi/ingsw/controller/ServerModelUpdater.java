package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.model.ModelInterface;

/**
 * handle the messages from the client
 */
public class ServerModelUpdater {
    private final ModelInterface turn;

    public ServerModelUpdater(ModelInterface turn) {
        this.turn = turn;
    }

    /**
     * translate the commands from the client to call to methods in the server
     * @param command the command sent by the client
     */
    public void receive(Object command) {
        if(command instanceof ChooseCardInstr) {
            turn.setCards(((ChooseCardInstr) command).getChosenCard());
        } else if(command instanceof ChooseCardListInstr) {
            turn.setInitialCards(((ChooseCardListInstr) command).getChosenCards());
        } else if(command instanceof FirstPositioningInstr) {
            turn.setWorkersPosition(((FirstPositioningInstr) command).getPositions());
        } else if(command instanceof SetNameInstr) {
            turn.setPlayerName(((SetNameInstr) command).getName());
        } else if(command instanceof ChooseWorkerInstr) {
            turn.selectCorrectWorker(((ChooseWorkerInstr) command).getChosenWorker());
        } else if(command instanceof SetPowerInstr) {
            turn.providePosition(((SetPowerInstr) command).isPower());
        } else if(command instanceof ChoosePosInstr) {
            System.out.println("Scelta la posizione\n" + ((ChoosePosInstr) command).getChosenPos());
            turn.apply(((ChoosePosInstr) command).getChosenPos());
        } else if(command instanceof AskForReloadStateInstr) {
            turn.loadState(((AskForReloadStateInstr) command).isResponse());
        } else if(command instanceof Integer) {
            turn.handleDisconnection((int) command);
        }
        else {
            System.out.println("Comando non previsto");
        }
    }
}