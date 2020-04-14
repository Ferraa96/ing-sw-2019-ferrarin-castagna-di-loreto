package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.SaveState;

import java.util.List;
import java.util.Map;

/**
 * handle the view
 */
public interface ViewInterface {

    /**
     * set the player ID
     * @param playerID the player ID
     */
    void setPlayerID(int playerID);

    /**
     * ask for name
     * @param stringList list to save names
     */
    void setName(List<String> stringList);

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
    void firstPositioning(List<Position> availablePos);

    /**
     * resume the game state
     */
    void resumeGame(Cell[][] cells);

    /**
     * allows player to select the worker to use in current turn
     */
    void chooseWorker();

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
     * @param movement workerId and position of the movement
     */
    void move(Map<Integer,Position> movement);

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
    void reloadState(Cell[][] map);
}