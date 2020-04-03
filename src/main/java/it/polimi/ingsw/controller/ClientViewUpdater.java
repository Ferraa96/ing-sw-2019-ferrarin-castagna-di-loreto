package it.polimi.ingsw.controller;

import it.polimi.ingsw.view.ViewInterface;

/**
 * handle the messages from the server
 */
public class ClientViewUpdater {
    private ViewInterface view;

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
    public void receive(Commands commands) {
        switch (commands.getInstruction()) {
            case move:
                view.move(commands.getPlayer(), commands.getPosition());
                view.updateScreen();
                break;
            case buildBlock:
                view.buildBlock(commands.getPosition(), commands.getHeight());
                view.updateScreen();
                break;
            case buildDome:
                view.buildDome(commands.getPosition(), commands.getHeight());
                view.updateScreen();
                break;
            case setCard:
                view.chooseCard(commands.getCardList());
                break;
            case setPlayerID:
                view.setPlayerID(commands.getPlayer());
                break;
            case initialCardChoose:
                view.chooseCardList(commands.getPlayer());
                break;
            case initialPosition:
                view.firstPositioning(commands.getAvailablePos());
                break;
            case resumeGame:
                break;
            case setName:
                view.setName(commands.getStringList());
                break;
            default:
                System.out.println("Ricevuto " + commands.getInstruction());
        }
    }
}
