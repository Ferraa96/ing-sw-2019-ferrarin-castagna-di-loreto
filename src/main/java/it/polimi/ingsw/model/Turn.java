package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.Instruction;
import it.polimi.ingsw.controller.SocketServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * handle all the turns
 */
//firstPosition dei worker, setEnemies, controlla Athena e setta noUp degli effect a true, controlla Pan
public class Turn implements ModelInterface {
    private int numPlayer;
    private int actualPlayer = 0;
    private SocketServer socket;
    private List<Card> cardList;
    private Commands commands;
    private Board board;
    private CardDeserializer cardDeserializer = new CardDeserializer();
    private SaveState saveState;
    private Map<Integer, String> names;
    private int totalEffects;
    private int currEffect = 0;
    private Worker currWorker;

    public Turn(SocketServer socket, int numPlayer) {
        this.socket = socket;
        this.numPlayer = numPlayer;
        saveState = new SaveState();
        cardList = new ArrayList<>();
        commands = new Commands();
        names = new HashMap<>();
        board = new Board();
        askName();
    }

    /**
     * handle all the turns after the setup of the game
     */
    private void game() {
        System.out.println("Setup complete");
        commands.setInstruction(Instruction.chooseWorker);
        socket.sendTo(actualPlayer, commands);
    }

    public void chooseWorker(Position pos) {
        if(cardList.get(actualPlayer).getWorker1().getPosition().isEqual(pos)) {
            verifyPower(cardList.get(actualPlayer).getWorker1());
        } else {
            verifyPower(cardList.get(actualPlayer).getWorker2());
        }
    }

    private void verifyPower(Worker worker) {
        currWorker = worker;
        System.out.println("Worker: " + currWorker.getPosition().getRow() + " " + currWorker.getPosition().getColumn());
        if(cardList.get(actualPlayer).checkCardActivation(worker)) {
            commands.setInstruction(Instruction.usePower);
            socket.sendTo(actualPlayer, commands);
        } else {
            setPower(false);
        }
    }

    public void setPower(boolean use) {
        cardList.get(actualPlayer).setActivePower(use);
        if(use) {
            totalEffects = cardList.get(actualPlayer).getCardRoutine().size();
        } else {
            totalEffects = cardList.get(actualPlayer).getStandardRoutine().size();
        }
        commands.setInstruction(Instruction.choosePosition);
        commands.setAvailablePos(cardList.get(actualPlayer).availablePositions(currEffect, currWorker));
        socket.sendTo(actualPlayer, commands);
        for(Position pos: commands.getAvailablePos()) {
            System.out.println(pos.getRow() + " " + pos.getColumn());
        }
    }

    private void providePositions() {

    }

    public void apply(Position pos) {
        if(cardList.get(actualPlayer).applyEffect(currEffect, currWorker, pos)) {
            System.out.println("Winner winner chicken dinner");
        } else {
            System.out.println("Non hai vinto lol");
        }
    }

    /**
     * it send to actualPlayer the list of all the positions available for the first positioning
     */
    private void firstPositioning() {
        commands.setInstruction(Instruction.initialPosition);
        commands.setAvailablePos(cardList.get(actualPlayer).availableFirstPositioning());
        socket.sendTo(actualPlayer, commands);
    }

    /**
     * called by all the players when they choose the position of their workers
     * @param positions the list of positions of the two workers of actualPlayer
     */
    @Override
    public void choosePosition(List<Position> positions) {
        Position w1 = positions.get(0);
        Position w2 = positions.get(1);
        cardList.get(actualPlayer).firstPositioning(w1, 0);
        cardList.get(actualPlayer).firstPositioning(w2, 1);
        commands.setInstruction(Instruction.move);
        commands.setPlayer(actualPlayer);
        commands.setPosition(w1);
        socket.sendToAllExcept(actualPlayer, commands);
        commands.setPosition(w2);
        socket.sendToAllExcept(actualPlayer, commands);
        nextTurn();
        if(actualPlayer == 0) {
            setEnemiesLists();
            game();
        } else {
            firstPositioning();
        }
    }

    public void setPlayerName(int playerID, List<String> nameList) {
        names.put(playerID, nameList.get(0));
        nextTurn();
        if(actualPlayer == 0) {
            initialChoose();
        } else {
            commands.getStringList().add(nameList.get(0));
            askName();
        }
    }

    private void askName() {
        commands.setInstruction(Instruction.setName);
        socket.sendTo(actualPlayer, commands);
    }

    /**
     * send to the first player all the cards, the player has to choose as many as the number of players
     */
    private void initialChoose() {
        commands.setInstruction(Instruction.initialCardChoose);
        commands.setPlayer(numPlayer);
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            list.add(i);
        }
        commands.setCardList(list);
        socket.sendTo(actualPlayer, commands);
        commands.setInstruction(Instruction.setCard);
    }

    /**
     * called by all the players to set their card
     * @param chosenCard the index of the card chosen
     */
    @Override
    public void setCards(int chosenCard) {
        Card card = cardDeserializer.getSelectedCardFlags().get(chosenCard);
        card.setCard(board.getBoard(), actualPlayer);
        cardList.add(card);
        commands.removeCard(chosenCard);
        if(!commands.getCardList().isEmpty()) {
            nextTurn();
            System.out.println("Invio a player " + actualPlayer);
            commands.setInstruction(Instruction.setCard);
            socket.sendTo(actualPlayer, commands);
        } else {
            Card temp = cardList.get(numPlayer - 1);
            cardList.remove(temp);
            cardList.add(0, temp);
            firstPositioning();
        }
    }

    /**
     * called by the first player, he has chosen all the cards in the game
     * @param cards the list of cards
     */
    @Override
    public void setInitialCards(List<Integer> cards) {
        nextTurn();
        commands.setCardList(cards);
        socket.sendTo(actualPlayer, commands);
    }

    private void setEnemiesLists() {
        for (int i = 0; i < cardList.size() ; i++)
            cardList.get(i).setEnemies(cardList);
    }

    /**
     * update the actualPlayer parameter
     */
    private void nextTurn() {
        if(actualPlayer < numPlayer - 1) {
            actualPlayer++;
        } else {
            actualPlayer = 0;
        }
    }
}