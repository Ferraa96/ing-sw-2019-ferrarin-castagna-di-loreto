package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.Instruction;
import it.polimi.ingsw.controller.SocketServer;

import java.util.*;

/**
 * handle all the turns
 */
public class Turn implements ModelInterface {
    private int numPlayer;
    private int actualPlayer = 0;
    private SocketServer socket;
    private List<Card> cardList;
    private Commands commands;
    private Board board;
    private IOHandler ioHandler = new IOHandler();
    private SaveState saveState;
    private int totalEffects;
    private int currEffect = 0;
    private Worker currWorker;
    private boolean powerUsed;
    private boolean stopGame = false;
    private List<PlayerInfo> players;

    public Turn(SocketServer socket, int numPlayer) {
        this.socket = socket;
        this.numPlayer = numPlayer;
        saveState = new SaveState();
        cardList = new ArrayList<>();
        commands = new Commands();
        board = new Board();
        players = new ArrayList<>();
        askName();
    }

    /**
     * ask for player name
     */
    private void askName() {
        commands.setInstruction(Instruction.setName);
        socket.sendTo(actualPlayer, commands);
    }

    /**
     * set player name
     * @param playerID id of current player
     * @param nameList save all player's names
     */
    public void setPlayerName(int playerID, List<String> nameList) {
        players.add(new PlayerInfo(nameList.get(0)));
        nextTurn();
        if(actualPlayer == 0) {
            nameList.clear();
            for(PlayerInfo curr: players) {
                nameList.add(curr.getPlayerName());
            }
            if(ioHandler.verifyFileExistance(saveState.generateName(nameList))) {
                commands.setInstruction(Instruction.askReloadState);
                socket.sendTo(actualPlayer, commands);
                return;
            }
            initialChoose();
        } else {
            commands.getStringList().add(nameList.get(0));
            askName();
        }
    }

    /**
     * lets the players resume a saved game
     * @param reload true if player 1 choose to load the game
     */
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
            commands.setInstruction(Instruction.reloadState);
            commands.setMap(board.getMap());
            socket.sendTo(0, commands);
            socket.sendToAllExcept(0, commands);
            socket.sortPlayers(mapPlayers(saveState));
            actualPlayer = saveState.getActualPlayer();
            if(saveState.getActualEffect() == 0) {
                game();
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
            initialChoose();
        }
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
     * called by the first player, he has chosen all the cards in the game
     * @param cards the list of cards
     */
    @Override
    public void setInitialCards(List<Integer> cards) {
        nextTurn();
        commands.setCardList(cards);
        socket.sendTo(actualPlayer, commands);
    }

    /**
     * called by all the players to set their card
     * @param chosenCard the index of the card chosen
     */
    @Override
    public void setCards(int chosenCard) {
        addCardToGame(chosenCard);
        commands.removeCard(chosenCard);
        if(!commands.getCardList().isEmpty()) {
            nextTurn();
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
     * instanciate the card chosen
     * @param chosenCard the index of the card chosen
     */
    private void addCardToGame(int chosenCard) {
        players.get(actualPlayer).setChosenCard(chosenCard);
        Card card = ioHandler.getSelectedCardFlags().get(chosenCard);
        card.setCard(board.getMap(), actualPlayer);
        cardList.add(card);
    }

    /**
     * it send to actualPlayer the list of all the positions available for the first positioning
     */
    private void firstPositioning() {
        saveState.setPlayers(players);
        commands.setInstruction(Instruction.initialPosition);
        commands.setAvailablePos(cardList.get(actualPlayer).availableFirstPositioning());
        commands.setPlayer(actualPlayer);
        socket.sendTo(actualPlayer, commands);
    }

    /**
     * called by all the players when they choose the position of their workers
     * @param positions the list of positions of worker1 and worker2 of the actual player
     */
    @Override
    public void setWorkersPosition(List<Position> positions) {
        Position w1 = positions.get(0);
        Position w2 = positions.get(1);
        cardList.get(actualPlayer).firstPositioning(w1, w2);
        commands.setInstruction(Instruction.move);
        commands.getMovement().clear();
        commands.getMovement().put(cardList.get(actualPlayer).getWorker1().getWorkerID(), w1);
        commands.getMovement().put(cardList.get(actualPlayer).getWorker2().getWorkerID(), w2);
        commands.setPlayer(actualPlayer);
        socket.sendToAllExcept(actualPlayer, commands);
        nextTurn();
        if(actualPlayer == 0) {
            setEnemiesLists();
            game();
        } else {
            firstPositioning();
        }
    }

    /**
     * set enemies list of every player
     */
    private void setEnemiesLists() {
        for (int i = 0; i < cardList.size() ; i++)
            cardList.get(i).setEnemies(cardList);
    }

    /**
     * handle all the turns after the setup of the game
     */
    private void game() {
        System.out.println("Start turn player" + " " + actualPlayer);
        commands.setInstruction(Instruction.chooseWorker);
        commands.setPlayer(actualPlayer);
        socket.sendTo(actualPlayer, commands);
    }

    /**
     * select the worker to play the turn
     * @param pos position of one of the two player's workers
     */
    @Override
    public void selectCorrectWorker(Position pos) {
        if(cardList.get(actualPlayer).getWorker1().getPosition().equals(pos)) {
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
            commands.setInstruction(Instruction.usePower);
            socket.sendTo(actualPlayer, commands);
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
        commands.setInstruction(Instruction.choosePosition);
        commands.setAvailablePos(cardList.get(actualPlayer).availablePositions(currEffect, currWorker));
        socket.sendTo(actualPlayer, commands);
        System.out.println("Available positions:");
        for(Position pos: commands.getAvailablePos()) {
            System.out.println(pos.getRow() + " " + pos.getColumn());
        }
    }

    /**
     * apply effect of current action
     * @param pos position chosen by player
     */
    @Override
    public void apply(Position pos) {
        commands.getMovement().clear();
        commands = cardList.get(actualPlayer).applyEffect(currEffect, currWorker, pos);
        socket.sendTo(actualPlayer,commands);
        socket.sendToAllExcept(actualPlayer, commands);
        if (commands.getInstruction() == Instruction.move)
            stopGame = cardList.get(actualPlayer).checkWin(currEffect, pos);
        showWin();
        if (!stopGame) {
            if (currEffect < totalEffects - 1) {
                currEffect++;
                providePosition(powerUsed);
            } else {
                currEffect = 0;
                nextTurn();
                game();
            }
        }
        //else blocca tutto
        saveGame();
    }

    /**
     * ce fa vede se amo vinto
     */
    private void showWin() {
        if (!stopGame)
            System.out.println("Avanti il prossimo");
        else
            System.out.println("Colazione finita! GG");
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

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for(Card card: cardList) {
            string.append(card.getPlayerId()).append(": ").append(card.getName()).append("\n");
            string.append("\t").append(card.getWorker1().getWorkerID()).append(": ").append(card.getWorker1().getPosition()).append("\n");
            string.append("\t").append(card.getWorker2().getWorkerID()).append(": ").append(card.getWorker2().getPosition()).append("\n");
        }
        string.append("Actual player: ").append(actualPlayer).append("\n");
        string.append(board).append("\n");
        return string.toString();
    }
}