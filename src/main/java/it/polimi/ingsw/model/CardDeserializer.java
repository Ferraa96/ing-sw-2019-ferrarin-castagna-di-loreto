package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * deserialize the list of cards
 */
public class CardDeserializer {

    /**
     * converts the json array to a list of cards
     * @return the list generated
     */
    public List<Card> getCardList() {
        Gson gson = new Gson();
        InputStream input = getClass().getResourceAsStream("/godsList.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        Type cardsList = new TypeToken<ArrayList<Card>>() {}.getType();
        return gson.fromJson(bf, cardsList);
    }

    /**
     * convert the json array to a list of flags
     * @return the selected card
     */
    public List<Card> getSelectedCardFlags() {
        Gson gson = new Gson();
        InputStream input = getClass().getResourceAsStream("/godsFlags.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        Type selectedCard = new TypeToken<ArrayList<Card>>() {}.getType();
        return gson.fromJson(bf, selectedCard);
    }

}
