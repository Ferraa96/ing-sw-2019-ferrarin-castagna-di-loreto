package it.polimi.ingsw.controller;

public interface ControllerInterface {

    void closeServer();
    void sendToAllExcept(int excludedClient, Object commands);
    void sendTo(int clientID, Object commands);
}
