package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;
import java.util.List;

public class ChooseCardListInstr implements Serializable, MessageInterface {
    private int numPlayers;
    private List<Integer> chosenCards;

    public ChooseCardListInstr(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public ChooseCardListInstr(List<Integer> chosenCards) {
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
}
