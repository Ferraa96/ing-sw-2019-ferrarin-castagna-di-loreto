package it.polimi.ingsw.controller;

import it.polimi.ingsw.view.ViewInterface;

/**
 * handle the messages from the server
 */
public class ClientViewUpdater {
    private static ViewInterface view;

    /**
     * adapter server-client
     * @param view the view instance
     */
    public ClientViewUpdater(ViewInterface view) {
        ClientViewUpdater.view = view;
    }

    /**
     * translates the command from the server to call to methods in the client
     * @param commands the command sent by the server
     */
    public static void receive(Commands commands) {
        switch (commands.getInstruction()) {
            case move:
                view.move(commands.getPlayer(), commands.getPosition());
                break;
            case buildBlock:
                view.buildBlock(commands.getPosition(), commands.getHeight());
                break;
            case buildDome:
                view.buildDome(commands.getPosition(), commands.getHeight());
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
            default:
                System.out.println("Ricevuto " + commands.getInstruction());
        }
    }
}