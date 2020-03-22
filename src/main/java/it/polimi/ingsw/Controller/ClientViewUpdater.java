package it.polimi.ingsw.Controller;

import it.polimi.ingsw.View.ViewInterface;

public class ClientViewUpdater {
    private static ViewInterface view;

    public ClientViewUpdater(ViewInterface view) {
        ClientViewUpdater.view = view;
    }

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
