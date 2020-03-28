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
    private Worker worker1;
    private Worker worker2;

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

    private ArrayList<Effect> cardRoutine;
    private ArrayList<Effect> standardRoutine;

    public Card() {
        worker1 = new Worker();
        worker2 = new Worker();
    }

    /**
     * set the correct routine without using the god power
     */
    public void setStandardRoutine() {
        standardRoutine = new ArrayList<>();
        standardRoutine.add(new Move(true,false,false,false,false));
        standardRoutine.add(new Build(true,false,false));
    }

    /**
     * set the correct routine using the god power
     */
    public void setCardRoutine(){
        cardRoutine = new ArrayList<>();
        if (move_1)
            if (search_people_1)
                if (knock_1)
                    cardRoutine.add(new Move(true,true,true, false, false)); //Minotaurus
                else
                    cardRoutine.add(new Move(true,false,true, false, false)); //Apollo
            else
                cardRoutine.add(new Move(true,false,false,false,false)); //standard move
        else
            cardRoutine.add(new Build(true,false,false)); //Prometheus

        if (move_2)
            if (myworker_2)
                if (freemove_samebefore_nextlevelbuilding_2)
                    cardRoutine.add(new Move(true,false, false, true, true)); //Prometheus
                else
                    cardRoutine.add(new Move(true,true,false, true,false)); //Artemis
            else
                if (freemove_samebefore_nextlevelbuilding_2)
                    cardRoutine.add(new Move(false,false, true,false,false)); //Apollo
                else
                    cardRoutine.add(new Move(false,true, true,false,false)); //Minotaurus
        else
            if (freemove_samebefore_nextlevelbuilding_2)
                cardRoutine.add(new Build(true,false,false)); //standard build
            else
                cardRoutine.add(new Build(false,false,false)); //Atlas

        if (action_3)
            if (specific_3)
                if (samebefore_block_3)
                    cardRoutine.add(new Build(true,true,true)); //Hephaestus
                else
                    cardRoutine.add(new Build(true,true,false)); //Demeter
            else
                cardRoutine.add(new Build(true,false,false)); //standard build
/*        else
            if (samebefore_block_3)
                blocco di athena;
            else
                extra win di pan; */
    }

    public void setWorker1Position(Position position) { worker1.setPosition(position); }

    public void setWorker2Position(Position position) {
        worker2.setPosition(position);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Worker getWorker1() {
        return worker1;
    }

    public void setWorker1(Worker worker1) {
        this.worker1 = worker1;
    }

    public Worker getWorker2() {
        return worker2;
    }

    public void setWorker2(Worker worker2) {
        this.worker2 = worker2;
    }
}