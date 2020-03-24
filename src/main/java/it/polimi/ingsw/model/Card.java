package it.polimi.ingsw.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Card {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    private Worker worker1;
    private Worker worker2;
    private List<Effect> effects;

    public Card() {
        worker1 = new Worker();
        worker2 = new Worker();
    }

    public void setWorker1Position(Position position) {
        worker1.setPosition(position);
    }

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