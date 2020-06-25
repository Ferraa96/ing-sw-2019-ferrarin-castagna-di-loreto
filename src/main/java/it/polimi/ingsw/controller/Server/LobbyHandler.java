package it.polimi.ingsw.controller.Server;

import it.polimi.ingsw.controller.Instructions.MessageInterface;
import it.polimi.ingsw.controller.Instructions.MessageVisitor;
import it.polimi.ingsw.model.Game.ModelInterface;
import it.polimi.ingsw.model.Game.Turn;

import java.util.HashMap;
import java.util.Map;

public class LobbyHandler {
    private Map<Integer, ServerThread> observer;
    private final MessageVisitor modelUpdater;
    private final ModelInterface turn;
    private final boolean[] aborted;
    private boolean playing = false;

    public LobbyHandler(boolean[] aborted) {
        this.aborted = aborted;
        observer = new HashMap<>();
        turn = new Turn(this);
        modelUpdater = new ModelUpdater(turn);
    }

    public void numPlayerReached() {
        playing = true;
        turn.startGame(observer.size());
    }

    public void addClient(int clientID, ServerThread socketClient) {
        observer.put(clientID, socketClient);
        socketClient.setModelUpdater(modelUpdater);
        socketClient.start();
    }

    /**
     * closes the server
     */
    public void closeServer() {
        if(!playing) {
            aborted[0] = true;
        }
        for(Map.Entry<Integer, ServerThread> currThread : observer.entrySet()) {
            currThread.getValue().closeConnection();
        }
    }

    /**
     * send the command to a specific client
     * @param clientID the ID of the client
     * @param command the command to send
     */
    public void sendTo(int clientID, MessageInterface command) {
        observer.get(clientID).send(command);
    }

    /**
     * broadcasts the command to all the clients
     * @param command the command to be sent to all the clients
     */
    public void broadcast(MessageInterface command) {
        for(Map.Entry<Integer, ServerThread> currThread : observer.entrySet()) {
            currThread.getValue().send(command);
        }
    }

    /**
     * sort all the players in the same order of the game loaded
     * @param playerMap maps the actual order to the loaded game order
     */
    public void sortPlayers(Map<Integer, Integer> playerMap) {
        Map<Integer, ServerThread> newOrder = new HashMap<>();
        for(int i = 0; i < playerMap.size(); i++) {
            newOrder.put(i, observer.get(playerMap.get(i)));
            newOrder.get(i).setClientID(i);
        }
        observer = newOrder;
    }

    /**
     * remove a disconnected client
     * @param clientID the id of the client
     */
    public void removeObserver(int clientID) {
        observer.get(clientID).closeConnection();
        observer.remove(clientID);
    }
}
