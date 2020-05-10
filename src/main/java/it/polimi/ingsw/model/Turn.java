package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Server.LobbyHandler;
import it.polimi.ingsw.controller.Instructions.*;

import java.util.*;

/**
 * handle all the turns
 */
public class Turn implements ModelInterface {
    private final int numPlayer;
    private int actualPlayer = 0;
    private final LobbyHandler socket;
    private final List<Card> cardList;
    private final Board board;
    private final IOHandler ioHandler = new IOHandler();
    private SaveState saveState;
    private int totalEffects;
    private int currEffect = 0;
    private Worker currWorker;
    private boolean powerUsed;
    private boolean stopGame = false;
    private final List<PlayerInfo> players;
    private final List<String> nameList;
    private List<Integer> chosenCards;

    public Turn(LobbyHandler socket, int numPlayer) {
        this.socket = socket;
        this.numPlayer = numPlayer;
        saveState = new SaveState();
        cardList = new ArrayList<>();
        board = new Board();
        players = new ArrayList<>();
        nameList = new ArrayList<>();
    }

    @Override
    public void startGame() {
        socket.sendTo(actualPlayer, new SetNameInstr(nameList));
    }

    /**
     * set player name
     * @param name the chosen name
     */
    @Override
    public void setPlayerName(String name) {
        players.add(new PlayerInfo(name));
        nextTurn();
        if(actualPlayer == 0) {
            for(PlayerInfo curr: players) {
                nameList.add(curr.getPlayerName());
            }
            if(ioHandler.verifyFileExistance(saveState.generateName(nameList))) {
                socket.sendTo(actualPlayer, new AskForReloadStateInstr());
                return;
            }
            //initial cards choose
            socket.sendTo(actualPlayer, new ChooseCardListInstr(numPlayer));
        } else {
            socket.sendTo(actualPlayer, new SetNameInstr(nameList));
        }
    }

    /**
     * lets the players resume a saved game
     * @param reload true if player 1 choose to load the game
     */
    @Override
    public void loadState(boolean reload) {
        if(reload) {
            saveState = ioHandler.getSaveState();
            board.setMap(saveState.getGameMap());
            for(PlayerInfo currPlayer: saveState.getPlayers()) {
                addCardToGame(currPlayer.getChosenCard());
                cardList.get(actualPlayer).firstPositioning(currPlayer.getWorkerPos().get(0), currPlayer.getWorkerPos().get(1));
                nextTurn();
            }
            setEnemiesLists();
            LoadGameInstr oldState = new LoadGameInstr(board.getMap());
            socket.sendTo(0, oldState);
            socket.sendToAllExcept(0, oldState);
            socket.sortPlayers(mapPlayers(saveState));
            actualPlayer = saveState.getActualPlayer();
            if(saveState.getActualEffect() == 0) {
                System.out.println("Start turn player" + " " + actualPlayer);
                askWhichWorker();
            } else {
                if(saveState.getChosenWorker() % 2 == 0) {
                    currWorker = cardList.get(actualPlayer).getWorker1();
                } else {
                    currWorker = cardList.get(actualPlayer).getWorker2();
                }
                currEffect = saveState.getActualEffect();
                providePosition(saveState.isGodPower());
            }
        } else {
            //initial cards choose
            socket.sendTo(actualPlayer, new ChooseCardListInstr(numPlayer));
        }
    }

    private void askWhichWorker() {
        List<Position> availableWorkers = new ArrayList<>();
        availableWorkers.add(cardList.get(actualPlayer).getWorker1().getPosition());
        availableWorkers.add(cardList.get(actualPlayer).getWorker2().getPosition());
        socket.sendTo(actualPlayer, new ChooseWorkerInstr(availableWorkers));
    }

    /**
     * puts all the players in the same order of the game loaded
     * @param state the file with all the configurations saved
     * @return maps the actual players order to the saved game order
     */
    private Map<Integer, Integer> mapPlayers(SaveState state) {
        Map<Integer, Integer> playerMap = new HashMap<>();
        for(int i = 0; i < numPlayer; i++) {
            for(int j = 0; j < numPlayer; j++) {
                if(players.get(i).getPlayerName().equals(state.getPlayers().get(j).getPlayerName())) {
                    playerMap.put(j, i);
                    break;
                }
            }
        }
        return playerMap;
    }

    /**
     * called by the first player, he has chosen all the cards in the game
     * @param cards the list of cards
     */
    @Override
    public void setInitialCards(List<Integer> cards) {
        nextTurn();
        chosenCards = cards;
        socket.sendTo(actualPlayer, new ChooseCardInstr(cards));
    }

    /**
     * called by all the players to set their card
     * @param chosenCard the index of the card chosen
     */
    @Override
    public void setCards(int chosenCard) {
        addCardToGame(chosenCard);
        chosenCards.remove(Integer.valueOf(chosenCard));
        if(chosenCards.size() != 0) {
            nextTurn();
            socket.sendTo(actualPlayer, new ChooseCardInstr(chosenCards));
        } else {
            Card temp = cardList.get(numPlayer - 1);
            cardList.remove(temp);
            cardList.add(0, temp);
            //first positioning
            saveState.setPlayers(players);
            socket.sendTo(actualPlayer, new FirstPositioningInstr(cardList.get(actualPlayer).availableFirstPositioning()));
        }
    }

    /**
     * instanciate the card chosen
     * @param chosenCard the index of the card chosen
     */
    private void addCardToGame(int chosenCard) {
        players.get(actualPlayer).setChosenCard(chosenCard);
        Card card = ioHandler.getCardList().get(chosenCard);
        card.setCard(board.getMap(), actualPlayer);
        cardList.add(card);
    }

