package it.polimi.ingsw.controller;

import java.util.Map;

public interface ControllerInterface {

    void closeServer(String message);
    void sendToAllExcept(int excludedClient, Object commands);
    void sendTo(int clientID, Object commands);
    void sortPlayers(Map<Integer, Integer> playerMap);
    void removeClient(int clientID);
}
