package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Turn;

public class ServerModelUpdater {
    private static Turn turn;

    public ServerModelUpdater(Turn turn) {
        ServerModelUpdater.turn = turn;
    }

    public static void receive(Commands commands) {
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
}