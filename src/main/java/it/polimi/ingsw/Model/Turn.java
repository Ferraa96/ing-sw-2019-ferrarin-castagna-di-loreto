package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.SocketInterface;
import it.polimi.ingsw.View.ViewInterface;

public class Turn {
    private SocketInterface socket;
    private ViewInterface view;
    private Card card;
    private boolean isMyTurn = false;

    public Turn(SocketInterface socket, ViewInterface view, Card card) {
        this.socket = socket;
        this.view = view;
        this.card = card;
        if(card.getFirstPlayer()) {
            isMyTurn = true;
        }
    }

    private void chooseCard() {
        while(!isMyTurn) {
            System.out.println("Attendi il tuo turno");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Scegli una carta");

    }

    public void myTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }
}
