package it.polimi.ingsw.controller.Server;

import it.polimi.ingsw.controller.Instructions.MessageInterface;
import it.polimi.ingsw.controller.Instructions.MessageVisitor;
import it.polimi.ingsw.model.ModelInterface;
import it.polimi.ingsw.model.Turn;

import java.util.HashMap;
import java.util.Map;

public class LobbyHandler {
    private Map<Integer, ServerThread> observer;

    public LobbyHandler(Map<Integer, ServerThread> observer) {
        this.observer = observer;
        ModelInterface turn = new Turn(this, observer.size());
        MessageVisitor modelUpdater = new ModelUpdater(turn);
        for(Map.Entry<Integer, ServerThread> currThread : observer.entrySet()) {
            currThread.getValue().setModelUpdater(modelUpdater);
            currThread.getValue().start();
        }
    }

    /**
     * closes the server
     */
    public void closeServer() {
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
