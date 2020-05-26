package it.polimi.ingsw.view.Cli;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.controller.Client.SocketClient;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.ViewInterface;

import java.util.*;

/**
 * the CLI
 */
public class CLIHandler implements ViewInterface {
    private final SocketClient socketClient;
    private final Tile[][] gameMap;
    private final TileGetter tileGetter;
    private int clientID;
    private List<Position> posList;
    private List<Integer> intList = new ArrayList<>();
    private List<Integer> chosenCards;
    private final ScannerListener scannerListener;
    private List<Position> chosenPos;
    private int numPlayers;
    private String godName;

    /**
     * creates the CLI
     * @param socketClient the server socket
     */
    public CLIHandler(SocketClient socketClient) {
        this.socketClient = socketClient;
        gameMap = new Tile[5][5];
        tileGetter = new TileGetter();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                gameMap[i][j] = tileGetter.getTile(0, 0, false);
            }
        }
        printLogo();
        scannerListener = new ScannerListener(this);
        scannerListener.start();
        setName();
    }

    /**
     * lets the player set his name
     */
    @Override
    public void setName() {
        scannerListener.setRequest(Request.name);
        System.out.print("Set the name: ");
    }

    /**
     * verify that the name is valid (non empty)
     * @param name the input
     */
    public void verifyName(String name) {
        if(name.length() > 0) {
            socketClient.send(new SetNameNotification(name, clientID));
            scannerListener.setRequest(Request.ignore);
        } else {
            System.out.print("Name " + name + " not valid, choose another one: ");
            scannerListener.setRequest(Request.name);
        }
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

    /**
     * add the position of a worker in the map
     * @param pos the position of the worker
     * @param godName the name of the worker's god
     */
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

    /**
     * ask the player if he wants to load a saved game
     */
    @Override
    public void askForReloadState() {
        scannerListener.setRequest(Request.askReload);
        System.out.print("Do you want to use an existent state? Y/N ");
    }

    /**
     * controls the input of the player
     * @param answer the input
     */
    public void reloadStateAnswer(String answer) {
        if(answer.equals("y")) {
            socketClient.send(new AskForReloadStateNotification(true));
        } else if(answer.equals("n")) {
            socketClient.send(new AskForReloadStateNotification(false));
        } else {
            System.out.print("Y/N: ");
            scannerListener.setRequest(Request.askReload);
        }
    }

    /**
     * load a saved game
     * @param map contains all the workers and buildings positions and informations
     * @param godNames the name of all the gods in game
     */
    @Override
    public void reloadState(Cell[][] map, List<String> godNames, List<String> userNames) {
        HashMap<Integer, Position> workerPos = new HashMap<>();
        System.out.println("Loading game...");
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
            firstPositioning(myWorker, godNames.get(i), userNames.get(i), false);
        }
    }

    /**
     * lets the player select the position
     * @param list the list of all possible positions
     */
    @Override
    public void choosePosition(List<Position> list) {
        this.posList = list;
        for(Position pos: list) {
            gameMap[pos.getRow()][pos.getColumn()].setIdentifier('x');
        }
        updateScreen();
        scannerListener.setRequest(Request.position);
        System.out.print("Choose the position (row, column): ");
    }

    /**
     * verify and return the position entered by the player is available
     * @param pos the position chosen by the player
     */
    public void verifyPosition(Position pos) {
        for (Position curr : posList) {
            if (pos.getRow() == curr.getRow() && pos.getColumn() == curr.getColumn()) {
                for(Position currPos: posList) {
                    gameMap[currPos.getRow()][currPos.getColumn()].setIdentifier(' ');
                }
                socketClient.send(new ChoosePosNotification(pos));
                return;
            }
        }
        System.out.print("Not valid choice, choose another position: ");
        scannerListener.setRequest(Request.position);
    }

    /**
     * lets the player choose the card to play with
     * @param cardList the list of cards
     */
    @Override
    public void chooseCard(List<Integer> cardList) {
        intList = cardList;
        List<Card> cards = new IOHandler().getCardList();
        System.out.println("\nChoose a card\n");
        for(Integer curr: cardList) {
            System.out.println(curr + ": " + cards.get(curr).getName());
            System.out.println(cards.get(curr).getDescription() + "\n");
        }
        scannerListener.setRequest(Request.card);
    }

    /**
     * verify and return the index of the card chosen by the player
     * @param selected the selected card
     */
    public void verifyCard(int selected) {
        for (Integer i : intList) {
            if (i == selected) {
                socketClient.send(new ChooseCardNotification(selected));
                intList.clear();
                return;
            }
        }
        System.out.print("Not valid card, choose another one: ");
        scannerListener.setRequest(Request.card);
    }

    /**
     * lets the player choose all the cards that will be in the game
     * @param num the number of cards that the player have to choose
     */
    @Override
    public void chooseCardList(int num) {
        numPlayers = num;
        chosenCards = new ArrayList<>();
        List<Card> cards = new IOHandler().getCardList();
        System.out.println("\nYou are the first player, choose " + num + " cards\n");
        for(int i = 0; i < cards.size(); i++) {
            intList.add(i);
            System.out.println(i + ": " + cards.get(i).getName() + "\n" + cards.get(i).getDescription() + "\n");
        }
        System.out.print("Card " + 1 + ": ");
        scannerListener.setRequest(Request.cardList);
    }

    /**
     * verify that the selected card is allowed
     * @param card the chosen card
     */
    public void verifyCardList(int card) {
        if(card < new IOHandler().getCardList().size()) {
            chosenCards.add(card);
            if(chosenCards.size() == numPlayers) {
                socketClient.send(new ChooseCardListNotification(chosenCards));
                intList.clear();
                return;
            }
            System.out.print("Card " + (chosenCards.size() + 1) + ": ");
        } else {
            System.out.println("Not valid choice");
        }
        scannerListener.setRequest(Request.cardList);
    }

    /**
     * lets the player set the position of his workers
     * @param availablePos the list of all the available positions
     * @param godName the name of the chosen god
     * @param isMyTurn indicates if is the turn of the player
     */
    @Override
    public void firstPositioning(List<Position> availablePos, String godName, String userName, boolean isMyTurn) {
        this.godName = godName;
        posList = availablePos;
        if(isMyTurn) {
            chosenPos = new ArrayList<>();
            for (Position currPos : availablePos) {
                gameMap[currPos.getRow()][currPos.getColumn()].setIdentifier('x');
            }
            updateScreen();
            System.out.print("Initial position player 1 (row, column): ");
            scannerListener.setRequest(Request.firstPos);
        } else {
            for(Position currPos: availablePos) {
                addPosition(currPos, godName);
            }
            updateScreen();
        }
    }

    /**
     * verify if the position chosen is valid
     * @param pos the chosen position
     */
    public void verifyFirstPos(Position pos) {
        System.out.println(pos);
        if(notAvailablePos(pos)) {
            System.out.println("Position not available");
            scannerListener.setRequest(Request.firstPos);
            return;
        }
        chosenPos.add(pos);
        addPosition(pos, godName);
        updateScreen();
        if(chosenPos.size() == 2) {
            for (Position currPos : posList) {
                gameMap[currPos.getRow()][currPos.getColumn()].setIdentifier(' ');
            }
            socketClient.send(new FirstPositioningNotification(chosenPos));
            return;
        }
        System.out.print("Initial positionplayer 2 (row, column): ");
        scannerListener.setRequest(Request.firstPos);
    }

    /**
     * verify if the chosen position is valid
     * @param pos the position
     * @return true if the position is NOT valid
     */
    private boolean notAvailablePos(Position pos) {
        for (Position curr : posList) {
            if (pos.getRow() == curr.getRow() && pos.getColumn() == curr.getColumn()) {
                return false;
            }
        }
        return true;
    }

    /**
     * lets the player choose which worker play with
     * @param availableWorkers the list of all his workers
     */
    @Override
    public void chooseWorker(List<Position> availableWorkers) {
        posList = availableWorkers;
        for(Position pos: availableWorkers) {
            gameMap[pos.getRow()][pos.getColumn()].setIdentifier('x');
        }
        updateScreen();
        System.out.print("Choose the worker: ");
        scannerListener.setRequest(Request.worker);
    }

    /**
     * verify that the chosen worker is valid
     * @param pos the position of the chosen worker
     */
    public void verifyWorker(Position pos) {
        if(notAvailablePos(pos)) {
            System.out.println("Choose a valid worker");
            scannerListener.setRequest(Request.worker);
            return;
        }
        for(Position currPos: posList) {
            gameMap[currPos.getRow()][currPos.getColumn()].setIdentifier(' ');
        }
        socketClient.send(new ChooseWorkerNotification(pos));
    }

    /**
     * lets the player activate the power of his god
     */
    @Override
    public void choosePower() {
        System.out.print("Do you want to activate the god's power? Y/N ");
        scannerListener.setRequest(Request.power);
    }

    /**
     * verify the answer of the player
     * @param answer the answer
     */
    public void verifyPower(String answer) {
        if(answer.equals("y")) {
            socketClient.send(new SetPowerNotification(true));
        } else if(answer.equals("n")) {
            socketClient.send(new SetPowerNotification(false));
        } else {
            System.out.print("Y/N: ");
            scannerListener.setRequest(Request.power);
        }
    }

    /**
     * handle the disconnection
     * @param message the log of the disconnection
     */
    @Override
    public void handleDisconnection(String message) {
        System.out.println(message);
        socketClient.closeClient();
        scannerListener.stopReading();
    }

    /**
     * handle a message for other clients
     * @param message the message that the client has to show
     */
    @Override
    public void notificationForOtherClient(String message) {
        System.out.println(message);
    }

    /**
     * handle the elimination of a player
     * @param elim indicates if the client is the eliminated player
     * @param eliminatedPlayer the name of the eliminated player
     */
    @Override
    public void elimination(boolean elim, String eliminatedPlayer) {
        if(elim) {
            System.out.println("You lose");
        } else {
            System.out.println(eliminatedPlayer + " has lost");
        }
    }

    /**
     * handle the win
     * @param win indicates if the client is the winner
     * @param winnerName the winner's name
     */
    @Override
    public void win(boolean win, String winnerName) {
        if(win) {
            System.out.println("You won");
        } else {
            System.out.println(winnerName + " won");
        }
        socketClient.closeClient();
        scannerListener.stopReading();
    }

    /**
     * prints the logo of "Santorini"
     */
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
