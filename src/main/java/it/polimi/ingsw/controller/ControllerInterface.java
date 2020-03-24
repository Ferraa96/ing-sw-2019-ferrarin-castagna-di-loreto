package it.polimi.ingsw.controller;

public interface ControllerInterface {

    void closeServer();
    void sendToAllExcept(int excludedClient, Commands commands);
    void sendTo(int clientID, Commands commands);
}
