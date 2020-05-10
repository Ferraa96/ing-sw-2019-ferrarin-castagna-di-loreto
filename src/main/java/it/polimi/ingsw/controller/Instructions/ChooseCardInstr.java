package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;
import java.util.List;

/**
 * lets the player choose a card
 */
public class ChooseCardInstr implements Serializable, MessageInterface {
    private List<Integer> availableCards;
    private int chosenCard;

    public ChooseCardInstr(int chosenCard) {
        this.chosenCard = chosenCard;
    }

    public ChooseCardInstr(List<Integer> availableCards) {
        this.availableCards = availableCards;
    }

    public List<Integer> getAvailableCards() {
        return availableCards;
    }

    public int getChosenCard() {
        return chosenCard;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
