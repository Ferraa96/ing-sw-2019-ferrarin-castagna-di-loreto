package it.polimi.ingsw.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * card/player contains card informations, list of effects and it's linked to his 2 workers
 */
public class Card {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;

    @SerializedName("move_1")
    private Boolean move_1;
    @SerializedName("search_people_1")
    private Boolean search_people_1;
    @SerializedName("knock_1")
    private Boolean knock_1;
    @SerializedName("move_2")
    private Boolean move_2;
    @SerializedName("specific_2")
    private Boolean specific_2;
    @SerializedName("dome_2")
    private Boolean dome_2;
    @SerializedName("action_3")
    private Boolean action_3;
    @SerializedName("not_before_3")
    private Boolean not_before_3;

    private Cell[][] map;
    private int playerId;
    private Worker worker1;
    private Worker worker2;
    private List<Effect> cardRoutine;
    private List<Effect> standardRoutine;
    private List<Card> enemies;
    private Worker enemy;
    private boolean activePower;

    /**
     * check for current player win
     * @param difference height difference between final and starting block
     * @param finalPosition final position
     * @return true if you satisfy a win condition
     */
    private Boolean checkWin(int difference, Position finalPosition) {
        if (difference == 1 && map[finalPosition.getRow()][finalPosition.getColumn()].getHeight() == 3)
            return true;
        return this.name.equals("Pan") && difference <= -2;
    }

    /**
     * gives the enemy corresponding input id
     * @param id worker's id of the enemy you want search
     * @return correct enemy
     */
    private Worker getCorrectEnemy(int id) {
        for (int i = 0; i < enemies.size() ; i++) {
            if (enemies.get(i).getWorker1().getWorkerID()==id) {
                enemy = enemies.get(i).getWorker1();
                i = enemies.size();
            }
            else if (enemies.get(i).getWorker2().getWorkerID()==id) {
                enemy = enemies.get(i).getWorker2();
                i = enemies.size();
            }
        }
        return enemy;
    }

    /**
     * set parameters for actual/next effect
     * @param i number of action in the routine
     * @param chosenCell cell chosen by player
     * @param target interested worker
     */
    private void setParameters(int i,Position chosenCell, Worker target) {
        if (map[chosenCell.getRow()][chosenCell.getColumn()].getWorkerID() == -1)
            if (i+1< cardRoutine.size()) {
            cardRoutine.get(i + 1).setLastMoveInitialPosition(target.getPosition());
            cardRoutine.get(i + 1).setLastBuildPosition(chosenCell);
        }
        else
            cardRoutine.get(i).setLastMoveInitialPosition(target.getPosition());
    }

    /**
     * check for athena move up
     * @param difference difference between final and initial position
     */
    private void checkMoveUp( int difference) {
        if (this.name.equals("Athena")) {
            if (difference > 0) {
                for (Card curr : enemies) {
                    for (int j = 0; j < curr.getStandardRoutine().size(); j++)
                        curr.getStandardRoutine().get(j).setNoUp(true);
                    for (int j = 0; j < curr.getCardRoutine().size(); j++)
                        curr.getCardRoutine().get(j).setNoUp(true);
                }
            }
            else {
                for (Card curr : enemies) {
                    for (int j = 0; j < curr.getStandardRoutine().size(); j++)
                        curr.getStandardRoutine().get(j).setNoUp(false);
                    for (int j = 0; j < curr.getCardRoutine().size(); j++)
                        curr.getCardRoutine().get(j).setNoUp(false);
                }
            }
        }
    }

    /**
     * set the correct routine without using the god power
     */
    private void setStandardRoutine() {
        standardRoutine = new ArrayList<>();
        standardRoutine.add(new Move(map,false,false,false,false));
        standardRoutine.add(new Build(map,true,false));
    }

    /**
     * set the correct routine using the god power
     */
    private void setCardRoutine(){
        cardRoutine = new ArrayList<>();
        if (move_1)
            if (search_people_1)
                if (knock_1)
                    cardRoutine.add(new Move(map,true,true, false, false)); //Mino
                else
                    cardRoutine.add(new Move(map,false,true, false, false)); //Apollo
            else
                cardRoutine.add(new Move(map,false,false,false,false)); //standard move
        else
            cardRoutine.add(new Build(map,true,false)); //standard build

        if (move_2)
            if (specific_2)
                cardRoutine.add(new Move(map,true,false, true,true)); //Artemis
            else
                cardRoutine.add(new Move(map,false, false, true, false)); //Prometheus
        else
            if (specific_2)
                if (dome_2)
                    cardRoutine.add(new Build(map,false,false)); //Atlas
                else
                    cardRoutine.add(new Build(map,false,true)); //Hephaestus
            else
                cardRoutine.add(new Build(map,true,false)); //standard build

        if (action_3)
            if (not_before_3)
                cardRoutine.add(new Build(map,true,true)); //Demeter
            else
                cardRoutine.add(new Build(map,true,false)); //standard build
    }

