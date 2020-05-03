package it.polimi.ingsw.model;

import java.util.List;

/**
 * public methods of the model
 */
public interface ModelInterface {

    void startGame();

    /**
     * set player name
     * @param name the name chosen
     */
    void setPlayerName(String name);

    /**
     * called by all the players to set their card
     * @param chosen the index of the card chosen
     */
    void setCards(int chosen);

    /**
     * called by the first player, he has chosen all the cards in the game
     * @param cards the list of cards
     */
    void setInitialCards(List<Integer> cards);

    /**
     * called by all the players when they choose the position of their workers
     * @param list the list of positions of worker1 and worker2 of the actual player
     */
    void setWorkersPosition(List<Position> list);

    /**
     * select the correct worker
     * @param chosenWorker the worker chosen by the player
     */
    void selectCorrectWorker(Position chosenWorker);

    /**
     * send player available positions
     * @param use true if player active hero power
     */
    void providePosition(boolean use);

    /**
     * apply effect of current action
     * @param pos position chosen by player
     */
    void apply(Position pos);

    /**
     * lets the players resume a saved game
     * @param reload true if player 1 choose to load the game
     */
    void loadState(boolean reload);

    void handleDisconnection(int clientDisconnected);
}
