package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Commands implements Serializable {
    private Position position;
    private int height;
    private int player;
    private int chosenCard;
    private Instruction instruction;
    private List<Integer> cardList = new ArrayList<>();
    private List<Position> availablePos = new ArrayList<>();

    public Commands() {}

    public Commands(Position position, int player) {
        this.position = position;
        this.player = player;
    }

    public List<Position> getAvailablePos() {
        return availablePos;
    }

    public void setAvailablePos(List<Position> availablePos) {
        this.availablePos = availablePos;
    }

    public int getChosenCard() {
        return chosenCard;
    }

    public List<Integer> getCardList() {
        return cardList;
    }

    public void setCardList(List<Integer> cardList) {
        this.cardList = cardList;
    }

    public void removeCard(int numCard) {
        cardList.remove(Integer.valueOf(numCard));
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setChosenCard(int chosenCard) {
        this.chosenCard = chosenCard;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }
}