package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * commands class
 */
public class Commands implements Serializable {
    private Position position;
    private int height;
    private int player;
    private int chosenCard;
    private Instruction instruction;
    private List<Integer> cardList = new ArrayList<>();
    private List<Position> availablePos = new ArrayList<>();
    private Cell[][] map;
    private List<String> stringList = new ArrayList<>();
    private boolean answer;
    private Map<Integer, Position> movement = new HashMap<>();  //<workerID, finalPos>

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

    public Cell[][] getMap() {
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setMovement(Map<Integer, Position> movement) {
        this.movement = movement;
    }

    public Map<Integer, Position> getMovement() {
        return movement;
    }
}