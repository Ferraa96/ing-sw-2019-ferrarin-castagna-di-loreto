package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ModelInterface;
import it.polimi.ingsw.model.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LobbyHandler extends Thread {
    private List<ServerThread> observer;

    public LobbyHandler(List<ServerThread> observer) {
        this.observer = observer;
        start();
    }

    @Override
    public void run() {
        ModelInterface turn = new Turn(this, observer.size());
        ServerModelUpdater serverModelUpdater = new ServerModelUpdater(turn);
        for(ServerThread currThread: observer) {
            currThread.setServerModelUpdater(serverModelUpdater);
            currThread.start();
        }
        turn.startGame();
    }

    /**
     * closes the server
     */
    public void closeServer(String message) {
        for(ServerThread curr: observer) {
            curr.send(message);
            curr.closeConnection();
        }
    }

    /**
     * send the command to all the clients connected except for excludedClient
     * @param excludedClient the excluded client
     * @param commands the command to send
     */
    public void sendToAllExcept(int excludedClient, Object commands) {
        for(int i = 0; i < observer.size(); i++) {
            if(i != excludedClient) {
                observer.get(i).send(commands);
            }
        }
    }

    /**
     * send the command to a specific client
     * @param clientID the receiver client
     * @param commands the command to send
     */
    public void sendTo(int clientID, Object commands) {
        observer.get(clientID).send(commands);
    }

    /**
     * sort all the players in the same order of the game loaded
     * @param playerMap maps the actual order to the game loaded order
     */
    public void sortPlayers(Map<Integer, Integer> playerMap) {
        List<ServerThread> temp = new ArrayList<>();
        for(int i = 0; i < observer.size(); i++) {
            temp.add(observer.get(playerMap.get(i)));
            temp.get(i).setClientID(i);
        }
        observer = temp;
    }

    public void removeClient(int clientID) {
        observer.remove(clientID);
    }
}
