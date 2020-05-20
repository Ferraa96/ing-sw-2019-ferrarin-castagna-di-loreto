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
    private final List<PlayerInfo> players;
    private final Map<Integer, String> nameMap;
    private final List<String> nameList;
    private List<Integer> chosenCards;
    private final List<Integer> eliminatedPlayers;

    public Turn(LobbyHandler socket, int numPlayer) {
        this.socket = socket;
        this.numPlayer = numPlayer;
        saveState = new SaveState();
        cardList = new ArrayList<>();
        board = new Board();
        players = new ArrayList<>();
        nameList = new ArrayList<>();
        eliminatedPlayers = new ArrayList<>();
        nameMap = new HashMap<>();
    }

    /**
     * set player name
     * @param name the chosen name
     */
    @Override
    public synchronized void setPlayerName(String name, int playerID) {
        if(controlName(name)) {
            socket.sendTo(playerID, new SetNameNotification(playerID, true));
            nameMap.put(playerID, name);
            nextTurn();
            if (nameMap.size() == numPlayer) {
                for(int i = 0; i < numPlayer; i++) {
                    players.add(new PlayerInfo(nameMap.get(i)));
                }
                for (PlayerInfo curr : players) {
                    nameList.add(curr.getPlayerName());
                }
                if (ioHandler.verifyFileExistance(saveState.generateName(nameList))) {
                    socket.broadcast(new AskForReloadStateNotification(actualPlayer));
                    return;
                }
                //initial cards choose
                socket.broadcast(new ChooseCardListNotification(numPlayer, actualPlayer));
            }
        }
        else {
            socket.sendTo(playerID, new SetNameNotification(playerID, false));
        }
    }

    private boolean controlName(String name) {
        for(String curr: nameMap.values()) {
            if (curr.equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * lets the players resume a saved game
     * @param reload true if player 1 choose to load the game
     */
    @Override
    public void loadState(boolean reload) {
        if(reload) {
            List<String> godNames = new ArrayList<>();
            saveState = ioHandler.getSaveState();
            board.setMap(saveState.getGameMap());
            for(PlayerInfo currPlayer: saveState.getPlayers()) {
                godNames.add(ioHandler.getCardList().get(currPlayer.getChosenCard()).getName());
                addCardToGame(currPlayer.getChosenCard());
                cardList.get(actualPlayer).firstPositioning(currPlayer.getWorkerPos().get(0), currPlayer.getWorkerPos().get(1));
                nextTurn();
            }
            setEnemiesLists();
            LoadGameNotification oldState = new LoadGameNotification(board.getMap());
            oldState.setGodNames(godNames);
            socket.broadcast(oldState);
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
            socket.broadcast(new ChooseCardListNotification(numPlayer, actualPlayer));
        }
    }

    private void askWhichWorker() {
        List<Position> availableWorkers = new ArrayList<>();
        availableWorkers.add(cardList.get(actualPlayer).getWorker1().getPosition());
        availableWorkers.add(cardList.get(actualPlayer).getWorker2().getPosition());
        socket.broadcast(new ChooseWorkerNotification(availableWorkers, actualPlayer));
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
        socket.broadcast(new ChooseCardNotification(cards, actualPlayer));
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
            socket.broadcast(new ChooseCardNotification(chosenCards, actualPlayer));
        } else {
            Card temp = cardList.get(numPlayer - 1);
            cardList.remove(temp);
            cardList.add(0, temp);
            //first positioning
            saveState.setPlayers(players);
            String godName = cardList.get(actualPlayer).getName();
            socket.broadcast(new FirstPositioningNotification(cardList.get(actualPlayer).availableFirstPositioning(), godName, actualPlayer));
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
        String godName = cardList.get(actualPlayer).getName();
        Position w1 = positions.get(0);
        Position w2 = positions.get(1);
        cardList.get(actualPlayer).firstPositioning(w1, w2);
        FirstPositioningNotification fpn = new FirstPositioningNotification(positions, godName, actualPlayer);
        fpn.setLoadPos(true);
        socket.broadcast(fpn);
        nextTurn();
        if(actualPlayer == 0) {
            setEnemiesLists();
            System.out.println("Start turn player " + actualPlayer);
            List<Position> availableWorkers = new ArrayList<>();
            availableWorkers.add(cardList.get(actualPlayer).getWorker1().getPosition());
            availableWorkers.add(cardList.get(actualPlayer).getWorker2().getPosition());
            socket.broadcast(new ChooseWorkerNotification(availableWorkers, actualPlayer));
        } else {
            //first positioning
            saveState.setPlayers(players);
            godName = cardList.get(actualPlayer).getName();
            socket.broadcast(new FirstPositioningNotification(cardList.get(actualPlayer).availableFirstPositioning(), godName, actualPlayer));
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
            socket.broadcast(new SetPowerNotification(actualPlayer));
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
        List<Position> availablePos = cardList.get(actualPlayer).availablePositions(currEffect, currWorker);
        if(availablePos.isEmpty()) {
            playerElimination();
        } else {
            socket.broadcast(new ChoosePosNotification(availablePos, actualPlayer));
        }
    }

    private void playerElimination() {
        System.out.println("Player " + actualPlayer + " eliminated");
        eliminatedPlayers.add(actualPlayer);
        socket.broadcast(new EliminationNotification(actualPlayer, nameList.get(actualPlayer)));
        if(eliminatedPlayers.size() == numPlayer - 1) {
            for(int i = 0; i < numPlayer; i++) {
                if(!eliminatedPlayers.contains(i)) {
                    handleWin(i);
                    break;
                }
            }
        } else {
            nextTurn();
            List<Position> availableWorkers = new ArrayList<>();
            availableWorkers.add(cardList.get(actualPlayer).getWorker1().getPosition());
            availableWorkers.add(cardList.get(actualPlayer).getWorker2().getPosition());
            socket.broadcast(new ChooseWorkerNotification(availableWorkers, actualPlayer));
        }
    }

    /**
     * apply effect of current action
     * @param pos position chosen by player
     */
    @Override
    public void apply(Position pos) {
        MessageInterface command = cardList.get(actualPlayer).applyEffect(currEffect, currWorker, pos);
        socket.broadcast(command);
        if(cardList.get(actualPlayer).checkWin(currEffect, pos)) {
            handleWin(actualPlayer);
        } else  {
            if (currEffect < totalEffects - 1) {
                currEffect++;
                providePosition(powerUsed);
            } else {
                currEffect = 0;
                nextTurn();
                askWhichWorker();
            }
            saveGame();
        }
    }

    /**
     * handle the end of the game due to a win
     */
    private void handleWin(int winner) {
        System.out.println("Player " + winner + " wins");
        socket.broadcast(new WinNotification(winner, nameList.get(winner)));
        ioHandler.deleteFile();
        socket.closeServer();
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
        if(eliminatedPlayers.contains(actualPlayer)) {
            nextTurn();
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

    /**
     * handle the disconnection of a player. If the player was eliminated before he left the game, the game continues
     * @param deadClient the id of the disconnected player
     */
    @Override
    public void handleDisconnection(int deadClient) {
        System.out.println("Client " + deadClient + " disconnected");
        socket.removeObserver(deadClient);
        if(!eliminatedPlayers.contains(deadClient)) {
            DisconnectionNotification gameEnd = new DisconnectionNotification();
            gameEnd.setClientID(deadClient);
            socket.broadcast(gameEnd);
            socket.closeServer();
        }
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