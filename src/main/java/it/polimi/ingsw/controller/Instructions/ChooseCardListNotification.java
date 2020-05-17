package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;
import java.util.List;

/**
 * lets the first player choose all the card that will be in the game
 */
public class ChooseCardListNotification implements Serializable, MessageInterface {
    private int clientID;
    private int numPlayers;
    private List<Integer> chosenCards;

    public ChooseCardListNotification(int numPlayers, int clientID) {
        this.numPlayers = numPlayers;
        this.clientID = clientID;
    }

    public ChooseCardListNotification(List<Integer> chosenCards) {
        this.chosenCards = chosenCards;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public List<Integer> getChosenCards() {
        return chosenCards;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }

    @Override
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public int getClientID() {
        return clientID;
    }
}
