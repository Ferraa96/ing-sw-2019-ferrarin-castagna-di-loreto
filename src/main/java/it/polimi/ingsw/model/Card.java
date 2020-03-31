package it.polimi.ingsw.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * card/player contains card informations, list of effects and is linked to his 2 workers
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
    @SerializedName("myworker_2")
    private Boolean myworker_2;
    @SerializedName("freemove_samebefore_nextlevelbuilding_2")
    private Boolean freemove_samebefore_nextlevelbuilding_2;
    @SerializedName("action_3")
    private Boolean action_3;
    @SerializedName("specific_3")
    private Boolean specific_3;
    @SerializedName("samebefore_block_3")
    private Boolean samebefore_block_3;

    private Cell[][] map;
    private int playerId;
    private Worker worker1;
    private Worker worker2;
    private ArrayList<Effect> cardRoutine;
    private ArrayList<Effect> standardRoutine;
    private Position chosenCell;
    private ArrayList<Card> enemies;
    private Worker enemy;

    public Card( Cell[][] map, int player) {
        this.map = map;
        this.playerId=player;
        this.worker1 = new Worker(player,1);
        this.worker2 = new Worker(player,2);
        //serializeCards
        this.setStandardRoutine();
        this.setCardRoutine();
    }

    /**
     * check if card power is activable for selectedWorker
     * @param selectedWorker is the target
     * @return true if available
     */
    public Boolean checkCardActivation(Worker selectedWorker) {
        return cardRoutine.get(0).availableWithGod(selectedWorker).size() != 0;
    }

    //restituisce il nemico corretto corrispondente al workerId
    public Worker getCorrectEnemy(int id) {
        for (int i = 0; i <enemies.size() ; i++) {
            if (enemies.get(i).getWorker1().getWorkerID()==id) {
                enemy = enemies.get(i).getWorker1();
                i=enemies.size();
            }
            else if (enemies.get(i).getWorker2().getWorkerID()==id) {
                enemy = enemies.get(i).getWorker2();
                i=enemies.size();
            }
        }
        return enemy;
    }

    //esegue la routine di azioni scelta dal player
    public void executeRoutine(Worker target, Boolean activePower) {
        Worker enemy = target;
        if (!activePower) {
            for (Effect effect : standardRoutine) {
                effect.availableWithGod(target);
                //wait for player choose
                effect.executeAction(chosenCell, target);
            }
        }
        else {
            for (int i = 0; i < cardRoutine.size(); i++) {
                if (cardRoutine.get(i).getMyWorker()) {
                    cardRoutine.get(i).availableWithGod(target);
                    //wait for player choose
                    if (i+1<3) {
                        cardRoutine.get(i + 1).setLastMoveInitialPosition(target.getPosition());
                        cardRoutine.get(i + 1).setLastBuildPosition(chosenCell);
                    }
                    if (map[chosenCell.getRow()][chosenCell.getColumn()].getWorkerID()!=0)
                        enemy = getCorrectEnemy(map[chosenCell.getRow()][chosenCell.getColumn()].getWorkerID());
                    cardRoutine.get(i).executeAction(chosenCell, target);
                }
                else {
                    cardRoutine.get(i).executeAutoAction(enemy);
                }
            }
        }
    }

    /**
     * set the correct routine without using the god power
     */
    public void setStandardRoutine() {
        standardRoutine = new ArrayList<>();
        standardRoutine.add(new Move(map,true,false,false,false,false));
        standardRoutine.add(new Build(true,true,false,false));
    }

    /**
     * set the correct routine using the god power
     */
    public void setCardRoutine(){
        cardRoutine = new ArrayList<>();
        if (move_1)
            if (search_people_1)
                if (knock_1)
                    cardRoutine.add(new Move(map,true,true,true, false, false)); //Minotaurus
                else
                    cardRoutine.add(new Move(map,true,false,true, false, false)); //Apollo
            else
                cardRoutine.add(new Move(map,true,false,false,false,false)); //standard move
        else
            cardRoutine.add(new Build(true,true,false,false)); //Prometheus

        if (move_2)
            if (myworker_2)
                if (freemove_samebefore_nextlevelbuilding_2)
                    cardRoutine.add(new Move(map,true,false, false, true, true)); //Prometheus
                else
                    cardRoutine.add(new Move(map,true,true,false, true,false)); //Artemis
            else
                if (freemove_samebefore_nextlevelbuilding_2)
                    cardRoutine.add(new Move(map,false,false, true,false,false)); //Apollo
                else
                    cardRoutine.add(new Move(map,false,true, true,false,false)); //Minotaurus
        else
            if (freemove_samebefore_nextlevelbuilding_2)
                if (myworker_2)
                    cardRoutine.add(new Build(true,true,false,false)); //standard build
                else
                    cardRoutine.add(new Build(true,true,true,true)); //Hephaestus
            else
                cardRoutine.add(new Build(true,false,false,false)); //Atlas

        if (action_3)
            if (specific_3)
                if (samebefore_block_3)
                    cardRoutine.add(new Build(false,true,true,true)); //Hephaestus
                else
                    cardRoutine.add(new Build(true,true,true,false)); //Demeter
            else
                cardRoutine.add(new Build(true,true,false,false)); //standard build
/*        else
            if (samebefore_block_3)
                blocco di athena;
            else
                extra win di pan; */
    }

    //restituisce tutte le celle libere
    public List<Position> availableFirstPositioning() {
        List<Position> list = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(map[i][j].getWorkerID() == 0) {
                    list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    //piazza il worker nella cella scelta se è vuota
    public void firstPositioning(Position chosenMove, Worker target) {
        if (availableFirstPositioning().contains(chosenMove) && map[chosenMove.getRow()][chosenMove.getColumn()].getWorkerID() == 0) {
            target.setPosition(new Position(chosenMove.getRow(),chosenMove.getColumn()));
            map[chosenMove.getRow()][chosenMove.getColumn()].setWorkerID(target.getWorkerID());
        }
    }

    //setta le carte nemiche di quella corrente
    public void setEnemies(ArrayList<Card> playedCards) {
        enemies = new ArrayList<>(playedCards);
        for (Card current : playedCards) {
            if (current.getPlayerId() == this.playerId) {
                enemies.remove(current);
                break;
            }
        }
    }

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

    public void setChoosenMove(Position choosenMove) {
        this.chosenCell = choosenMove;
    }

}