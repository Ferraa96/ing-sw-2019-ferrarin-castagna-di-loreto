package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Server.LobbyHandler;
import it.polimi.ingsw.controller.Server.ServerThread;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;


public class TurnTest {

    @Test
    public void tryTurn(){
        List<ServerThread> list = new ArrayList<>();
        Turn turn = new Turn(new LobbyHandler(list),1);
        //turn.setPlayerName("anna",0);
        turn.loadState(false);
        List<Integer> gods = new ArrayList<>();
        gods.add(0);
        gods.add(1);
        turn.setInitialCards(gods);

        turn.toString();
    }
}
