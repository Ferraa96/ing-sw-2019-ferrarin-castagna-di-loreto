package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.Instruction;
import it.polimi.ingsw.controller.SocketServer;

import java.util.ArrayList;
import java.util.List;

/**
 * handle all the turns
 */
//firstPosition dei worker, setEnemies, controlla Athena e setta noUp degli effect a true, controlla Pan
public class Turn implements ModelInterface {
    private int numPlayer;
    private int actualPlayer = 0;
    private SocketServer socket;
    private List<Card> cardList;
    private ArrayList<Card> playedCards= new ArrayList<>();
    private Commands commands;
    private Board board;
    private Move move;
    private CardDeserializer cardDeserializer = new CardDeserializer();

    public Turn(SocketServer socket, int numPlayer) {
        this.socket = socket;
        this.numPlayer = numPlayer;
        cardList = new ArrayList<>();
        commands = new Commands();
        initialChoose();
        board = new Board();
//        move = new Move(board.getBoard());
    }

    /**
     * handle all the turns after the setup of the game
     */
    private void game() {
        System.out.println("Comincia la partita");
    }

    /**
     * it send to actualPlayer the list of all the positions available for the first positioning
     */
    private void firstPositioning() {
        commands.setInstruction(Instruction.initialPosition);
//        commands.setAvailablePos(move.firstPositioning());
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
        board.getBoard()[w1.getRow()][w1.getColumn()].setWorkerID(cardList.get(actualPlayer).getWorker1().getWorkerID());
        board.getBoard()[w2.getRow()][w2.getColumn()].setWorkerID(cardList.get(actualPlayer).getWorker2().getWorkerID());
        commands.setInstruction(Instruction.move);
        commands.setPlayer(actualPlayer);
        commands.setPosition(w1);
        socket.sendToAllExcept(actualPlayer, commands);
        commands.setPosition(w2);
        socket.sendToAllExcept(actualPlayer, commands);
        nextTurn();
        if(actualPlayer == 0) {
            game();
        } else {
            firstPositioning();
        }
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
        Card card = cardDeserializer.getCardList().get(chosenCard);
        cardList.add(card);
        commands.removeCard(chosenCard);
        if(!commands.getCardList().isEmpty()) {
            nextTurn();
            System.out.println("Invio a player " + actualPlayer);
            commands.setInstruction(Instruction.setCard);
            socket.sendTo(actualPlayer, commands);
        } else {
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

    public void setEnemiesLists() {
        for (int i = 0; i <playedCards.size() ; i++)
            playedCards.get(i).setEnemies(playedCards);
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