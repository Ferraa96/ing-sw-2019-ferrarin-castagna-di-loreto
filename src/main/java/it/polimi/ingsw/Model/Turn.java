package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.Commands;
import it.polimi.ingsw.Controller.Instruction;
import it.polimi.ingsw.Controller.SocketServer;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private int numPlayer;
    private int actualPlayer = 0;
    private SocketServer socket;
    private List<Card> cardList;
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
        move = new Move(board.getBoard());
    }

    private void game() {
        System.out.println("Comincia la partita");
    }

    private void firstPositioning() {
        commands.setInstruction(Instruction.initialPosition);
        commands.setAvailablePos(move.firstPositioning());
        socket.sendTo(actualPlayer, commands);
    }

    public void choosePosition(List<Position> positions) {
        Position w1 = positions.get(0);
        Position w2 = positions.get(1);
        board.getBoard()[w1.getRow()][w1.getColumn()] = cardList.get(actualPlayer).getWorker1();
        board.getBoard()[w2.getRow()][w2.getColumn()] = cardList.get(actualPlayer).getWorker2();
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

    public void setInitialCards(List<Integer> cards) {
        nextTurn();
        commands.setCardList(cards);
        socket.sendTo(actualPlayer, commands);
    }

    private void nextTurn() {
        if(actualPlayer < numPlayer - 1) {
            actualPlayer++;
        } else {
            actualPlayer = 0;
        }
    }
}