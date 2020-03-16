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
    private Board board;
    private Worker worker1;
    private Worker worker2;
    private Moves moves;

    public Card(Card myCard) {
        this.name = myCard.name;
        this.description = myCard.description;
        this.movID = myCard.movID;
        this.costrID = myCard.costrID;
        this.opponentsID = myCard.opponentsID;
        this.winID = myCard.winID;
        worker1 = new Worker();
        worker2 = new Worker();
        board = new Board();
        moves = new Moves(board.getBoard());
    }

    public void setWorker1Position(Position position) {
        worker1.setPosition(position);
    }

    public void setWorker2Position(Position position) {
        worker2.setPosition(position);
    }

    public static List<Card> getCardList() {
        Gson gson = new Gson();
        InputStream input = Card.class.getClassLoader().getResourceAsStream("godsList.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        Type cardsList = new TypeToken<ArrayList<Card>>() {}.getType();
        return gson.fromJson(bf, cardsList);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}