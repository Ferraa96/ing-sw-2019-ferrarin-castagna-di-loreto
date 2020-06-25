package it.polimi.ingsw.model.Effects;

import it.polimi.ingsw.controller.Instructions.MessageInterface;
import it.polimi.ingsw.model.Game.Position;
import it.polimi.ingsw.model.Player.Worker;

import java.util.List;

/**
 * regroup 2 different types of effects: Move or Build
 */
public interface Effect {

    /**
     * search free cells around
     * @param target worker interested
     * @return list of available positions
     */
    List<Position> availableWithGod(Worker target);

    /**
     * do the action and update the map
     * @param chosenCell cell selected
     * @param worker target of action
     * @return message to send to view
     */
    MessageInterface executeAction(Position chosenCell, Worker worker);

    /**
     * set last move
     * @param lastMoveInitialPosition initial cell of last move
     */
    void setLastMoveInitialPosition(Position lastMoveInitialPosition);

    /**
     * used for athena
     * @param noUp true if athena triggers her power
     */
    void setNoUp(Boolean noUp);

    /**
     * auto move for forced targets
     * @param enemy worker forced by mino/apollo
     * @return message to send to view
     */
    MessageInterface executeAutoAction(Worker enemy, Position pos, Worker worker);

    /**
     * set last build
     * @param lastBuildPosition cell of last build
     */
    void setLastBuildPosition(Position lastBuildPosition);

    /**
     * @return the last position chosen
     */
    Position getLastPosition();

    /**
     * getter
     * @return height difference of the move
     */
    int getDownUp();
}
