package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Movement;
import it.polimi.ingsw.model.Position;

import java.util.List;

/**
 * handle the view
 */
public interface ViewInterface {

    /**
     * ask for name
     */
    void setName();

    /**
     * lets the player choose a card from a list
     * @param cardList the list of cards
     */
    void chooseCard(List<Integer> cardList);

    /**
     * lets the first player choose all the cards that will be in the game
     * @param num the number of cards that the player can choose
     */
    void chooseCardList(int num);

    /**
     * lets the player choose the initial position of his workers
     * @param availablePos the list of all the available positions
     */
    void firstPositioning(List<Position> availablePos, String godName, String userName, boolean isMyTurn);

    /**
     * allows player to select the worker to use in current turn
     */
    void chooseWorker(List<Position> availableWorkers);

    /**
     * allows player to choose for power activation
     */
    void choosePower();

    /**
     * allows player to choose cell for move/build
     * @param list list of available positions
     */
    void choosePosition(List<Position> list);

    /**
     * move player to position
     * @param movements workerId and position of the movement
     */
    void move(List<Movement> movements);

    /**
     * build a block in position
     * @param position the position to build the block in
     * @param height the height of the block
     */
    void buildBlock(Position position, int height);

    /**
     * build a dome in position
     * @param position the position to build the dome in
     * @param height the height of the dome
     */
    void buildDome(Position position, int height);

    /**
     * update the screen
     */
    void updateScreen();

    /**
     * ask the player if he wants to load a saved game
     */
    void askForReloadState();

    /**
     * loads the saved game
     * @param map contains all the workers and buildings positions and informations
     */
    void reloadState(Cell[][] map, List<String> godNames, List<String> userNames);

    void handleDisconnection(String message);

    void notificationForOtherClient(String message);

    void elimination(boolean elim, String eliminatedPlayer);

    void win(boolean win, String winnerName);
}