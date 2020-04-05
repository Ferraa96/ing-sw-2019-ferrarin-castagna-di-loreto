package it.polimi.ingsw.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TileGetter {
    private List<Tile> allTiles;

    public TileGetter() {
        Gson gson = new Gson();
        InputStream input = getClass().getResourceAsStream("/gameMap.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        Type tilesList = new TypeToken<ArrayList<Tile>>() {}.getType();
        allTiles = gson.fromJson(bf, tilesList);
    }

    public Tile getTile(int player, int build, boolean dome) {
        if(!dome) {
            Tile tile = new Tile(allTiles.get(2 * build + player));
            tile.setHeight(build);
            return tile;
        }
        return new Tile(allTiles.get(11 - build));
    }
}
