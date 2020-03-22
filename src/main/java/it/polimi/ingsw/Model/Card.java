package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Card {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("movID")
    private int movID;
    @SerializedName("costrID")
    private int costrID;
    @SerializedName("oppID")
    private int opponentsID;
    @SerializedName("winID")
    private int winID;
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