    /**
     * called by all the players when they choose the position of their workers
     * @param positions the list of positions of worker1 and worker2 of the actual player
     */
    @Override
    public void setWorkersPosition(List<Position> positions) {
        List<Movement> movements = new ArrayList<>();
        Position w1 = positions.get(0);
        Position w2 = positions.get(1);
        cardList.get(actualPlayer).firstPositioning(w1, w2);
        movements.add(new Movement(null, w1));
        movements.add(new Movement(null, w2));
        socket.sendToAllExcept(actualPlayer, new MoveInstr(movements));
        nextTurn();
        if(actualPlayer == 0) {
            setEnemiesLists();
            System.out.println("Start turn player " + actualPlayer);
            List<Position> availableWorkers = new ArrayList<>();
            availableWorkers.add(cardList.get(actualPlayer).getWorker1().getPosition());
            availableWorkers.add(cardList.get(actualPlayer).getWorker2().getPosition());
            socket.sendTo(actualPlayer, new ChooseWorkerInstr(availableWorkers));
        } else {
            //first positioning
            saveState.setPlayers(players);
            socket.sendTo(actualPlayer, new FirstPositioningInstr(cardList.get(actualPlayer).availableFirstPositioning()));
        }
    }

    /**
     * set enemies list of every player
     */
    private void setEnemiesLists() {
        for (int i = 0; i < cardList.size() ; i++) {
            cardList.get(i).setEnemies(cardList);
        }
    }

    /**
     * select the worker to play the turn
     * @param chosenWorker the worker chosen by the player
     */
    @Override
    public void selectCorrectWorker(Position chosenWorker) {
        if(chosenWorker.equals(cardList.get(actualPlayer).getWorker1().getPosition())) {
            verifyPower(cardList.get(actualPlayer).getWorker1());
        } else {
            verifyPower(cardList.get(actualPlayer).getWorker2());
        }
    }

    /**
     * verify if hero power is available
     * @param worker selected worker by player
     */
    private void verifyPower(Worker worker) {
        currWorker = worker;
        if(cardList.get(actualPlayer).checkCardActivation(worker)) {
            socket.sendTo(actualPlayer, new SetPowerInstr());
        } else {
            providePosition(false);
        }
    }

    /**
     * send player available positions
     * @param use true if player active hero power
     */
    @Override
    public void providePosition(boolean use) {
        if (currEffect == 0) {
            powerUsed = use;
            cardList.get(actualPlayer).setActivePower(use);
            if (powerUsed) {
                totalEffects = cardList.get(actualPlayer).getCardRoutine().size();
            } else {
                totalEffects = cardList.get(actualPlayer).getStandardRoutine().size();
            }
        }
        socket.sendTo(actualPlayer, new ChoosePosInstr(cardList.get(actualPlayer).availablePositions(currEffect, currWorker)));
    }

    /**
     * apply effect of current action
     * @param pos position chosen by player
     */
    @Override
    public void apply(Position pos) {
        MessageInterface command = cardList.get(actualPlayer).applyEffect(currEffect, currWorker, pos);
        socket.sendTo(actualPlayer, command);
        socket.sendToAllExcept(actualPlayer, command);
        stopGame = cardList.get(actualPlayer).checkWin(currEffect, pos);
        showWin();
        if (!stopGame) {
            if (currEffect < totalEffects - 1) {
                currEffect++;
                providePosition(powerUsed);
            } else {
                currEffect = 0;
                nextTurn();
                System.out.println("Start turn player" + " " + actualPlayer);
                askWhichWorker();
            }
        }
        saveGame();
    }

    /**
     * show the game result to all the players
     */
    private void showWin() {
        if(stopGame) {
            HandleEndGameInstr gameEnd = new HandleEndGameInstr();
            gameEnd.setMessage("Hai vinto");
            socket.sendTo(actualPlayer, gameEnd);
            gameEnd.setMessage("Ha vinto il giocatore " + nameList.get(actualPlayer));
            socket.sendToAllExcept(actualPlayer, gameEnd);
            ioHandler.deleteFile();
        }
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

    /**
     * save the game to a file
     */
    private void saveGame() {
        List<Position> positions;
        for(int i = 0; i < players.size(); i++) {
            positions = new ArrayList<>();
            positions.add(cardList.get(i).getWorker1().getPosition());
            positions.add(cardList.get(i).getWorker2().getPosition());
            players.get(i).setWorkerPos(positions);
        }
        saveState.setActualPlayer(actualPlayer);
        saveState.setGameMap(board.getMap());
        saveState.setActualEffect(currEffect);
        saveState.setChosenWorker(currWorker.getWorkerID());
        saveState.setGodPower(powerUsed);
        ioHandler.save(saveState);
    }

    public void handleDisconnection(int deadClient) {
        System.out.println("Client " + deadClient + " disconnected");
        HandleEndGameInstr gameEnd = new HandleEndGameInstr();
        gameEnd.setMessage("Il giocatore " + deadClient + " si è disconnesso, la partita è annullata");
        socket.closeServer(gameEnd);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for(Card card: cardList) {
            string.append(card.getPlayerId()).append(": ").append(card.getName()).append("\n");
            string.append("\tWorker ").append(card.getWorker1().getWorkerID()).append(": ").append(card.getWorker1().getPosition()).append("\n");
            string.append("\tWorker ").append(card.getWorker2().getWorkerID()).append(": ").append(card.getWorker2().getPosition()).append("\n");
        }
        string.append("Actual player: ").append(actualPlayer).append("\n");
        string.append(board).append("\n");
        return string.toString();
    }
}