package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Position;

import java.util.List;

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
     * move player to position
     * @param player the player to move
     * @param position the position to move the player to
     */
    void move(int player, Position position);

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
}