package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;
import java.util.List;

/**
 * lets the player choose a card
 */
public class ChooseCardNotification implements Serializable, NotificationInterface {
    private int clientID;
    private List<Integer> availableCards;
    private int chosenCard;

    public ChooseCardNotification(int chosenCard) {
        this.chosenCard = chosenCard;
    }

    public ChooseCardNotification(List<Integer> availableCards, int clientID) {
        this.availableCards = availableCards;
        this.clientID = clientID;
    }

    public List<Integer> getAvailableCards() {
        return availableCards;
    }

    public int getChosenCard() {
        return chosenCard;
    }

    @Override
    public void accept(NotificationVisitor v) {
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
