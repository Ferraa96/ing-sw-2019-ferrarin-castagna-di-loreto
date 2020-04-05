package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.Instruction;
import it.polimi.ingsw.controller.SocketClient;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.CardDeserializer;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Position;

import java.util.*;

/**
 * the CLI
 */
public class CLI implements ViewInterface {
    private Commands commands;
    private SocketClient socket;
    private Tile[][] gameMap;
    private Scanner scanner = new Scanner(System.in);
    private Map<Integer, Position> workerPos;
    private TileGetter tileGetter;

    /**
     * creates the CLI
     * @param socket the server socket
     */
    public CLI(SocketClient socket) {
        this.socket = socket;
        commands = new Commands();
        gameMap = new Tile[5][5];
        tileGetter = new TileGetter();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                gameMap[i][j] = tileGetter.getTile(0, 0, false);
            }
        }
        updateScreen();
        workerPos = new HashMap<>();
    }

    /**
     * set the player ID
     * @param playerID the player ID
     */
    @Override
    public void setPlayerID(int playerID) {
        commands.setPlayer(playerID);
        System.out.println("Sono il giocatore " + playerID);
    }

    /**
     * change the position of all players in movement
     * @param movement workerId and position of the movement
     */
    @Override
    public void move(Map<Integer, Position> movement) {
        for(Integer key: movement.keySet()) {
            int height;
            Position pos = movement.get(key);
            if(workerPos.size() > key) {
                Position oldPos = workerPos.get(key);
                height = gameMap[oldPos.getRow()][oldPos.getColumn()].getHeight();
                gameMap[oldPos.getRow()][oldPos.getColumn()] = tileGetter.getTile(0, height, false);
            }
            height = gameMap[pos.getRow()][pos.getColumn()].getHeight();
            System.out.println("GetTile(1, " + height + ", false)");
            gameMap[pos.getRow()][pos.getColumn()] = tileGetter.getTile(1, height, false);
            if(workerPos.containsKey(key)) {
                workerPos.replace(key, pos);
            } else {
                workerPos.put(key, pos);
            }
        }
    }

    /**
     * add a block in position
     * @param position the position to build the block in
     * @param height the height of the block
     */
    @Override
    public void buildBlock(Position position, int height) {
        gameMap[position.getRow()][position.getColumn()] = tileGetter.getTile(0, height, false);
        gameMap[position.getRow()][position.getColumn()].setHeight(height);
    }

    /**
     * add a dome
     * @param position the position to build the dome in
     * @param height the height of the dome
     */
    @Override
    public void buildDome(Position position, int height) {
        gameMap[position.getRow()][position.getColumn()] = tileGetter.getTile(0, height, true);
    }

    /**
     * update the screen
     */
    @Override
    public void updateScreen() {
        System.out.println(String.format("%11d" + "%14d" + "%14d" + "%14d" + "%14d", 1, 2, 3, 4, 5));
        System.out.println("    -----------------------------------------------------------------------");
        for(int raw = 0; raw < 5; raw++) {
            for(int line = 0; line < 5; line++) {
                if(line == 2) {
                    System.out.print(" " + (raw + 1) + "  ");
                } else {
                    System.out.print("    ");
                }
                for(int column = 0; column < 5; column++) {
                    System.out.print("|" + gameMap[raw][column].getLine(line));
                }
                System.out.print("|\n");
            }
            System.out.println("    -----------------------------------------------------------------------");
        }
    }

    /**
     * lets the player select the position
     * @param list the list of all possible positions
     */
    @Override
    public void choosePosition(List<Position> list) {
        for(Position pos: list) {
            markPosition(pos, 'x');
        }
        updateScreen();
        System.out.print("Scegli la posizione (riga, colonna): ");
        commands.setInstruction(Instruction.choosePosition);
        commands.setPosition(verifyPosition(list));
        for(Position pos: list) {
            markPosition(pos, ' ');
        }
        socket.send(commands);
    }

    @Override
    public void chooseCard(List<Integer> cardList) {
        List<Card> cards;
        cards = new CardDeserializer().getCardList();
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
        List<Card> cards = new CardDeserializer().getCardList();
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
        movement.put(commands.getPlayer() * 2, pos);
        move(movement);
        updateScreen();
        list.add(pos);
        movement.clear();
        System.out.print("Posizione iniziale lavoratore 2 (riga, colonna): ");
        pos = verifyPosition(availablePos);
        movement.put(commands.getPlayer() * 2 + 1, pos);
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
        Position pos = new Position(scanner.nextInt() - 1, scanner.nextInt() - 1);
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
            pos = new Position(scanner.nextInt() - 1, scanner.nextInt() - 1);
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
        Position posW1 = workerPos.get(commands.getPlayer() * 2);
        Position posW2 = workerPos.get(commands.getPlayer() * 2 + 1);
        markPosition(posW1, 'x');
        markPosition(posW2, 'x');
        updateScreen();
        System.out.print("Seleziona il worker: ");
        List<Position> temp = new ArrayList<>();
        temp.add(posW1);
        temp.add(posW2);
        commands.setPosition(verifyPosition(temp));
        markPosition(posW1, ' ');
        markPosition(posW2, ' ');
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
