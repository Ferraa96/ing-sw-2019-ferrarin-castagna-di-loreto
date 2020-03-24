package it.polimi.ingsw.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.Instruction;
import it.polimi.ingsw.controller.SocketClient;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.CardDeserializer;
import it.polimi.ingsw.model.Position;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * the CLI
 */
public class CLI implements ViewInterface {
    private Commands commands;
    private SocketClient socket;
    private List<Tile> allTiles;
    private Tile[][] gameMap;
    private Scanner scanner = new Scanner(System.in);
    private CardDeserializer cardDeserializer = new CardDeserializer();

    /**
     * creates the CLI
     * @param socket the server socket
     */
    public CLI(SocketClient socket) {
        this.socket = socket;
        commands = new Commands();
        Gson gson = new Gson();
        InputStream input = getClass().getResourceAsStream("/gameMap.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        Type tilesList = new TypeToken<ArrayList<Tile>>() {}.getType();
        allTiles = gson.fromJson(bf, tilesList);
        gameMap = new Tile[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                gameMap[i][j] = allTiles.get(0);
            }
        }
        updateScreen();
    }

    @Override
    public void setPlayerID(int playerID) {
        commands.setPlayer(playerID);
        System.out.println("Sono il giocatore " + playerID);
    }

    @Override
    public void move(int player, Position position) {
        int index = allTiles.indexOf(gameMap[position.getRow()][position.getColumn()]);
        gameMap[position.getRow()][position.getColumn()] = allTiles.get(index + 1);
        updateScreen();
    }

    @Override
    public void buildBlock(Position position, int height) {
        int index = allTiles.indexOf(gameMap[position.getRow()][position.getColumn()]);
        gameMap[position.getRow()][position.getColumn()] = allTiles.get(index + 2);
        updateScreen();
    }

    @Override
    public void buildDome(Position position, int height) {
        int index = allTiles.indexOf(gameMap[position.getRow()][position.getColumn()]);
        gameMap[position.getRow()][position.getColumn()] = allTiles.get(11 - index);
        updateScreen();
    }

    @Override
    public void updateScreen() {
        System.out.println("-----------------------------------------------------------------------");
        for(int i = 0; i < 5; i++) {
            for(int line = 0; line < 5; line++) {
                for(int j = 0; j < 5; j++) {
                    System.out.print("|" + gameMap[i][j].getLine(line));
                }
                System.out.print("|\n");
            }
            System.out.println("-----------------------------------------------------------------------");
        }
    }

    @Override
    public void chooseCard(List<Integer> cardList) {
        List<Card> cards;
        cards = cardDeserializer.getCardList();
        System.out.println("Scegli una carta tra\n");
        for(Integer curr: cardList) {
            System.out.println(curr + ": " + cards.get(curr).getName());
            System.out.println(cards.get(curr).getDescription() + "\n");
        }
        commands.setInstruction(Instruction.setCard);
        commands.setChosenCard(verifyCard(cardList));
        socket.send(commands);
    }

    @Override
    public void chooseCardList(int num) {
        List<Card> cards = cardDeserializer.getCardList();
        List<Integer> choosen = new ArrayList<>();
        System.out.println("Sei il primo giocatore, scegli " + num + " carte tra\n");
        for(int i = 0; i < cards.size(); i++) {
            System.out.println(i + ": " + cards.get(i).getName() + "\n" + cards.get(i).getDescription() + "\n");
        }
        commands.setInstruction(Instruction.initialCardChoose);
        List<Integer> tempList = new ArrayList<>();
        for(int i = 0; i < cards.size(); i++) {
            tempList.add(i);
        }
        for(int i = 0; i < num; i++) {
            System.out.println("Carta " + (i + 1) + ": ");
            choosen.add(verifyCard(tempList));
        }
        commands.setCardList(choosen);
        socket.send(commands);
    }

    /**
     * verify and return the index of the card chosen by the player
     * @param list the list of all the cards available
     * @return the card index chosen and verified
     */
    private int verifyCard(List<Integer> list) {
        int card = scanner.nextInt();
        boolean ok = false;
        while(true) {
            for (Integer i : list) {
                if (i == card) {
                    ok = true;
                    list.remove(i);
                    break;
                }
            }
            if(ok) {
                return card;
            }
            System.out.println("Carta non valida, riprova: ");
            card = scanner.nextInt();
        }
    }

    @Override
    public void firstPositioning(List<Position> availablePos) {
        List<Position> list = new ArrayList<>();
        Position pos;
        System.out.println("Posizione iniziale lavoratore 1: (riga, colonna) ");
        pos = verifyPosition(availablePos);
        move(commands.getPlayer(), pos);
        list.add(pos);
        System.out.println("Posizione iniziale lavoratore 2: (riga, colonna) ");
        pos = verifyPosition(availablePos);
        move(commands.getPlayer(), pos);
        list.add(pos);
        commands.setInstruction(Instruction.initialPosition);
        commands.setAvailablePos(list);
        socket.send(commands);
    }

    /**
     * verify and return the position entered by the player is available
     * @param list the list of all the available positions
     * @return the position chosen by the player and verified
     */
    private Position verifyPosition(List<Position> list) {
        Position pos = new Position(scanner.nextInt(), scanner.nextInt());
        boolean ok = false;
        while(true) {
            for (Position curr : list) {
                if (pos.getRow() == curr.getRow() && pos.getColumn() == curr.getColumn()) {
                    ok = true;
                    list.remove(curr);
                    break;
                }
            }
            if(ok) {
                return pos;
            }
            System.out.println("Posizione non valida, riprova: ");
            pos = new Position(scanner.nextInt(), scanner.nextInt());
        }
    }
}
