package it.polimi.ingsw.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.Controller.Commands;
import it.polimi.ingsw.Controller.SocketInterface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CLI implements ViewInterface {
    private Commands commands;
    private SocketInterface socket;
    private List<Tile> allTiles;
    private Tile[][] gameMap;

    public CLI(SocketInterface socket) {
        this.socket = socket;
        Gson gson = new Gson();
        InputStream input = getClass().getResourceAsStream("/gameMap.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        Type tilesList = new TypeToken<ArrayList<Tile>>() {}.getType();
        allTiles = gson.fromJson(bf, tilesList);
        gameMap = new Tile[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                gameMap[i][j] = allTiles.get(0);
            }
        }
        updateScreen();
    }

    @Override
    public void setPlayerID(int playerID) {
        commands.setPlayer(playerID);
        System.out.println("Sono il giocatore " + playerID);
    }

    @Override
    public void move(int player, int row, int column) {
        int index = allTiles.indexOf(gameMap[row][column]);
        gameMap[row][column] = allTiles.get(index + 1);
        updateScreen();
    }

    @Override
    public void buildBlock(int raw, int column, int height) {
        int index = allTiles.indexOf(gameMap[raw][column]);
        gameMap[raw][column] = allTiles.get(index + 2);
        updateScreen();
    }

    @Override
    public void buildDome(int raw, int column, int height) {
        int index = allTiles.indexOf(gameMap[raw][column]);
        gameMap[raw][column] = allTiles.get(11 - index);
        updateScreen();
    }

    @Override
    public void updateScreen() {
        System.out.println("-----------------------------------------------------------------------");
        for(int i = 0; i < 5; i++) {
            for(int line = 0; line < 5; line++) {
                for(int j = 0; j < 5; j++) {
                    System.out.print("|" + gameMap[i][j].getLine(line));
                }
                System.out.print("|\n");
            }
            System.out.println("-----------------------------------------------------------------------");
        }
    }

    @Override
    public void updateServer(Commands commands) {
        socket.send(commands);
    }
}
