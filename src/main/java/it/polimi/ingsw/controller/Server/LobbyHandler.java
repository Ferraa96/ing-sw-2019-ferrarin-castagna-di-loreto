package it.polimi.ingsw.controller.Server;

import it.polimi.ingsw.controller.Instructions.MessageInterface;
import it.polimi.ingsw.controller.Instructions.MessageVisitor;
import it.polimi.ingsw.model.ModelInterface;
import it.polimi.ingsw.model.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LobbyHandler {
    private List<ServerThread> observer;

    public LobbyHandler(List<ServerThread> observer) {
        this.observer = observer;
        ModelInterface turn = new Turn(this, observer.size());
        MessageVisitor modelUpdater = new ModelUpdater(turn);
        for(ServerThread currThread: observer) {
            currThread.setModelUpdater(modelUpdater);
            currThread.start();
        }
    }

    /**
     * closes the server
     */
    public void closeServer() {
        for(ServerThread curr: observer) {
            curr.closeConnection();
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
        for(ServerThread currClient: observer) {
            currClient.send(command);
        }
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

    /**
     * remove a disconnected client
     * @param clientID the id of the client
     */
    public void removeObserver(int clientID) {
        observer.get(clientID).closeConnection();
        observer.remove(clientID);
    }
}
