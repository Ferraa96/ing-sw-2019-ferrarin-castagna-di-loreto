package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CardDeserializer {

    public List<Card> getCardList() {
        Gson gson = new Gson();
        InputStream input = getClass().getResourceAsStream("/godsList.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        Type cardsList = new TypeToken<ArrayList<Card>>() {}.getType();
        return gson.fromJson(bf, cardsList);
    }
}
