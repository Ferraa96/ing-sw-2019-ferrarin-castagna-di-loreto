package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Game.Cell;
import it.polimi.ingsw.model.Effects.Movement;
import it.polimi.ingsw.model.Game.Position;

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
     * @param godNames the names of all the gods in game
     * @param userNames the names of all the players
     * @param client the player who have to do the first positioning
     * @param isMyTurn indicates if the player have to set the position of his worker or if he has to load the position of other player's workers
     */
    void firstPositioning(List<Position> availablePos, List<String> godNames, List<String> userNames, int client, boolean isMyTurn);

    /**
     * allows player to select the worker to use in current turn
     * @param availableWorkers the positions of all the available workers
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
     * @param godNames the names of all the gods in game
     * @param userNames the names of all the players
     * @param clientId the client ID
     */
    void reloadState(Cell[][] map, List<String> godNames, List<String> userNames, int clientId);

    void handleDisconnection(String message);

    void notificationForOtherClient(String message);

    void elimination(boolean elim, String eliminatedPlayer, List<Position> eliminatedWorkers);

    void win(boolean win, String winnerName);
}