    /**
     * check if you can use god power for selectedWorker
     * @param selectedWorker is the target
     * @return true if available
     */
    public Boolean checkCardActivation(Worker selectedWorker) {
        if (this.name.equals("Athena") || this.name.equals("Pan"))
            return false;
        return cardRoutine.get(0).availableWithGod(selectedWorker).size() != 0;
    }

    /**
     * asks for available position to move or build
     * @param i number of action in the routine
     * @param target interested worker
     * @return available positions
     */
    public List<Position> availablePositions(int i, Worker target) {
        if (!activePower)
            return standardRoutine.get(i).availableWithGod(target);
        return cardRoutine.get(i).availableWithGod(target);
    }

    /**
     * launch the real move/build
     * @param i number of action in the routine
     * @param target interested worker
     * @param chosenCell cell chosen by player
     * @return difference between final and initial position
     */
    public boolean applyEffect(int i, Worker target, Position chosenCell) {
        int heightDifference;
        if (!activePower) {
            heightDifference = standardRoutine.get(i).executeAction(chosenCell, target);
            checkMoveUp(heightDifference);
        }
        else {
            setParameters(i, chosenCell, target);
            if (map[chosenCell.getRow()][chosenCell.getColumn()].getWorkerID() != -1) {
                enemy = getCorrectEnemy(map[chosenCell.getRow()][chosenCell.getColumn()].getWorkerID());
                heightDifference = cardRoutine.get(i).executeAction(chosenCell, target);
                cardRoutine.get(i).executeAutoAction(enemy);
            }
            else
                heightDifference = cardRoutine.get(i).executeAction(chosenCell, target);
        }
        return checkWin(heightDifference, chosenCell);
    }

    /**
     * set card ready for first turn
     * @param map game board
     * @param playerId player's Identifier
     */
    public void setCard(Cell[][] map, int playerId) {
        this.map = map;
        this.playerId = playerId;
        this.worker1 = new Worker(playerId, 0);
        this.worker2 = new Worker(playerId, 1);
        this.setStandardRoutine();
        this.setCardRoutine();
    }

    /**
     * first position for workers
     * @return list of available cells
     */
    public List<Position> availableFirstPositioning() {
        List<Position> list = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(map[i][j].getWorkerID() == -1) {
                    list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    /**
     * place workers on the board
     * @param chosenMove initial position chosen
     * @param workerID worker interested
     */
    public void firstPositioning(Position chosenMove, int workerID) {
        if(workerID == 0) {
            worker1.setPosition(new Position(chosenMove.getRow(), chosenMove.getColumn()));
            map[chosenMove.getRow()][chosenMove.getColumn()].setWorkerID(worker1.getWorkerID());
        } else {
            worker2.setPosition(new Position(chosenMove.getRow(), chosenMove.getColumn()));
            map[chosenMove.getRow()][chosenMove.getColumn()].setWorkerID(worker2.getWorkerID());
        }
    }

    /**
     * set enemies for current player
     * @param playedCards players in the game
     */
    public void setEnemies(List<Card> playedCards) {
        this.enemies = new ArrayList<>(playedCards);
        enemies.removeIf(curr -> curr.getPlayerId() == this.playerId);
//        for (int i = 0; i < enemies.size(); i++) {
//            if (enemies.get(i).getPlayerId() == this.playerId) {
//                enemies.remove(enemies.get(i));
//                i--;
//            }
//        }
    }

    //setter e getter vari
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Worker getWorker1() {
        return worker1;
    }
    public Worker getWorker2() {
        return worker2;
    }
    public int getPlayerId() {
        return playerId;
    }
    public List<Effect> getCardRoutine() {
        return cardRoutine;
    }
    public List<Effect> getStandardRoutine() {
        return standardRoutine;
    }

    public void setActivePower(boolean activePower) {
        this.activePower = activePower;
    }

}