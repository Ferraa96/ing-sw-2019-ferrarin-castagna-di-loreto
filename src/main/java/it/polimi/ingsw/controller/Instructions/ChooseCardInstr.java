package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;
import java.util.List;

public class ChooseCardInstr implements Serializable {
    private List<Integer> alreadyChosen;
    private int chosenCard;

    public ChooseCardInstr(int chosenCard) {
        this.chosenCard = chosenCard;
    }

    public ChooseCardInstr(List<Integer> alreadyChosen) {
        this.alreadyChosen = alreadyChosen;
    }

    public List<Integer> getAlreadyChosen() {
        return alreadyChosen;
    }

    public int getChosenCard() {
        return chosenCard;
    }
}
