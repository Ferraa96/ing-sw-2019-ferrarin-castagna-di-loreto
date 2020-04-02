package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Turn;

/**
 * handle the messages from the client
 */
public class ServerModelUpdater {
    private Turn turn;

    /**
     * translate the commands from the client to call to methods in the server
     * @param commands the command sent by the client
     */
    public void receive(Commands commands) {
        switch (commands.getInstruction()) {
            case move:
                break;
            case buildBlock:
                break;
            case buildDome:
                break;
            case setCard:
                turn.setCards(commands.getChosenCard());
                break;
            case initialCardChoose:
                turn.setInitialCards(commands.getCardList());
                break;
            case initialPosition:
                turn.choosePosition(commands.getAvailablePos());
                break;
            default:
                System.out.println("Ricevuto " + commands.getInstruction());
        }
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }
}