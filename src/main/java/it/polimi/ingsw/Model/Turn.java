package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.Commands;
import it.polimi.ingsw.Controller.SocketInterface;
import it.polimi.ingsw.View.ViewInterface;

import java.util.List;

public class Turn {
    private SocketInterface socket;
    private ViewInterface view;
    private Card card;
    private boolean isMyTurn = false;
    private boolean endGame = false;

    public Turn(SocketInterface socket, ViewInterface view) {
        this.socket = socket;
        this.view = view;
    }

    public void update(Commands commands) {

    }

    private void chooseCard() {
        List<Card> cardList;
        System.out.println("Scegli una carta");
        cardList = Card.getCardList();
        for(Card currCard: cardList) {
            System.out.println(currCard.getName() + "\n" + currCard.getDescription() + "\n");
        }
    }

    public void myTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }
}
