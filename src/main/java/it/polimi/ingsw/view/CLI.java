package it.polimi.ingsw.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.Instruction;
import it.polimi.ingsw.controller.SocketClient;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.CardDeserializer;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Position;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;

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
    private List<Position> workerPos;

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
        workerPos = new ArrayList<>();
    }

    @Override
    public void setPlayerID(int playerID) {
        commands.setPlayer(playerID);
        System.out.println("Sono il giocatore " + playerID);
    }

    @Override
    public void move(Map<Integer,Position> movement) {
        for(Integer key: movement.keySet()) {
            int index = allTiles.indexOf(gameMap[movement.get(key).getRow()][movement.get(key).getColumn()]);
            gameMap[movement.get(key).getRow()][movement.get(key).getColumn()] = allTiles.get(index + 1);
            workerPos.add(key, movement.get(key));
        }
    }

    @Override
    public void buildBlock(Position position, int height) {
        int index = allTiles.indexOf(gameMap[position.getRow()][position.getColumn()]);
        gameMap[position.getRow()][position.getColumn()] = allTiles.get(index + 2);
    }

    @Override
    public void buildDome(Position position, int height) {
        int index = allTiles.indexOf(gameMap[position.getRow()][position.getColumn()]);
        gameMap[position.getRow()][position.getColumn()] = allTiles.get(11 - index);
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
    public void choosePosition(List<Position> list) {
        System.out.print("Scegli la posizione (riga, colonna): ");
        commands.setInstruction(Instruction.choosePosition);
        commands.setPosition(verifyPosition(list));
        socket.send(commands);
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
        List<Integer> chosen = new ArrayList<>();
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
            System.out.print("Carta " + (i + 1) + ": ");
            chosen.add(verifyCard(tempList));
        }
        commands.setCardList(chosen);
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
            System.out.print("Carta non valida, selezionane un'altra: ");
            card = scanner.nextInt();
        }
    }

    @Override
    public void firstPositioning(List<Position> availablePos) {
        List<Position> list = new ArrayList<>();
        Map<Integer, Position> movement = new HashMap<>();
        Position pos;
        for(Position currPos: availablePos) {
            markPosition(currPos, 'x');
        }
        updateScreen();
        System.out.print("Posizione iniziale lavoratore 1 (riga, colonna): ");
        pos = verifyPosition(availablePos);
        movement.put(commands.getPlayer()*2,pos);
        //move(commands.getPlayer(), pos);
        move(movement);
        updateScreen();
        list.add(pos);
        movement.clear();
        System.out.print("Posizione iniziale lavoratore 2 (riga, colonna): ");
        pos = verifyPosition(availablePos);
        movement.put(commands.getPlayer()*2+1, pos);
        //move(commands.getPlayer(), pos);
        move(movement);
        list.add(pos);
        commands.setInstruction(Instruction.initialPosition);
        commands.setAvailablePos(list);
        socket.send(commands);
        for(Position currPos: availablePos) {
            markPosition(currPos, ' ');
        }
        updateScreen();
    }

    @Override
    public void resumeGame(Cell[][] cells) {

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
            System.out.print("Scelta non valida, inseriscine un'altra: ");
            pos = new Position(scanner.nextInt(), scanner.nextInt());
        }
    }

    private void markPosition(Position pos, char mark) {
        gameMap[pos.getRow()][pos.getColumn()].setIdentifier(mark);
    }

    @Override
    public void setName(List<String> stringList) {
        System.out.print("Imposta il nome: ");
        commands.setInstruction(Instruction.setName);
        commands.getStringList().add(0, verifyName(stringList));
        socket.send(commands);
    }

    private String verifyName(List<String> alreadyChosen) {
        String name;
        boolean ok = true;
        while (true) {
            name = scanner.nextLine();
            for(String curr: alreadyChosen) {
                if(curr.equals(name)) {
                    ok = false;
                    break;
                }
            }
            if(ok) {
                return name;
            }
            System.out.print("Nome " + name + " già preso, scegline un altro: ");
            ok = true;
        }
    }

    @Override
    public void chooseWorker() {
//        for(Position curr: workerPos) {
//            markPosition(curr, 'x');
//        }
//        updateScreen();
        System.out.print("Seleziona il worker: ");
        commands.setPosition(verifyPosition(workerPos));
        commands.setInstruction(Instruction.chooseWorker);
        socket.send(commands);
    }

    @Override
    public void choosePower() {
        System.out.print("Attivare il potere della carta? S/N ");
        commands.setInstruction(Instruction.usePower);
        commands.setAnswer(verifyBoolean());
        socket.send(commands);
    }

    private boolean verifyBoolean() {
        String answer;
        while (true) {
            answer = scanner.nextLine();
            if(answer.equals("s")) {
                return true;
            } else if(answer.equals("n")) {
                return false;
            }
        }
    }
}
