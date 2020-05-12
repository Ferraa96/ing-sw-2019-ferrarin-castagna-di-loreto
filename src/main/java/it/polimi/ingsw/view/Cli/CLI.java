package it.polimi.ingsw.view.Cli;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.controller.Client.SocketClient;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.ViewInterface;

import java.util.*;

/**
 * the CLI
 */
public class CLI implements ViewInterface {
    private final SocketClient socket;
    private final Tile[][] gameMap;
    private final Scanner scanner = new Scanner(System.in);
    private final TileGetter tileGetter;

    /**
     * creates the CLI
     * @param socket the server socket
     */
    public CLI(SocketClient socket) {
        this.socket = socket;
        gameMap = new Tile[5][5];
        tileGetter = new TileGetter();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                gameMap[i][j] = tileGetter.getTile(0, 0, false);
            }
        }
        printLogo();
    }

    /**
     * change the position of all players in movement
     * @param movements the old and new position of the worker
     */
    @Override
    public void move(List<Movement> movements) {
        int height;
        List<String> godNames;
        //remove old positions
        godNames = new ArrayList<>();
        for(Movement currMove: movements) {
            Position oldPos = currMove.getOldPos();
            godNames.add(gameMap[oldPos.getRow()][oldPos.getColumn()].getGodName());
            height = gameMap[oldPos.getRow()][oldPos.getColumn()].getHeight();
            gameMap[oldPos.getRow()][oldPos.getColumn()] = tileGetter.getTile(0, height, false);
        }
        //add the new positions
        for(int i = 0; i < movements.size(); i++) {
            Position newPos = movements.get(i).getNewPos();
            addPosition(newPos, godNames.get(i));
        }
    }

    private void addPosition(Position pos, String godName) {
        int height;
        height = gameMap[pos.getRow()][pos.getColumn()].getHeight();
        gameMap[pos.getRow()][pos.getColumn()] = tileGetter.getTile(1, height, false);
        gameMap[pos.getRow()][pos.getColumn()].setPlayerInfo(godName);
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
        System.out.println(String.format("\n%11d" + "%14d" + "%14d" + "%14d" + "%14d", 1, 2, 3, 4, 5));
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

    @Override
    public void askForReloadState() {
        System.out.print("E' presente un salvataggio, caricarlo? S/N ");
        socket.send(new AskForReloadStateInstr(verifyBoolean()));
    }

    @Override
    public void reloadState(Cell[][] map, List<String> godNames) {
        HashMap<Integer, Position> workerPos = new HashMap<>();
        System.out.println("Caricamento partita in corso...");
        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 5; column++) {
                int height = map[row][column].getHeight();
                if(height > 0) {
                    if(map[row][column].isDome()) {
                        buildDome(new Position(row, column), height);
                    } else {
                        buildBlock(new Position(row, column), height);
                    }
                }
                if(map[row][column].getWorkerID() != -1) {
                    workerPos.put(map[row][column].getWorkerID(), new Position(row, column));
                }
            }
        }
        for(int i = 0; i < godNames.size(); i++) {
            List<Position> myWorker = new ArrayList<>();
            myWorker.add(workerPos.get(i * 2));
            myWorker.add(workerPos.get(i * 2 + 1));
            firstPositioning(myWorker, godNames.get(i), false);
        }
    }

    /**
     * lets the player select the position
     * @param list the list of all possible positions
     */
    @Override
    public void choosePosition(List<Position> list) {
        for(Position pos: list) {
            gameMap[pos.getRow()][pos.getColumn()].setIdentifier('x');
        }
        updateScreen();
        System.out.print("Scegli la posizione (riga, colonna): ");
        for(Position pos: list) {
            gameMap[pos.getRow()][pos.getColumn()].setIdentifier(' ');
        }
        socket.send(new ChoosePosInstr(verifyPosition(list)));
    }

    @Override
    public void chooseCard(List<Integer> cardList) {
        List<Card> cards;
        cards = new IOHandler().getCardList();
        System.out.println("\nScegli una carta tra\n");
        for(Integer curr: cardList) {
            System.out.println(curr + ": " + cards.get(curr).getName());
            System.out.println(cards.get(curr).getDescription() + "\n");
        }
        socket.send(new ChooseCardInstr(verifyCard(cardList)));
    }

    @Override
    public void chooseCardList(int num) {
        List<Card> cards = new IOHandler().getCardList();
        List<Integer> chosen = new ArrayList<>();
        System.out.println("\nSei il primo giocatore, scegli " + num + " carte tra\n");
        for(int i = 0; i < cards.size(); i++) {
            System.out.println(i + ": " + cards.get(i).getName() + "\n" + cards.get(i).getDescription() + "\n");
        }
        List<Integer> tempList = new ArrayList<>();
        for(int i = 0; i < cards.size(); i++) {
            tempList.add(i);
        }
        for(int i = 0; i < num; i++) {
            System.out.print("Carta " + (i + 1) + ": ");
            chosen.add(verifyCard(tempList));
        }
        socket.send(new ChooseCardListInstr(chosen));
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
    public void firstPositioning(List<Position> availablePos, String godName, boolean isMyTurn) {
        if(isMyTurn) {
            List<Position> list = new ArrayList<>();
            Position pos;
            for (Position currPos : availablePos) {
                gameMap[currPos.getRow()][currPos.getColumn()].setIdentifier('x');
            }
            updateScreen();
            System.out.print("Posizione iniziale lavoratore 1 (riga, colonna): ");
            pos = verifyPosition(availablePos);
            addPosition(pos, godName);
            updateScreen();
            list.add(pos);
            System.out.print("Posizione iniziale lavoratore 2 (riga, colonna): ");
            pos = verifyPosition(availablePos);
            addPosition(pos, godName);
            gameMap[pos.getRow()][pos.getColumn()].setPlayerInfo(godName);
            list.add(pos);
            socket.send(new FirstPositioningInstr(list));
            for (Position currPos : availablePos) {
                gameMap[currPos.getRow()][currPos.getColumn()].setIdentifier(' ');
            }
        } else {
            for(Position currPos: availablePos) {
                addPosition(currPos, godName);
            }
        }
        updateScreen();
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

    @Override
    public void setName(List<String> stringList) {
        System.out.print("Imposta il nome: ");
        socket.send(new SetNameInstr(verifyName(stringList)));
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
    public void chooseWorker(List<Position> availableWorkers) {
        for(Position pos: availableWorkers) {
            gameMap[pos.getRow()][pos.getColumn()].setIdentifier('x');
        }
        updateScreen();
        System.out.print("Seleziona il worker: ");
        Position chosen = verifyPosition(availableWorkers);
        for(Position pos: availableWorkers) {
            gameMap[pos.getRow()][pos.getColumn()].setIdentifier(' ');
        }
        socket.send(new ChooseWorkerInstr(chosen));
    }

    @Override
    public void choosePower() {
        System.out.print("Attivare il potere della carta? S/N ");
        socket.send(new SetPowerInstr(verifyBoolean()));
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

    @Override
    public void handleEndGame(String message) {
        System.out.println(message);
        socket.closeClient();
    }

    private void printLogo() {
        String santoriniLogo = "   _____ ___    _   ____________  ____  _____   ______\n" +
                "  / ___//   |  / | / /_  __/ __ \\/ __ \\/  _/ | / /  _/\n" +
                "  \\__ \\/ /| | /  |/ / / / / / / / /_/ // //  |/ // /  \n" +
                " ___/ / ___ |/ /|  / / / / /_/ / _, _// // /|  // /   \n" +
                "/____/_/  |_/_/ |_/ /_/  \\____/_/ |_/___/_/ |_/___/   \n" +
                "                                                      \n" +
                "Welcome to Santorini Board Game made by Alberto Ferrarin, Filiberto Castagna and Luigi di Loreto.\n";
        System.out.println(santoriniLogo);
    }
}
